package it.polimi.ingsw.network;

import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.PingRequestMessage;
import it.polimi.ingsw.network.messages.enums.MessageType;
import it.polimi.ingsw.network.rmi.RMIClientConnection;
import it.polimi.ingsw.network.rmi.RMIClientInterface;
import it.polimi.ingsw.network.utils.Logger;

import java.util.HashMap;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

/**
 * The pinger is a thread that's invoked by the server as soon as it starts. It sends and receives ping messages to
 * and from clients.
 */
public class Pinger extends Thread {
    /**
     * Map between nicknames and connection data
     */
    private HashMap<String, ClientConnection> nickToConnection;
    /**
     * Server side network handler
     */
    private final ServerNetworkHandler networkHandler;
    /**
     * The last ping message to be received
     */
    private Message currentMessage;
    public Pinger(ServerNetworkHandler networkHandler){
        this.networkHandler = networkHandler;
    }

    /**
     * This method controls the pinging of every client. The map of connections is retrieved
     * from the network handler. If the client is connected a timer
     */
    @Override
    public void run() {
        while(!Thread.currentThread().isInterrupted()) {
            nickToConnection = retrieveNicksAndConnections();
            if (nickToConnection.size() != 0) {
                for (String nick : nickToConnection.keySet()) {
                    if (nickToConnection.get(nick).isConnected() && nickToConnection.get(nick) instanceof RMIClientConnection) {
                        Logger.pingerInfo("Pinging "+nick);
                        networkHandler.sendToPlayer(
                                nick,
                                new PingRequestMessage(
                                        ServerNetworkHandler.HOSTNAME,
                                        nick
                                )
                        );
                        try {
                            // the thread sleeps waiting for the ping response to arrive
                            TimeUnit.MILLISECONDS.sleep(3000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        if (currentMessage == null) {
                            networkHandler.onDisconnection(nickToConnection.get(nick));
                            Logger.pingerInfo(nick+ " didn't respond to the ping");
                        } else {
                            Logger.pingerInfo(nick+ " is online");
                        }
                    }
                    currentMessage = null;
                }

                try {
                    TimeUnit.SECONDS.sleep(8);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            } else {
                try {
                    Logger.pingerInfo("no players to ping");
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private HashMap<String, ClientConnection> retrieveNicksAndConnections() {
        return networkHandler.getNickToConnectionMap();
    }

    public void addMessage(Message message) {
        currentMessage = message;
    }
}
