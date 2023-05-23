package it.polimi.ingsw.network;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.client.controller.messageHandling.Reducer;
import it.polimi.ingsw.client.controller.messageHandling.messageHandlers.MessagesHandler;
import it.polimi.ingsw.client.controller.stateController.ClientState;
import it.polimi.ingsw.client.controller.stateController.StateContainer;
import it.polimi.ingsw.client.view.cli.Printer;
import it.polimi.ingsw.network.messages.*;
import it.polimi.ingsw.network.messages.enums.MessageType;
import it.polimi.ingsw.network.messages.enums.ResponseResultType;
import it.polimi.ingsw.network.rmi.RMIClient;
import it.polimi.ingsw.network.socket.SocketClient;
import it.polimi.ingsw.network.utils.ClientNetworkConfigurationData;
import it.polimi.ingsw.network.utils.ConnectionTypeEnum;
import it.polimi.ingsw.network.utils.Logger;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

//TODO ADD PINGING FROM THE CLIENT TO THE SERVER

/**
 * The client network handler is responsible for the server-client interactions.
 * It's an abstract class that is extended by {@link RMIClient} and {@link SocketClient}.
 */
public abstract class ClientNetworkHandler extends UnicastRemoteObject implements Serializable {
    protected List<Message> messageQueue;
    protected ClientManager manager;
    protected String name;
    protected StateContainer stateContainer;

    public ClientNetworkHandler(StateContainer stateContainer, ClientManager clientManager) throws RemoteException {
        super();
        this.stateContainer = stateContainer;
        messageQueue = new ArrayList<>();
        this.manager = clientManager;
        // new MessageRetriever().start();
    }

    /**
     * This method manages the incoming message:
     * <ul>
     *     <li>
     *         If the message is of type PING_REQUEST, it's directly sent back to the server.
     *     </li>
     *     <li>
     *         In any other case the message is sent to the state container that dispatches an handler
     *         capable of executing the payload and changing the the view based on the information
     *         contained in the message
     *     </li>
     * </ul>
     * @param received The received message
     */
    public void handleIncomingMessage(Message received) {
        //Logger.externalInjection("Managing "+received.getType());
        // It's useless to make the ping message exit the client network handler
        if (received.getType().equals(MessageType.PING_REQUEST)) {
            //System.out.println("Sending ping request to server");
            this.sendMessage(
                    new PingRequestMessage(
                            name,
                            ServerNetworkHandler.HOSTNAME
                    )
            );
        } else {
            // Reduce current state with message payload
            stateContainer.dispatch(received);
        }
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
        Printer.error("CONNECTION LOST");
        manager.onDisconnection();
    }

    /**
     * This method sends a message through the network, utilising a technology between socket and rmi
     * (as the player chose at the beginning of its connection)
     * @param toSend
     */
    public abstract void sendMessage(Message toSend);

    /**
     * This method initializes the network handler,
     * by opening the socket connection in the case of a SocketClient or
     * by executing the registry lookup in the case of a RMIClient.
     */
    public abstract void init();


    /**
     * This method manages the case of an impossible connection
     */
    public void onImpossibleConnection() {
        Printer.error("Couldn't connect to the server. Check that you and the server are " +
                "both online and connected to the same network.");
        Printer.error("========TERMINATING========");
        System.exit(1);
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
                    Logger.externalInjection("SUBMITTING "+ queuedMessages.get(0).getType());
                    handleIncomingMessage(queuedMessages.get(0));
                    queuedMessages.remove(0);
                }
            }
        }
    }
    public void setName(String name) {
        this.name = name;
    }
}
