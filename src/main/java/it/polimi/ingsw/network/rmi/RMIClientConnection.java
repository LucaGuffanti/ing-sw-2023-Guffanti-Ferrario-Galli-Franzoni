package it.polimi.ingsw.network.rmi;

import it.polimi.ingsw.network.ClientConnection;
import it.polimi.ingsw.network.ServerNetworkHandler;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.utils.Logger;

import java.rmi.RemoteException;

/**
 * Class that inherits from {@link ClientConnection}, shaped for RMI connections:
 * the client interface is kept in order to correctly call each client.
 * @author Luca Guffanti
 */
public class RMIClientConnection extends ClientConnection {

    /**
     * A particular client interface on which methods are called
     */
    private RMIClientInterface rmiClientInterface;
    /**
     * The server network handler
     */
    private ServerNetworkHandler networkHandler;

    public RMIClientConnection(RMIClientInterface rmiClientInterface, ServerNetworkHandler networkHandler) {
        super(true);
        this.rmiClientInterface = rmiClientInterface;
        this.networkHandler = networkHandler;
    }

    /**
     * This method sends a message to the client with interface {@link RMIClientConnection#rmiClientInterface}
     * @param m the message to be sent
     */
    @Override
    public void sendMessage(Message m) {
        try {
            rmiClientInterface.messageFromServer(m);
        } catch (RemoteException e) {
            //e.printStackTrace();
            Logger.networkWarning("Couldn't invoke remote method on client");
            networkHandler.onDisconnection(this);
        }
    }
}
