package it.polimi.ingsw.network;

import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.rmi.RMIServer;
import it.polimi.ingsw.network.socket.SocketServer;

import java.util.HashMap;

/**
 * The network handler contains the instance of a socket server and of a rmi server:
 * it has methods that regard the general client-server interaction, both towards the server and towards the client.
 * It dispatches messages towards the game controller and reacts to unexpected situations (such as the disconnection
 * of a client).
 * @author Luca Guffanti
 */
public class NetworkHandler {

    public static final String HOSTNAME = "SERVER";
    private RMIServer rmiServer;
    private SocketServer socketServer;
    private HashMap<String, Boolean> nickToConnectedMap;
    private HashMap<String, TypeOfConnection> nickToTypeOfConnectionMap;
    public void sendToPlayer(Message message) {

    }

    public void broadcastToAllButSender(String sender, Message message) {

    }

    public void broadcastToAll(Message message) {

    }
}
