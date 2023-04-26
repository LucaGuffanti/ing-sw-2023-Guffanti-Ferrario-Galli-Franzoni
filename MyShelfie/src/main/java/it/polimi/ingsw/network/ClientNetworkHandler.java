package it.polimi.ingsw.network;

import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.rmi.RMIClient;
import it.polimi.ingsw.network.socket.SocketClient;
import it.polimi.ingsw.network.utils.ClientNetworkConfigurationData;
import it.polimi.ingsw.network.utils.ConnectionTypeEnum;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

/**
 * @TODO: TO COMPLETE
 */
public abstract class ClientNetworkHandler extends UnicastRemoteObject implements Serializable {
    private List<Message> messageQueue;

    public ClientNetworkHandler() throws RemoteException {
        super();
        messageQueue = new ArrayList<>();
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
        System.out.println("retrieved "+ list.size()+ " message");
        return list;
    }

    public void onConnectionLost() {

    }

    public abstract void sendMessage(Message toSend);
    public abstract void init();

    public void onImpossibleConnection() {
        System.out.println("Couldn't connect");
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
