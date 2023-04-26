package it.polimi.ingsw.network.rmi;

import it.polimi.ingsw.network.ClientNetworkHandler;
import it.polimi.ingsw.network.ClientNetworkInterface;
import it.polimi.ingsw.network.messages.Message;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * The RMI Client is a client that uses the RMI (Remote Method Invocation) technology to communicate
 * with the game server.
 */
public class RMIClient implements ClientNetworkInterface, Serializable {
    private ClientNetworkHandler networkHandler;
    private final String serviceName;
    private final String serverIP;
    private final int serverPort;
    private RMIServerInterface server;
    private RMIClientConnection connection;
    public RMIClient(ClientNetworkHandler networkHandler, String serviceName, String serverIP, int serverPort){
        super();
        this.networkHandler = networkHandler;
        this.serviceName = serviceName;
        this.serverIP = serverIP;
        this.serverPort = serverPort;
    }

    @Override
    public void sendMessage(Message toSend) {
        connection.sendMessage(toSend);
    }

    @Override
    public void login(String name) {
        try {
            server.login(name, connection, connection);
        } catch (RemoteException e) {
            System.out.println("Couldn't perform the remote call");
            e.printStackTrace();
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
        connection = new RMIClientConnection(server, networkHandler);
    }
}
