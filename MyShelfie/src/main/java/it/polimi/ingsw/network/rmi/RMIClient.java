package it.polimi.ingsw.network.rmi;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.client.controller.stateController.StateContainer;
import it.polimi.ingsw.network.ClientNetworkHandler;
import it.polimi.ingsw.network.ServerNetworkHandler;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.PingRequestMessage;
import it.polimi.ingsw.network.messages.enums.MessageType;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.concurrent.TimeUnit;

/**
 * The RMI Client is a client that uses the RMI (Remote Method Invocation) technology to communicate
 * with the game server.
 */
public class RMIClient extends ClientNetworkHandler implements RMIClientInterface {
    private final String serviceName;
    private final String serverIP;
    private final int serverPort;
    private RMIServerInterface server;
    private RMIClientPinger simplePinger;
    private boolean pingerActive;
    public RMIClient(String serviceName, String serverIP, int serverPort, StateContainer stateContainer, ClientManager manager) throws RemoteException {
        super(stateContainer, manager);
        this.serviceName = serviceName;
        this.serverIP = serverIP;
        this.serverPort = serverPort;
        this.pingerActive=false;
        init();
    }

    /**
     * This method manages the RMI side of the sending of messages:
     * <ul>
     *     <li>If the message is the first ping response sent to the server, a new pinger thread is spawned and
     *     starts asynchronously pinging the server.</li>
     * </ul>
     * @param toSend the message to send
     */
    @Override
    public void sendMessage(Message toSend) {
        try {
            server.receiveMessage(toSend, this);
            // WAIT FOR A TIMEOUT AND PING BACK THE SERVER if the message is a ping
            if (!pingerActive && toSend.getType().equals(MessageType.PING_REQUEST)) {
                pingerActive = true;
                System.out.println("Activating CALLBACK PINGER");
                simplePinger = new RMIClientPinger(this);
                Thread t = new Thread(simplePinger);
                t.start();
            }
        } catch (RemoteException e) {
            onConnectionLost();
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
            //system.out.println("Couldn't access the remote object");
            //e.printStackTrace();
            onImpossibleConnection();
        }
    }

    @Override
    public void messageFromServer(Message message) throws RemoteException {
        handleIncomingMessage(message);
    }

    private class RMIClientPinger implements Runnable {

        private RMIClientInterface rmiClientInterface;

        public RMIClientPinger(RMIClientInterface rmiClientInterface) {
            this.rmiClientInterface = rmiClientInterface;
        }
        @Override
        public void run() {
            while(!Thread.currentThread().isInterrupted()) {
                try {
                    //System.out.println("Spontaneous pinging from the client");
                    server.incomingPing(new PingRequestMessage(
                            "NOT IMPORTANT", ServerNetworkHandler.HOSTNAME),
                            rmiClientInterface
                    );
                    TimeUnit.MILLISECONDS.sleep(15000);
                } catch (RemoteException e) {
                    onConnectionLost();
                } catch (InterruptedException e) {
                    System.out.println("Could not wait");
                    Thread.currentThread().interrupt();
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
