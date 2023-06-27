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
 *
 * @author Daniele Ferrario, Luca Guffanti
 */
public class ClientManager {
    /**
     * Client manager is a singleton, this is the instance of the client manager
     */
    public static ClientManager instance;
    /**
     * The state container
     */
    private StateContainer stateContainer;
    /**
     * The client-side network handler
     */
    private ClientNetworkHandler networkHandler;
    /**
     * The user interface, which can either be GUI or CLI
     */
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

    /**
     * Runs the user interface
     */
    public void runUI(){
        userInterface.run();
    }

    /**
     * This method handles the disconnection of the client, requiring a display of a message.
     */
    public void onDisconnection() {
        ClientPhasesEnum phase = stateContainer.getCurrentState().getCurrentPhase();
        if (!(phase.equals(ClientPhasesEnum.ALREADY_STARTED) || phase.equals(ClientPhasesEnum.NOT_ADMITTED))) {
            if (userInterface instanceof Gui) {
                System.out.println("Disconnections during phase " + phase);
                Gui.instance.onGameAborted();
            } else {
                userInterface.onGameAborted();
            }
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
