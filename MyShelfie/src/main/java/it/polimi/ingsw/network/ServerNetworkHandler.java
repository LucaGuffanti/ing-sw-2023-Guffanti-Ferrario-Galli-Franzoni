package it.polimi.ingsw.network;

import it.polimi.ingsw.network.messages.*;
import it.polimi.ingsw.network.messages.enums.ReloadGameChoice;
import it.polimi.ingsw.network.messages.enums.ResponseResultType;
import it.polimi.ingsw.network.rmi.RMIServer;
import it.polimi.ingsw.network.socket.SocketServer;
import it.polimi.ingsw.network.utils.Logger;
import it.polimi.ingsw.network.utils.ResponsesDescriptions;
import it.polimi.ingsw.network.utils.ServerConfigurationData;
import it.polimi.ingsw.server.controller.GameController;
import it.polimi.ingsw.server.controller.GameStatusEnum;
import jdk.jfr.Label;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * The network handler contains the instance of a socket server and of a rmi server:
 * it has methods that regard the general client-server interaction, both towards the server and towards the client.
 * It dispatches messages towards the game controller and reacts to unexpected situations (such as the disconnection
 * of a client).
 * @author Luca Guffanti
 */
public class ServerNetworkHandler {

    public static final String HOSTNAME = "SERVER";
    private String RMIServiceName;
    private String socketIP;
    private int socketPort;
    private int RMIPort;
    private RMIServer rmiServer;
    private SocketServer socketServer;
    private final HashMap<String, ClientConnection> nickToConnectionMap;
    private final HashSet<String> allReconnectedUsers;
    private final Object clientStauts = new Object();
    private Pinger pinger;

    private GameController controller = null;
    private final Object controllerLock = new Object();

    /**
     * This constructor is used only for testing purposes: it allows the testing of the controller
     * in an offline environment
     */
    public ServerNetworkHandler(){
        this.nickToConnectionMap = new HashMap<>();
        this.allReconnectedUsers = new HashSet<>();
    }

    /**
     * This constructor builds a network handler, that remains in a "dark and quiet" state. To activate the network
     * handler a call to {@code activateHandler} is needed. This method of constructing and then activating the handler
     * permits the execution of operations before the handler is online.
     * @param data server configuration data
     */
    public ServerNetworkHandler(ServerConfigurationData data) {
        this.socketIP = data.getIpAddress();
        this.socketPort = data.getSocketPort();
        this.RMIServiceName = data.getRMIRegistryServiceName();
        this.RMIPort = data.getRmiPort();
        this.pinger = new Pinger(this);

        this.nickToConnectionMap = new HashMap<>();
        this.allReconnectedUsers = new HashSet<>();

    }

    /**
     * This method build and activates the RMI server and the Socket server,
     * by calling their own constructors.
     */
    public void activateHandler() {
        Logger.networkInfo("Activating the RMI Server");
        try {
            rmiServer = new RMIServer(RMIServiceName, socketIP, RMIPort, this);
        } catch (RemoteException e) {
            Logger.networkCritical("Could not activate the RMI server");
            e.printStackTrace();
        }

        Logger.networkInfo("Activating the Socket Server");
        try {
            socketServer = new SocketServer(socketIP, socketPort, this);
            Logger.networkInfo("Socket server correctly deployed");
            socketServer.start();
        } catch (IOException e) {
            Logger.networkCritical("Could not open the socket on port " + socketPort);
        }
        Logger.networkInfo("Starting pinger");
        pinger.start();
    }

    @Label("DEBUG")
    private Message currentMessage;
    public void sendToPlayer(String recipient, Message message) {
        HashMap<String, ClientConnection> ntcCopy;
        synchronized (nickToConnectionMap) {
            ntcCopy = new HashMap<>(nickToConnectionMap);
        }

        currentMessage = message;
        Logger.networkInfo("sent a " + message.getType() + " private message to "+ recipient);
        synchronized (ntcCopy.get(recipient)) {
            if(ntcCopy.get(recipient).isConnected()) {
                ntcCopy.get(recipient).sendMessage(message);
            }
        }
    }

    public void broadcastToAllButSender(String sender, Message message) {
        HashMap<String, ClientConnection> ntcCopy;
        HashMap<String, Boolean> ntbCopy;
        synchronized (nickToConnectionMap) {
            ntcCopy = new HashMap<>(nickToConnectionMap);
        }

        currentMessage = message;
        Logger.networkInfo("broadcast a " + message.getType() + " message to all but " + sender);
        for (String nick : ntcCopy.keySet()) {
            if (!nick.equals(sender) && ntcCopy.get(sender).isConnected()) {
                synchronized (ntcCopy.get(nick)) {
                    ntcCopy.get(nick).sendMessage(message);
                }
            }
        }
    }

    public void broadcastToAll(Message message) {
        HashMap<String, ClientConnection> ntcCopy;
        HashMap<String, Boolean> ntbCopy;
        synchronized (nickToConnectionMap) {
            ntcCopy = new HashMap<>(nickToConnectionMap);
        }
        currentMessage = message;
        Logger.networkInfo("broadcast a " + message.getType() + " message");

        for (String nick : ntcCopy.keySet()) {
            if (ntcCopy.get(nick).isConnected()) {
                synchronized (ntcCopy.get(nick)) {
                    ntcCopy.get(nick).sendMessage(message);
                }
            }
        }

    }

    @Label("DEBUG")
    public Message getCurrentMessage() {
        return currentMessage;
    }

    /**
     * This method manages the login of a new client. The client can request a username
     * which can either be
     * <ol>
     *     <li>new: a client with the same name has never logged in</li>
     *     <li>known: a client with the same name has already logged in but has disconnected</li>
     *     <li>unavailable: a client with the same name has already logged and is still connected</li>
     * </ol>
     * In the cases of new or known user the system automatically admits the user. If the user is knows he's sent
     * a message containing the current status of the game.
     * If the nickname is unavailable the client is simply notified
     * @param name the name the new client requests
     * @param connection the connection with the client.
     * @return
     */
    public LoginResult onLoginRequest(String name, ClientConnection connection) {
        Logger.networkInfo("A new client is trying to log in");

        ArrayList<String> alreadyLoggedIn;
        boolean logged = false;
        boolean reconnecting = false;

        synchronized (nickToConnectionMap) {
                if (!nickToConnectionMap.containsKey(name)) {
                    nickToConnectionMap.put(name, connection);
                    Logger.networkInfo(name+ " logged in");
                    logged = true;
                } else {
                    if (!nickToConnectionMap.get(name).isConnected()) {
                        nickToConnectionMap.get(name).setConnected(true);
                        Logger.networkInfo(name+ " is a known user");
                        Logger.networkInfo(name+ " logged back in");
                        logged = true;
                        reconnecting = true;
                        synchronized (allReconnectedUsers) {
                            allReconnectedUsers.add(name);
                        }
                    } else {
                        Logger.networkWarning(name+ " is already taken");
                        logged = false;
                    }
                }

            }
        return new LoginResult(logged, reconnecting);
    }
    public void onDisconnection(ClientConnection connection) {
        Logger.networkWarning("Disconnecting client");
        HashMap<String, ClientConnection> temp;
        synchronized (nickToConnectionMap) {
            temp = new HashMap<>(nickToConnectionMap);
            if (temp.containsValue(connection)) {
                String nickname = "";
                for (String nick : temp.keySet()) {
                    if (temp.get(nick).equals(connection)) {
                        nickname = nick;
                        nickToConnectionMap.get(nick).setConnected(false);
                        Logger.networkWarning(nickname + " is now considered disconnected");
                        break;
                    }
                }
            }
        }
        synchronized (controllerLock) {
            controller.onPlayerDisconnection();
        }

    }

    /**
     * This method analyzes the contents of the received message and dispatches it towards the controller or back into
     * the network handler. It's important to notice that this method only refers to messages that can actually be received
     * by the server
     * @param m the message that was received
     */
    public void onMessageReceived(Message m) {
        switch (m.getType()) {
            case JOIN_GAME -> {
                synchronized (controllerLock) {
                    if (controller != null) {
                        controller.onPlayerJoin(m.getSenderUsername());
                    } else {
                       sendToPlayer(m.getSenderUsername(),
                               new PickNumberOfPlayersMessage(
                                       ServerNetworkHandler.HOSTNAME,
                                       "Insert the number of user for this game through the specific command.",
                                       m.getSenderUsername()
                               )
                       );
                    }
                }
            }
            case NUMBER_OF_PLAYERS_SELECTION -> {
                synchronized (controllerLock) {
                    if (controller == null) {
                        NumberOfPlayersSelectionMessage msg = (NumberOfPlayersSelectionMessage) m;
                        controller = new GameController(this);
                        controller.createGame(msg.getSenderUsername(),msg.getNumOfPlayers(),0);
                    } else {
                        sendToPlayer(m.getSenderUsername(),
                                new AccessResultMessage(
                                        ServerNetworkHandler.HOSTNAME,
                                        ResponsesDescriptions.JOIN_FAILURE_ALREADY_CREATED,
                                        ResponseResultType.FAILURE,
                                        null,
                                        m.getSenderUsername()
                                )
                        );
                    }
                }
            }
            case REJOIN_GAME -> {
                HashSet<String> aruCopy;
                synchronized (allReconnectedUsers) {
                    aruCopy = new HashSet<>(allReconnectedUsers);
                }
                // preventing already joined players to rejoin
                if (aruCopy.contains(m.getSenderUsername())) {
//                    synchronized (controllerLock) {
//                        controller.onPlayerReconnection(m.getSenderUsername());
//                    }
                }
            }
            case PICK_FROM_BOARD -> {
                synchronized (controllerLock) {
                    PickFromBoardMessage p = (PickFromBoardMessage) m;
                    controller.onGameMessageReceived(p);
                }
            }
            case SELECT_COLUMN -> {
                synchronized (controllerLock) {
                    SelectColumnMessage s = (SelectColumnMessage) m;
                    controller.onGameMessageReceived(s);
                }
            }
            case PING_REQUEST -> {
                pinger.addMessage(m);
            }
            case CHAT_MESSAGE -> {
                ChatMessage c = (ChatMessage) m;
                List<String> users = c.getRecipients();

                if (users.size() == 0) {
                    broadcastToAll(c);
                } else {
                    for (String u : users) {
                        sendToPlayer(u, c);
                    }
                    sendToPlayer(c.getSenderUsername(), c);
                }
            }
            case FOUND_SAVED_GAME_RESPONSE -> {
                FoundSavedGameResponseMessage f = (FoundSavedGameResponseMessage) m;
                synchronized (controllerLock) {
                    if (controller.getGameStatus().equals(GameStatusEnum.FOUND_SAVE_FILE)) {
                        if (f.getChoice().equals(ReloadGameChoice.ACCEPT_RELOAD) && f.getSenderUsername().equals(controller.getGame().getGameInfo().getAdmin())) {
                            controller.reloadExistingGame();
                        } else if (f.getChoice().equals(ReloadGameChoice.DECLINE_RELOAD)){
                            controller.newGameNoReload();
                        }
                    }
                }
            }
            default -> {
                Logger.networkCritical(m.getType()+" management is not yet implemented.");
            }
        }
    }

    public HashMap<String, ClientConnection> getNickToConnectionMap() {
        HashMap<String, ClientConnection> temp;
        synchronized (nickToConnectionMap) {
            temp = new HashMap<>(nickToConnectionMap);
        }
        return temp;
    }
}
