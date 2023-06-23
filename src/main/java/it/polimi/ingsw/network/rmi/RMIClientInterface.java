package it.polimi.ingsw.network.rmi;

import it.polimi.ingsw.network.ClientConnection;
import it.polimi.ingsw.network.messages.Message;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * This interface contains methods that the server uses to invoke the client via RMI
 * @author Luca Guffanti
 */
public interface RMIClientInterface extends Remote {
    /**
     * This method is used to send a message from the server to a client
     * @param message message to be sent
     * @throws RemoteException thrown when a connection issue occurs
     */
    void messageFromServer(Message message) throws RemoteException;
}
