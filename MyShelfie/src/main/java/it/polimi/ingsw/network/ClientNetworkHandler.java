package it.polimi.ingsw.network;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.client.controller.messageHandling.Reducer;
import it.polimi.ingsw.client.controller.messageHandling.messageHandlers.MessagesHandler;
import it.polimi.ingsw.client.controller.stateController.ClientState;
import it.polimi.ingsw.client.controller.stateController.StateContainer;
import it.polimi.ingsw.network.messages.LoginRequestMessage;
import it.polimi.ingsw.network.messages.LoginResponseMessage;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.PingRequestMessage;
import it.polimi.ingsw.network.messages.enums.MessageType;
import it.polimi.ingsw.network.messages.enums.ResponseResultType;
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
    protected List<Message> messageQueue;
    protected ClientManager manager;
    protected String name;
    protected StateContainer stateContainer;

    public ClientNetworkHandler(StateContainer stateContainer) throws RemoteException {
        super();
        this.stateContainer = stateContainer;
        messageQueue = new ArrayList<>();
        new MessageRetriever().start();
    }

    public void handleIncomingMessage(Message received) {
        /*
        * TODO REFACTOR WHEN THE CLIENT CONTROLLER IS READY
        */

        // Old part
        if (received.getType().equals(MessageType.PING_REQUEST)) {
            System.out.println("Sending ping request to server");
            this.sendMessage(
                    new PingRequestMessage(
                            name,
                            ServerNetworkHandler.HOSTNAME
                    )
            );
        } else if (received.getType().equals(MessageType.LOGIN_RESPONSE)){
            LoginResponseMessage lrm = (LoginResponseMessage) received;
            if (lrm.getResultType().equals(ResponseResultType.SUCCESS)) {
                setName(lrm.getRecipient());
                System.out.println("Set the name for me!");
            }
        } else {
            received.printMessage();
        }

        // New part

        // Reduce current state with message payload
        stateContainer.dispatch(received);

    }

    /**
     * This method retrieves messages from the messageQueue, an object that contains messages as they are received by
     * the client.
     * @return the list of messages held in the network interface
     */
    private List<Message> retrieveMessagesFromInterface() {
        List<Message> list;
        synchronized (messageQueue) {
            list = new ArrayList<>(messageQueue);
            messageQueue.clear();
        }
        //System.out.println("retrieved "+ list.size()+ " message");
        return list;
    }

    /**
     * This method is called as the client loses connection with the server because
     * one between the client and the server has become offline.
     */
    public void onConnectionLost() {

    }

    /**
     * This method sends a message through the network, utilising a technology between socket and rmi
     * (as the player chose at the beginning of its connection)
     * @param toSend
     */
    public abstract void sendMessage(Message toSend);
    public abstract void init();


    public void onImpossibleConnection() {
        System.out.println("Couldn't connect");
    }


    public List<Message> getMessageQueue() {
        return messageQueue;
    }


    /**
     * The message retriever accesses the message queue and,
     * for every retrieved message, commands the
     */
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
    public void setName(String name) {
        this.name = name;
    }
}
