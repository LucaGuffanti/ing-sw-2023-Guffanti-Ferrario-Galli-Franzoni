package it.polimi.ingsw.client.view.cli;


import it.polimi.ingsw.client.controller.ClientPhasesEnum;
import it.polimi.ingsw.client.controller.commandHandlers.CliCommandHandler;
import it.polimi.ingsw.client.controller.stateController.StateContainer;
import it.polimi.ingsw.client.view.UserInterface;
import it.polimi.ingsw.client.view.cli.cliviews.*;
import it.polimi.ingsw.network.ClientNetworkHandler;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.rmi.RMIClient;
import it.polimi.ingsw.network.socket.SocketClient;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cli implements UserInterface, PropertyChangeListener {

    /**
     * The container of the state of the client
     */
    private final StateContainer stateContainer;

    /**
     * The network handler. {@link ClientNetworkHandler} is an abstract class that specializes as a {@link SocketClient}
     * or as a {@link RMIClient}
     */
    private final ClientNetworkHandler networkHandler;

    /**
     * Boolean containing whether the list of the players has already been printed. Used for correctly printing the list
     * of joined players
     */
    private boolean playersAlreadyPrinted = false;

    /**
     * The number of players who joined the lobby. Used for correctly printing the list
     * of joined players
     */
    private int numOfPlayers = 0;
    private boolean firstTurn = true;
    private final CommandPicker commandPicker;
    private CliView cliView = null;


    private Map<ClientPhasesEnum, CliView> defaultViewsPerPhasesMap;

    public Cli(StateContainer stateContainer, ClientNetworkHandler networkHandler) {
        this.stateContainer = stateContainer;
        this.networkHandler = networkHandler;
        this.commandPicker = new CommandPicker(this, System.in);
        this.defaultViewsPerPhasesMap = new HashMap<>();

        defaultViewsPerPhasesMap.put(ClientPhasesEnum.LOGIN, new LoginView());
        defaultViewsPerPhasesMap.put(ClientPhasesEnum.NOT_JOINED, new NotJoinedView());
        defaultViewsPerPhasesMap.put(ClientPhasesEnum.PICK_PLAYERS, new PickPlayersView());
        defaultViewsPerPhasesMap.put(ClientPhasesEnum.WAITING_FOR_LOBBY, new WaitingForLobbyView());
        defaultViewsPerPhasesMap.put(ClientPhasesEnum.LOBBY, new LobbyView());
        defaultViewsPerPhasesMap.put(ClientPhasesEnum.DECIDING_FOR_RELOAD, new ReloadDecisionView());
        defaultViewsPerPhasesMap.put(ClientPhasesEnum.WAITING_FOR_TURN, new WaitingForTurnView());
        defaultViewsPerPhasesMap.put(ClientPhasesEnum.PICK_FORM_BOARD, new PickFromBoardView());
        defaultViewsPerPhasesMap.put(ClientPhasesEnum.SELECT_COLUMN, new SelectColumnView());
        defaultViewsPerPhasesMap.put(ClientPhasesEnum.ABORTED_GAME, new GameAbortedView());
        defaultViewsPerPhasesMap.put(ClientPhasesEnum.FINAL_RESULTS_SHOW, new EndGameResultsView());

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
        commandPickerThread.start();
        Printer.title("YOU CAN NOW WRITE AND SUBMIT COMMANDS");
    }

    @Override
    public void onGameAborted() {
        CliView cli = new GameAbortedView();
        cli.render(null);
    }

    @Override
    public void printErrorMessage(String msg) {
        Printer.error(msg);
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

        ClientPhasesEnum currentPhase = stateContainer.getCurrentState().getCurrentPhase();
        List<String> players = stateContainer.getCurrentState().getOrderedPlayersNames();
        String username = stateContainer.getCurrentState().getUsername();


        switch (evt.getPropertyName()) {
            // When the current phase changes the cli view should not be rendered by default. Instead
            // the last server message (containing the result of the previously ended turn) is displayed and
            // if the pick from board or wait for turn phase is reached for the first time the ORDERED LIST OF
            // PLAYERS is printed.
            case "currentPhase" -> {
                CliView cliView = defaultViewsPerPhasesMap.get((ClientPhasesEnum) evt.getNewValue());

                // this is done to prevent the double printing of the list of players that would otherwise
                // be experienced by the player who creates the game
                if (currentPhase.equals(ClientPhasesEnum.PICK_FORM_BOARD)) {
                    Printer.boldsSubtitle(stateContainer.getCurrentState().getServerLastMessage());
                }


                // if the pick from board || waiting for turn is reached after the game starts, the list of players is printed.
                if ((currentPhase.equals(ClientPhasesEnum.PICK_FORM_BOARD) || currentPhase.equals(ClientPhasesEnum.WAITING_FOR_TURN)) &&
                        (playersAlreadyPrinted || players.size() == 2) && firstTurn) {
                    firstTurn = false;
                    Printer.title("ORDERED PLAYERS");
                    for (String player : players) {
                        if (player.equals(username)) {
                            Printer.printHighlightedPlayerName(player + " <- that's you!");
                        } else {
                            Printer.printPlayerName(player);
                        }
                    }
                    Printer.title("====================");
                }

                if (!(currentPhase.equals(ClientPhasesEnum.LOBBY) ||
                        currentPhase.equals(ClientPhasesEnum.WAITING_FOR_TURN))) {
                    this.renderCliView(cliView);
                }
            }
            // A server error message change event is caught and has the maximum priority to be displayed.
            case "serverErrorMessage" -> Printer.error((String) evt.getNewValue());
            // The players list can change in two ways: either the number logged players increases (in which case the
            // players list in the lobby is displayed) or the order of players changes (this means that the game has started,
            // and the list of ORDERED PLAYERS will be printed when the change of phase is caught)
            case "orderedPlayersNames" -> {
                if (currentPhase.equals(ClientPhasesEnum.LOBBY) && (!playersAlreadyPrinted || numOfPlayers != players.size())) {
                    // this means that the orderedPlayers names changed in number
                    renderCurrentPhaseDefaultView();
                    playersAlreadyPrinted = true;
                    numOfPlayers = players.size();
                }
            }
            // When a chat message is received, if the chat view is being shown, only the last chat message
            // is displayed
            case "lastChatMessage" -> {
                if (cliView instanceof ChatView) {
                    ChatView cli = (ChatView) cliView;
                    cli.updateRender(stateContainer.getCurrentState());
                }
            }
            // The active player changed, an apposite message is prompted.
            case "activePlayer" -> {
                if (currentPhase.equals(ClientPhasesEnum.WAITING_FOR_TURN)) {
                    Printer.boldsSubtitle(stateContainer.getCurrentState().getServerLastMessage());
                    WaitingForTurnView wView = (WaitingForTurnView) defaultViewsPerPhasesMap.get(ClientPhasesEnum.WAITING_FOR_TURN);
                    wView.render(stateContainer.getCurrentState());
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
     * @param view the view that will be rendered
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
