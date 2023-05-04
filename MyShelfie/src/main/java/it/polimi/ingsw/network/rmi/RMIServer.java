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
 */
public class RMIServer extends UnicastRemoteObject implements RMIServerInterface{
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

    @Override
    public void receiveMessage(Message message, RMIClientInterface rmiClientInterface) throws RemoteException {
        if (message.getType().equals(MessageType.LOGIN_REQUEST)) {
            login(message.getSenderUsername(), rmiClientInterface);
            return;
        }
        serverNetworkHandler.onMessageReceived(message);
    }

    @Override
    public void incomingPing(Message message, RMIClientInterface rmiClientInterface) throws RemoteException {
        Logger.pingerInfo("Getting a ping from RMI Client");
    }


    public void login(String username, RMIClientInterface rmiClientInterface) throws RemoteException {
        LoginResult result;
        Logger.networkInfo("Logging client from  RMI");
        result = serverNetworkHandler.onLoginRequest(username, new RMIClientConnection(rmiClientInterface));
        Logger.networkInfo("Calling message from server for the new client");

        if (result.isLogged() && !result.isReconnecting()) {
            rmiClientInterface.messageFromServer(new LoginResponseMessage(
                    ServerNetworkHandler.HOSTNAME,
                    ResponsesDescriptions.LOGIN_SUCCESS,
                    ResponseResultType.SUCCESS,
                    username
            ));
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
