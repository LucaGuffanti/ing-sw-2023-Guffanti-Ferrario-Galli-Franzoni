package it.polimi.ingsw.network.rmi;

import it.polimi.ingsw.network.ClientConnection;
import it.polimi.ingsw.network.LoginResult;
import it.polimi.ingsw.network.ServerNetworkHandler;
import it.polimi.ingsw.network.messages.LoginResponseMessage;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.enums.MessageType;
import it.polimi.ingsw.network.messages.enums.ResponseResultType;
import it.polimi.ingsw.network.utils.Logger;
import it.polimi.ingsw.network.utils.ResponsesDescriptions;
import it.polimi.ingsw.network.utils.exceptions.NotYetImplementedException;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Map;

/**
 * The RMI server. it can be called by each client.
 * @author Luca Guffanti
 */
public class RMIServer extends UnicastRemoteObject implements RMIServerInterface{
    /**
     * The server network handler
     */
    ServerNetworkHandler serverNetworkHandler;
    public RMIServer(String RMIServiceName, String ip, int port, ServerNetworkHandler serverNetworkHandler) throws RemoteException {
        super();
        Logger.networkInfo("RMI Registry will be at "+ "rmi://"+ip+":"+port+"/"+RMIServiceName);
        this.serverNetworkHandler = serverNetworkHandler;
        Registry registry = LocateRegistry.createRegistry(port);
        registry.rebind(RMIServiceName, this);
        Logger.networkInfo("Rmi server bound");
    }

    public ServerNetworkHandler getServerNetworkHandler() {
        return serverNetworkHandler;
    }

    /**
     * Method remotely called by a client to send a non-ping message to the server
     * @param message message sent by the client
     * @param rmiClientInterface interface of the client
     * @throws RemoteException thrown if there are connection issues
     */
    @Override
    public void receiveMessage(Message message, RMIClientInterface rmiClientInterface) throws RemoteException {
        if (message.getType().equals(MessageType.LOGIN_REQUEST)) {
            login(message.getSenderUsername(), rmiClientInterface);
            return;
        }
        serverNetworkHandler.onMessageReceived(message);
    }

    /**
     * This message is called by the client to check that the server is online.
     * @param message ping message sent from the client
     * @param rmiClientInterface the interface of the client
     * @throws RemoteException thrown if there are connection issues
     */
    @Override
    public void incomingPing(Message message, RMIClientInterface rmiClientInterface) throws RemoteException {
        Logger.pingerInfo("Getting a ping from RMI Client");
    }

    @Override
    public void onClientDisconnection(String username, RMIClientInterface rmiClientInterface) throws RemoteException {
        Logger.networkCritical("GOT DISCONNECTION REQUEST FROM " + username);
        serverNetworkHandler.onDisconnection(serverNetworkHandler.getNickToConnectionMap().get(username));
    }

    /**
     * This method is called when a client wants to log into the server. A login request can yield a positive or negative
     * result, which is sent to the client.
     * @param username the requested username
     * @param rmiClientInterface the client interface used for callback operations
     * @throws RemoteException thrown if there are connection issues
     */
    public void login(String username, RMIClientInterface rmiClientInterface) throws RemoteException {
        LoginResult result;
        Logger.networkInfo("Logging client from  RMI");
        result = serverNetworkHandler.onLoginRequest(username, new RMIClientConnection(rmiClientInterface, serverNetworkHandler));
        Logger.networkInfo("Calling message from server for the new client");

        if (result.isLogged() && !result.isReconnecting()) {
            rmiClientInterface.messageFromServer(new LoginResponseMessage(
                    ServerNetworkHandler.HOSTNAME,
                    ResponsesDescriptions.LOGIN_SUCCESS,
                    ResponseResultType.SUCCESS,
                    username
            ));
            serverNetworkHandler.onNewLogin(username);
        }
        else if (result.isLogged() && result.isReconnecting()) {
            rmiClientInterface.messageFromServer(new LoginResponseMessage(
                    ServerNetworkHandler.HOSTNAME,
                    ResponsesDescriptions.RECONNECTION_SUCCESS,
                    ResponseResultType.SUCCESS,
                    username
            ));
        }
        else {
            rmiClientInterface.messageFromServer(new LoginResponseMessage(
                    ServerNetworkHandler.HOSTNAME,
                    ResponsesDescriptions.LOGIN_FAILURE_ALREADY_TAKEN,
                    ResponseResultType.FAILURE,
                    username
            ));
        }
    }
}
