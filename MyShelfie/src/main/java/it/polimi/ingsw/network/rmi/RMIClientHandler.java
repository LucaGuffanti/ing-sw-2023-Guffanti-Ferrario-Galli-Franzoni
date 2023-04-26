package it.polimi.ingsw.network.rmi;

import it.polimi.ingsw.network.ClientConnection;
import it.polimi.ingsw.network.LoginResult;
import it.polimi.ingsw.network.ServerNetworkHandler;
import it.polimi.ingsw.network.messages.LoginRequestMessage;
import it.polimi.ingsw.network.messages.LoginResponseMessage;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.enums.MessageType;
import it.polimi.ingsw.network.messages.enums.ResponseResultType;
import it.polimi.ingsw.network.utils.ResponsesDescriptions;

import java.io.Serial;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RMIClientHandler extends UnicastRemoteObject implements RMIServerInterface, Serializable {
    private final RMIServer rmiServer;
    public RMIClientHandler(RMIServer rmiServer) throws RemoteException {
        this.rmiServer = rmiServer;
    }

    @Override
    public void receiveMessage(Message message) throws RemoteException {
        if (message.getType().equals(MessageType.LOGIN_REQUEST)){
            //   login();
        }
        rmiServer.getServerNetworkHandler().onMessageReceived(message);

    }

    @Override
    public void login(String username, ClientConnection connection, RMIClientInterface rmiClientInterface) throws RemoteException {
        LoginResult result;

        result = rmiServer.getServerNetworkHandler().onLoginRequest(username, connection);
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
