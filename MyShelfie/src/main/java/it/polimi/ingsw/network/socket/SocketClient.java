package it.polimi.ingsw.network.socket;

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
 * The network interface of the client based on the socket technology
 * @author Luca Guffanti
 */
public class SocketClient extends ClientNetworkHandler {
    private Socket socket;
    private ExecutorService executorService;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private final String serverIP;
    private final int serverPort;
    private SocketSender sender;
    private SocketReceiver receiver;

    public SocketClient(String serverIP, int serverPort) throws RemoteException {
        super();
        this.serverIP = serverIP;
        this.serverPort = serverPort;
        this.executorService = Executors.newSingleThreadExecutor();
        init();
    }

    /**
     * This method initializes the socket connection and
     */
    @Override
    public void init() {
        try {
            socket = new Socket(serverIP, serverPort);
        } catch (IOException e) {
            System.out.println("Couldn't connect to the server");
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
    @Override
    public void sendMessage(Message toSend) {
        executorService.submit(()->sender.sendMessage(toSend));
    }

}
