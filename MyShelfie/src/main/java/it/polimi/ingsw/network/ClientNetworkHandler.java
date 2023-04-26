package it.polimi.ingsw.network;

import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.rmi.RMIClient;
import it.polimi.ingsw.network.socket.SocketClient;
import it.polimi.ingsw.network.utils.ClientNetworkConfigurationData;
import it.polimi.ingsw.network.utils.ConnectionTypeEnum;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @TODO: TO COMPLETE
 */
public class ClientNetworkHandler implements Serializable {

    private ClientNetworkInterface clientNetworkInterface;
    private List<Message> messageQueue;
    public ClientNetworkHandler(ConnectionTypeEnum type) {
        messageQueue = new ArrayList<>();
        ClientNetworkConfigurationData d = new ClientNetworkConfigurationData().get();
        if (type.equals(ConnectionTypeEnum.SOCKET)) {
            clientNetworkInterface = new SocketClient(this, d.getServerIP(), d.getServerPort());
            System.out.println("Started socket client");
        } else {
            clientNetworkInterface = new RMIClient(this, d.getServerRMIRegistry(), d.getServerIP(), d.getRMIPort());
            System.out.println("Started RMI client");
        }
        clientNetworkInterface.init();
        new MessageRetriever().start();
    }

    public void handleIncomingMessage(Message received) {
        received.printMessage();
    }

    private List<Message> retrieveMessagesFromInterface() {
        List<Message> list;
        synchronized (messageQueue) {
            list = new ArrayList<>(messageQueue);
            messageQueue.clear();
        }
        System.out.println("retrieved "+ list.size()+ "message");
        return list;
    }

    public void onConnectionLost() {

    }

    public void sendMessage(Message toSend) {
        clientNetworkInterface.sendMessage(toSend);
    }

    public void login(String name) {
        clientNetworkInterface.login(name);
    }

    public void onImpossibleConnection() {
        System.out.println("Couldn't connect");
    }

    public ClientNetworkInterface getClientNetworkInterface() {
        return clientNetworkInterface;
    }

    public List<Message> getMessageQueue() {
        return messageQueue;
    }

    class MessageRetriever extends Thread {
        @Override
        public void run() {
            while(!Thread.currentThread().isInterrupted()) {
                List<Message> queuedMessages;
                synchronized (messageQueue) {
                    while (messageQueue.size()==0) {
                        try {
                            messageQueue.wait();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            throw new RuntimeException(e);
                        }
                    }
                    queuedMessages = retrieveMessagesFromInterface();
                }
                for (int i = 0; i < queuedMessages.size(); i++) {
                    handleIncomingMessage(queuedMessages.get(0));
                }
            }
        }
    }
}
