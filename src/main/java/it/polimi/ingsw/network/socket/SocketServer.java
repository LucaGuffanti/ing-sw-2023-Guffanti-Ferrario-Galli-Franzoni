package it.polimi.ingsw.network.socket;

import it.polimi.ingsw.network.ServerNetworkHandler;
import it.polimi.ingsw.network.utils.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * The socket server.
 * @author Luca Guffanti
 */
public class SocketServer extends Thread {

    private Socket socket;
    private final ServerSocket serverSocket;
    private final ServerNetworkHandler serverNetworkHandler;



    public SocketServer(String socketIP, int socketPort, ServerNetworkHandler serverNetworkHandler) throws IOException {
        Logger.networkInfo("Socket server will be on " +socketIP+":"+socketPort);
        this.serverNetworkHandler = serverNetworkHandler;
        serverSocket = new ServerSocket(socketPort);
    }


    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                socket = serverSocket.accept();
                Logger.networkInfo("Accepted a new socket connection");
                SocketClientConnection newConnection = new SocketClientConnection(socket, this);

                Thread newConnectionThread = new Thread(newConnection);
                newConnectionThread.start();
            } catch (IOException e) {
                Logger.networkWarning("Could not accept a new connection");
            }
        }
    }

    public ServerNetworkHandler getServerNetworkHandler() {
        return serverNetworkHandler;
    }
}
