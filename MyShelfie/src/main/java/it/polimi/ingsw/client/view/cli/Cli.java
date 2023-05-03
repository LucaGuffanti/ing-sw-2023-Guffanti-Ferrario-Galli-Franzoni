package it.polimi.ingsw.client.view.cli;


import it.polimi.ingsw.client.controller.ClientPhasesEnum;
import it.polimi.ingsw.client.controller.commandHandlers.CliCommandHandler;
import it.polimi.ingsw.client.controller.stateController.StateContainer;
import it.polimi.ingsw.client.view.UserInterface;
import it.polimi.ingsw.client.view.cli.cliviews.*;
import it.polimi.ingsw.network.ClientNetworkHandler;
import it.polimi.ingsw.network.messages.Message;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.InputStream;
import java.util.Map;

public class Cli implements UserInterface, PropertyChangeListener {


    private StateContainer stateContainer;
    private ClientNetworkHandler networkHandler;

    private CommandPicker commandPicker;
    private CliView cliView = null;

    private final Map<ClientPhasesEnum, CliView> defaultViewsPerPhasesMap = Map.of(
        ClientPhasesEnum.LOGIN, new LoginView(),
        ClientPhasesEnum.NOT_JOINED, new NotJoinedView(),
        ClientPhasesEnum.LOBBY, new LobbyView(),
        ClientPhasesEnum.WAITING_FOR_TURN, new BoardView(),
        ClientPhasesEnum.PICK_FORM_BOARD, new PickFromBoardView(),
        ClientPhasesEnum.SELECT_COLUMN, new SelectColumnView(),
        ClientPhasesEnum.FINAL_RESULTS_SHOW, new EndGameResultsView()
    );

    public Cli(StateContainer stateContainer, ClientNetworkHandler networkHandler) {
        this.stateContainer = stateContainer;
        this.networkHandler = networkHandler;
        this.commandPicker = new CommandPicker(this, System.in);
        stateContainer.addPropertyChangeListener(this::propertyChange);
    }

    // For testing
    public Cli(InputStream inputStream, StateContainer stateContainer, ClientNetworkHandler networkHandler) {
        this.stateContainer = stateContainer;
        this.networkHandler = networkHandler;
        this.commandPicker = new CommandPicker(this, inputStream);
        stateContainer.addPropertyChangeListener(this::propertyChange);

    }


    /**
     * This method starts the cli by activating the thread which scans the input terminal for
     * user typed commands.
     */
    @Override
    public void run() {

        // Render default view, which should be LoginView when this command is invoked.
        renderCurrentPhaseDefaultView();

        Thread commandPickerThread = new Thread(commandPicker);
        System.out.println("Starting the command receiver thread");
        commandPickerThread.start();
    }

    /**
     * PropertyChangeListener overrode method.
     *
     * It this application, the method is only called when the ClientState in the StateContainer is updated.
     * Here, the cli will render a view depending on which attribute has been updated.
     *
     * @param evt A PropertyChangeEvent object describing the event source
     *          and the property that has changed.
     * @see StateContainer
     */

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            // When currentPhase is changed, always render the default view for the new currentPhase
            case "currentPhase" -> {
                CliView cliView = defaultViewsPerPhasesMap.get((ClientPhasesEnum) evt.getNewValue());
                // this is done to prevent the double printing of the list of players that would otherwise
                // be experienced by the player who creates the game
                if (!stateContainer.getCurrentState().getCurrentPhase().equals(ClientPhasesEnum.LOBBY)) {
                    this.renderCliView(cliView);
                }
            }
            case "serverErrorMessage" -> Printer.error((String) evt.getNewValue());
            case "serverLastMessage" -> Printer.log((String) evt.getNewValue());
            case "orderedPlayersNames" -> {
                if (stateContainer.getCurrentState().getCurrentPhase().equals(ClientPhasesEnum.LOBBY)) {
                    renderCurrentPhaseDefaultView();
                }
            }
            case "lastChatMessage" -> {
                if (cliView instanceof ChatView) {
                    ChatView cli = (ChatView) cliView;
                    cli.updateRender(stateContainer.getCurrentState());
                }
            }
        }
    }

    /**
     * Dispatch the message to the network handler.
     * Called after a Command handler execution.
     *
     * @param message
     * @see CliCommandHandler
     */
    public void dispatchMessageToNetwork(Message message){
        networkHandler.sendMessage(message);
    }

    /**
     * Render the requested view.
     * Called after a Command handler execution
     * or a state update that require a view change.
     *
     * @param view
     * @see CliCommandHandler
     * @see StateContainer
     */
    public void renderCliView(CliView view){
        view.render(stateContainer.getCurrentState());
        this.cliView = view;
    }


    /**
     * Renders the default view associated to the current clientPhase
     * described in ClientState
     *
     * @see it.polimi.ingsw.client.controller.stateController.ClientState
     */
    private void renderCurrentPhaseDefaultView(){
        CliView cliView = defaultViewsPerPhasesMap.get(stateContainer.getCurrentState().getCurrentPhase());
        cliView.render(stateContainer.getCurrentState());
    }

    public StateContainer getStateContainer() {
        return stateContainer;
    }

    public CliView getCliView() {
        return cliView;
    }

    public void setCliView(CliView cliView) {
        this.cliView = cliView;
    }
}
