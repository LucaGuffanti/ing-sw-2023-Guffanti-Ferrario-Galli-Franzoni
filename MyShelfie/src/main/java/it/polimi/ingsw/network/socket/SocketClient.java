package it.polimi.ingsw.network.socket;

import it.polimi.ingsw.network.ClientNetworkHandler;
import it.polimi.ingsw.network.ClientNetworkInterface;
import it.polimi.ingsw.network.messages.ConnectionEstablishedMessage;
import it.polimi.ingsw.network.messages.LoginRequestMessage;
import it.polimi.ingsw.network.messages.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The network interface of the client based on the socket technology
 * @author Luca Guffanti
 */
public class SocketClient implements ClientNetworkInterface {

    private ClientNetworkHandler networkHandler;
    private Socket socket;
    private ExecutorService executorService;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private final String serverIP;
    private final int serverPort;
    private SocketSender sender;
    private SocketReceiver receiver;

    public SocketClient(ClientNetworkHandler networkHandler, String serverIP, int serverPort) {
        this.networkHandler = networkHandler;
        this.serverIP = serverIP;
        this.serverPort = serverPort;
        this.executorService = Executors.newSingleThreadExecutor();
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
            networkHandler.onImpossibleConnection();
            return;
        }

        try {
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            System.out.println("Couldn't open the input or output streams");
            networkHandler.onImpossibleConnection();
            return;
        }
        try {
            ConnectionEstablishedMessage msg = (ConnectionEstablishedMessage) in.readObject();
            msg.printMessage();
        } catch (IOException e) {
            System.out.println("Lost connection from the server");
            networkHandler.onConnectionLost();
            return;
        } catch (ClassNotFoundException e) {
            System.out.println("Couldn't cast message");
            networkHandler.onImpossibleConnection();
            return;
        }

        System.out.println("Ready to recieve and send");
        receiver = new SocketReceiver(socket, in, networkHandler);
        receiver.start();
        sender = new SocketSender(socket, out, networkHandler);
    }
    @Override
    public void sendMessage(Message toSend) {
        executorService.submit(()->sender.sendMessage(toSend));
    }

    @Override
    public void login(String name) {
        sendMessage(new LoginRequestMessage(name));
    }
}
