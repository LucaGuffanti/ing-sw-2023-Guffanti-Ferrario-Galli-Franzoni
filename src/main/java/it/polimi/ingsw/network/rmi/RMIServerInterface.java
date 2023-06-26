package it.polimi.ingsw.network.rmi;

import it.polimi.ingsw.network.ClientConnection;
import it.polimi.ingsw.network.messages.Message;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * The remote interface of the server the client uses to invoke methods on the remote object.
 * @author Luca Guffanti
 */
public interface RMIServerInterface extends Remote {
    /**
     * Method called to send a generic message to the server
     * @param message the message that will be sent
     * @param rmiClientInterface the interface of the client invoking the method
     * @throws RemoteException thrown if there are connection issues
     */
    void receiveMessage(Message message, RMIClientInterface rmiClientInterface) throws RemoteException;

    /**
     * This method is used to check that the server is online
     * @param message the ping message
     * @param rmiClientInterface the client interface of the client calling the method
     * @throws RemoteException thrown if there are connection issues
     */
    void incomingPing(Message message, RMIClientInterface rmiClientInterface) throws RemoteException;

    void onClientDisconnection(String name, RMIClientInterface rmiClientInterface) throws RemoteException;
}
