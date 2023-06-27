package it.polimi.ingsw.network;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.client.controller.stateController.StateContainer;
import it.polimi.ingsw.client.view.cli.Printer;
import it.polimi.ingsw.network.messages.*;
import it.polimi.ingsw.network.messages.enums.MessageType;
import it.polimi.ingsw.network.rmi.RMIClient;
import it.polimi.ingsw.network.socket.SocketClient;
import it.polimi.ingsw.network.utils.Logger;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;


/**
 * The client network handler is responsible for the server-client interactions.
 * It's an abstract class that is extended by {@link RMIClient} and {@link SocketClient}.
 */
public abstract class ClientNetworkHandler extends UnicastRemoteObject implements Serializable {
    protected ClientManager manager;
    protected String name;
    protected StateContainer stateContainer;

    public ClientNetworkHandler(StateContainer stateContainer, ClientManager clientManager) throws RemoteException {
        super();
        this.stateContainer = stateContainer;
        this.manager = clientManager;
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
            this.sendMessage(
                    new PingRequestMessage(
                            name,
                            ServerNetworkHandler.HOSTNAME
                    )
            );
        } else {
            // Reduce current state with message payload
            stateContainer.updateState(received);
        }
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

    public void setName(String name) {
        this.name = name;
    }
}
