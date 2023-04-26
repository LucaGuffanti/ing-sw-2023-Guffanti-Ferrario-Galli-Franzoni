package it.polimi.ingsw.network.rmi;

import it.polimi.ingsw.network.ClientConnection;
import it.polimi.ingsw.network.messages.Message;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIClientInterface extends Remote {
    void messageFromServer(Message message) throws RemoteException;
}
