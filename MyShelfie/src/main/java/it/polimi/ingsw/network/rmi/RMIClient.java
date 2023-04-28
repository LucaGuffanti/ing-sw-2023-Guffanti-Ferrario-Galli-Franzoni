package it.polimi.ingsw.network.rmi;

import it.polimi.ingsw.client.controller.stateController.StateContainer;
import it.polimi.ingsw.network.ClientConnection;
import it.polimi.ingsw.network.ClientNetworkHandler;
import it.polimi.ingsw.network.messages.Message;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * The RMI Client is a client that uses the RMI (Remote Method Invocation) technology to communicate
 * with the game server.
 */
public class RMIClient extends ClientNetworkHandler implements RMIClientInterface {
    private final String serviceName;
    private final String serverIP;
    private final int serverPort;
    private RMIServerInterface server;
    public RMIClient(String serviceName, String serverIP, int serverPort, StateContainer stateContainer) throws RemoteException {
        super(stateContainer);
        this.serviceName = serviceName;
        this.serverIP = serverIP;
        this.serverPort = serverPort;
        init();
    }

    @Override
    public void sendMessage(Message toSend) {
        try {
            server.receiveMessage(toSend, this);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void init() {
        try {
            System.out.println("lookup on " + "rmi://"+serverIP+":"+serverPort+"/"+serviceName);
            server = (RMIServerInterface) Naming.lookup("rmi://"+serverIP+":"+serverPort+"/"+serviceName);
            System.out.println("Retrieved the remote object");
        } catch (NotBoundException e) {
            System.out.println("The server is not bound");
        } catch (MalformedURLException e) {
            System.out.println("The url is malformed");
        } catch (RemoteException e) {
            System.out.println("Couldn't access the remote object");
            e.printStackTrace();
        }
    }

    @Override
    public void messageFromServer(Message message) throws RemoteException {
        synchronized (messageQueue) {
            messageQueue.add(message);
            messageQueue.notifyAll();
        }

    }
}
