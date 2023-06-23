package it.polimi.ingsw.network.socket;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.client.controller.stateController.StateContainer;
import it.polimi.ingsw.network.ClientConnection;
import it.polimi.ingsw.network.ClientNetworkHandler;
import it.polimi.ingsw.network.messages.ConnectionEstablishedMessage;
import it.polimi.ingsw.network.messages.LoginRequestMessage;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.utils.ConnectionTypeEnum;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The implementation of the network-side of the client used for socket-based communication
 * @author Luca Guffanti
 */
public class SocketClient extends ClientNetworkHandler {
    /**
     * The socket on which the connection is based
     */
    private Socket socket;
    /**
     * Executor service used to send messages
     */
    private ExecutorService executorService;
    /**
     * Stream towards the server
     */
    private ObjectOutputStream out;
    /**
     * Stream from the server to the client
     */
    private ObjectInputStream in;
    /**
     * The ip address of the server
     */
    private final String serverIP;
    /**
     * The port of the server
     */
    private final int serverPort;
    /**
     * Object that sends messages via socket
     */
    private SocketSender sender;
    /**
     * Object that receives messages via socket
     */
    private SocketReceiver receiver;

    public SocketClient(String serverIP, int serverPort, StateContainer stateContainer, ClientManager manager) throws RemoteException {
        super(stateContainer, manager);
        this.serverIP = serverIP;
        this.serverPort = serverPort;
        this.executorService = Executors.newSingleThreadExecutor();
        init();
    }

    /**
     * This method initializes the socket connection, the input streams and correctly acts in case of problems.
     */
    @Override
    public void init() {
        try {
            socket = new Socket(serverIP, serverPort);
        } catch (IOException e) {
            //System.out.println("Couldn't connect to the server");
            onImpossibleConnection();
            return;
        }

        try {
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            System.out.println("Couldn't open the input or output streams");
            onImpossibleConnection();
            return;
        }
        try {
            ConnectionEstablishedMessage msg = (ConnectionEstablishedMessage) in.readObject();
            msg.printMessage();
        } catch (IOException e) {
            System.out.println("Lost connection from the server");
            onConnectionLost();
            return;
        } catch (ClassNotFoundException e) {
            System.out.println("Couldn't cast message");
            onImpossibleConnection();
            return;
        }

        System.out.println("Ready to receive and send");
        receiver = new SocketReceiver(socket, in, this);
        receiver.start();
        sender = new SocketSender(socket, out, this);
    }

    /**
     * This method calls the executor service to send a message to the server via a {@link SocketSender}
     * @param toSend the message to be sent to the server
     */
    @Override
    public void sendMessage(Message toSend) {
        executorService.submit(()->sender.sendMessage(toSend));
    }

}
