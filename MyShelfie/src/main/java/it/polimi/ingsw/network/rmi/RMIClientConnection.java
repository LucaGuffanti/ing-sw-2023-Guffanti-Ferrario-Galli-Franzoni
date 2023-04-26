package it.polimi.ingsw.network.rmi;

import it.polimi.ingsw.network.ClientConnection;
import it.polimi.ingsw.network.ClientNetworkHandler;
import it.polimi.ingsw.network.ClientNetworkInterface;
import it.polimi.ingsw.network.messages.AccessResultMessage;
import it.polimi.ingsw.network.messages.LoginRequestMessage;
import it.polimi.ingsw.network.messages.LoginResponseMessage;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.enums.MessageType;
import it.polimi.ingsw.network.utils.Logger;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * The object describing a connection between the client and the server
 */
public class RMIClientConnection implements RMIClientInterface, ClientConnection, Serializable {

    private RMIServerInterface server;
    private ClientNetworkHandler networkHandler;

    public RMIClientConnection(RMIServerInterface server, ClientNetworkHandler networkHandler) {
        this.server = server;
        this.networkHandler = networkHandler;
    }

    @Override
    public void sendMessage(Message m) {
        try {
            server.receiveMessage(m);
        } catch (RemoteException e) {
            System.out.println("Couldn't send message via rmi");
        }
    }

    @Override
    public void messageFromServer(Message message) throws RemoteException {
        List<Message> queue = null;
        queue = networkHandler.getMessageQueue();
        synchronized (queue) {
            queue.add(message);
            queue.notifyAll();
        }
    }
}
