package it.polimi.ingsw.network.rmi;

import it.polimi.ingsw.network.ClientConnection;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.utils.Logger;

import java.rmi.RemoteException;

public class RMIClientConnection extends ClientConnection {

    private RMIClientInterface rmiClientInterface;

    public RMIClientConnection(RMIClientInterface rmiClientInterface) {
        super(true);
        this.rmiClientInterface = rmiClientInterface;
    }

    @Override
    public void sendMessage(Message m) {
        try {
            rmiClientInterface.messageFromServer(m);
        } catch (RemoteException e) {
            e.printStackTrace();
            Logger.networkWarning("Couldn't invoke remote method on client");
        }
    }
}
