package it.polimi.ingsw.network;

import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.rmi.RMIServer;
import it.polimi.ingsw.network.socket.SocketServer;
import it.polimi.ingsw.network.utils.Logger;
import jdk.jfr.Description;
import jdk.jfr.Label;

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

    @Label("DEBUG")
    private Message currentMessage;
    public void sendToPlayer(String recipient, Message message) {
        currentMessage = message;
        Logger.networkInfo("sent a " + message.getType() + " private message to "+ recipient);
    }

    public void broadcastToAllButSender(String sender, Message message) {
        currentMessage = message;
        Logger.networkInfo("broadcast a " + message.getType() + " message to all but " + sender);
    }

    public void broadcastToAll(Message message) {
        currentMessage = message;
        Logger.networkInfo("broadcast a " + message.getType() + " message");
    }

    @Label("DEBUG")
    public Message getCurrentMessage() {
        return currentMessage;
    }
}
