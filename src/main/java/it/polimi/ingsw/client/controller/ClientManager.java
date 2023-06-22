package it.polimi.ingsw.client.controller;

import it.polimi.ingsw.client.controller.constants.UIModesEnum;
import it.polimi.ingsw.client.controller.stateController.ClientState;
import it.polimi.ingsw.client.controller.stateController.StateContainer;
import it.polimi.ingsw.client.view.gui.Gui;
import it.polimi.ingsw.client.view.UserInterface;
import it.polimi.ingsw.client.view.cli.Cli;
import it.polimi.ingsw.network.ClientNetworkHandler;
import it.polimi.ingsw.network.rmi.RMIClient;
import it.polimi.ingsw.network.socket.SocketClient;

import java.rmi.RemoteException;

/**
 * The ClientManager acts as a controller layer above the UserInterface. Here
 * there are the methods to initialize the different UIs.
 */
public class ClientManager {
    public static ClientManager instance;
    private StateContainer stateContainer;
    private ClientNetworkHandler networkHandler;
    private UserInterface userInterface;


    public ClientManager(UIModesEnum uiMode, String serverIp, int serverPort) throws RemoteException {
        instance = this;
        ClientState initialState = new ClientState();
        initialState.setCurrentPhase(ClientPhasesEnum.LOGIN);
        stateContainer = new StateContainer(initialState);
        networkHandler = new SocketClient(serverIp, serverPort, stateContainer, this);
        userInterface = uiMode.equals(UIModesEnum.CLI) ? new Cli(stateContainer, networkHandler) : new Gui();
    }

    public ClientManager(UIModesEnum uiMode, String serverIp, String serviceName, int serverPort) throws RemoteException {
        instance = this;
        ClientState initialState = new ClientState();
        initialState.setCurrentPhase(ClientPhasesEnum.LOGIN);
        stateContainer = new StateContainer(initialState);
        networkHandler = new RMIClient(serviceName, serverIp , serverPort, stateContainer, this);
        userInterface = uiMode.equals(UIModesEnum.CLI) ? new Cli(stateContainer, networkHandler) : new Gui();
    }

    // Constructor with initial state to init externally --------------------
    public ClientManager(UIModesEnum uiMode, String serverIp, int serverPort, StateContainer stateContainer) throws RemoteException {
        instance = this;
        this.stateContainer = stateContainer;
        networkHandler = new SocketClient(serverIp, serverPort, stateContainer, this);
        userInterface = uiMode.equals(UIModesEnum.CLI) ? new Cli(stateContainer, networkHandler) : new Gui();
    }

    public ClientManager(UIModesEnum uiMode, String serverIp, String serviceName, int serverPort, StateContainer stateContainer) throws RemoteException {
        instance = this;
        this.stateContainer = stateContainer;
        networkHandler = new RMIClient(serviceName, serverIp , serverPort, stateContainer, this);
        userInterface = uiMode.equals(UIModesEnum.CLI) ? new Cli(stateContainer, networkHandler) : new Gui();
    }
    // ------------------------------------------------------------------------

    public void runUI(){
        userInterface.run();
    }


    public void onDisconnection() {
        if (userInterface instanceof Gui) {
            Gui.instance.onGameAborted();
        } else {
            userInterface.onGameAborted();
        }
    }

    public StateContainer getStateContainer() {
        return stateContainer;
    }

    public ClientNetworkHandler getNetworkHandler() {
        return networkHandler;
    }

    public UserInterface getUserInterface() {
        return userInterface;
    }

    public static ClientManager getInstance() {
        return instance;
    }
}
