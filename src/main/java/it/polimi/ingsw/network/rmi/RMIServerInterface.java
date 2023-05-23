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
    void receiveMessage(Message message, RMIClientInterface rmiClientInterface) throws RemoteException;
    void incomingPing(Message message, RMIClientInterface rmiClientInterface) throws RemoteException;
}
