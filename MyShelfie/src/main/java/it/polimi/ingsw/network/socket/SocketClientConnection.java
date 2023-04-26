package it.polimi.ingsw.network.socket;

import it.polimi.ingsw.network.ClientConnection;
import it.polimi.ingsw.network.LoginResult;
import it.polimi.ingsw.network.ServerNetworkHandler;
import it.polimi.ingsw.network.messages.ConnectionEstablishedMessage;
import it.polimi.ingsw.network.messages.LoginRequestMessage;
import it.polimi.ingsw.network.messages.LoginResponseMessage;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.enums.ResponseResultType;
import it.polimi.ingsw.network.utils.Logger;
import it.polimi.ingsw.network.utils.ResponsesDescriptions;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SocketClientConnection implements Runnable, ClientConnection{

    private final Socket socket;
    private final SocketServer socketServer;
    private  ObjectOutputStream out;
    private ObjectInputStream in;


    public SocketClientConnection(Socket socket, SocketServer socketServer) {
        this.socket = socket;
        this.socketServer = socketServer;
    }

    @Override
    public void run() {
        try {
            synchronized (socket) {
                out = new ObjectOutputStream(socket.getOutputStream());
                in = new ObjectInputStream(socket.getInputStream());
            }
        } catch (IOException e) {
            Logger.networkCritical("Could not open input and output streams for the socket");
        }
        /*
            Before everything, send a ConnectionEstablished Message to the client
         */
        Message msg;

        try {
            msg = new ConnectionEstablishedMessage(ServerNetworkHandler.HOSTNAME);
            out.writeObject(msg);
            out.flush();
            Logger.networkInfo("Sending CONNECTION_ESTABLISHED to start protocol");
        } catch (IOException e) {
            Logger.networkWarning("Lost connection with the client - Can't start communication protocol. Abort");
            Thread.currentThread().interrupt();
        }

        /*
            Then the server expects a client login request and manages it
            THE POSSIBLE RECONNECTION OF THE CLIENT IS MANAGED HERE .
         */
        LoginResult result;
        do {
            try {
                result = manageClientLogin();
            } catch (IOException | ClassNotFoundException e) {
                Logger.networkWarning("Could not complete LOGIN PROCEDURE");
                Thread.currentThread().interrupt();
                return;
            }
        }   while (!result.isLogged());

        while (!Thread.currentThread().isInterrupted()) {
            try {
                msg = (Message) in.readObject();
                socketServer.getServerNetworkHandler().onMessageReceived(msg);
            } catch (IOException e) {
                Logger.networkWarning("Lost connection with a client - Executing client logout procedures");
                Thread.currentThread().interrupt();
                socketServer.getServerNetworkHandler().onDisconnection(this);
            } catch (ClassNotFoundException e) {
                Logger.networkWarning("Could not cast the received object to Message");
                socketServer.getServerNetworkHandler().onDisconnection(this);
            }
        }
    }

    /**
     * This method manages the client login, and the possible client reconnection
     * (that occurs when a client who had previously disconnected tries to log
     * in with the same username).
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private LoginResult manageClientLogin() throws IOException, ClassNotFoundException {
        LoginRequestMessage msg = (LoginRequestMessage)  in.readObject();
        LoginResult result;

        result = socketServer.getServerNetworkHandler().onLoginRequest(msg.getSenderUsername(), this);
        if (result.isLogged() && !result.isReconnecting()) {
            sendMessage(new LoginResponseMessage(
                    ServerNetworkHandler.HOSTNAME,
                    ResponsesDescriptions.LOGIN_SUCCESS,
                    ResponseResultType.SUCCESS,
                    msg.getSenderUsername()
            ));
        }
        else if (result.isLogged() && result.isReconnecting()) {
            sendMessage(new LoginResponseMessage(
                    ServerNetworkHandler.HOSTNAME,
                    ResponsesDescriptions.RECONNECTION_SUCCESS,
                    ResponseResultType.SUCCESS,
                    msg.getSenderUsername()
            ));
        }
        else {
            sendMessage(new LoginResponseMessage(
                            ServerNetworkHandler.HOSTNAME,
                            ResponsesDescriptions.LOGIN_FAILURE_ALREADY_TAKEN,
                            ResponseResultType.FAILURE,
                            msg.getSenderUsername()
                    ));
        }

        return result;
    }

    @Override
    public void sendMessage(Message m){
        socketSendMessage(m);
    }

    private void socketSendMessage(Message m) {
        try {
            out.writeObject(m);
            out.flush();
        } catch (IOException e) {
            Logger.networkWarning("Lost connection with the client");
            socketServer.getServerNetworkHandler().onDisconnection(this);

        }
    }
}
