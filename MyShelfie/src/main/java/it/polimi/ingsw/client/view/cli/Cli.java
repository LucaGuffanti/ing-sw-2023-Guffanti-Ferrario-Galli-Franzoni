package it.polimi.ingsw.client.view.cli;


import it.polimi.ingsw.client.controller.ClientPhasesEnum;
import it.polimi.ingsw.client.controller.commandHandlers.ViewChangeCommandHandler;
import it.polimi.ingsw.client.controller.exceptions.CliCommandException;
import it.polimi.ingsw.client.controller.exceptions.NoCommandFoundException;
import it.polimi.ingsw.client.controller.stateController.StateContainer;
import it.polimi.ingsw.client.view.UserInterface;
import it.polimi.ingsw.client.view.cli.cliviews.*;
import it.polimi.ingsw.client.controller.commandHandlers.CliCommandHandler;
import it.polimi.ingsw.client.controller.commandHandlers.PickFromBoardCommandHandler;
import it.polimi.ingsw.network.ClientNetworkHandler;
import it.polimi.ingsw.network.messages.Message;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.InputStream;
import java.util.Map;
import java.util.Scanner;

public class Cli extends UserInterface implements PropertyChangeListener {

    // For testing, simulate the input buffer of the user
    // @todo: no test written yet
    private InputStream systemIn;
    private final String EXIT_CMD_STR = "/exit";

    private StateContainer stateContainer;
    private ClientNetworkHandler networkHandler;


    private final Map<String, CliCommandHandler> inputCommandMap = Map.of(
            PickFromBoardCommandHandler.commandLabel, new PickFromBoardCommandHandler(this)
            //...
            //..
    );

    private final Map<ClientPhasesEnum, CliView> defaultViewsPerPhasesMap = Map.of(
        ClientPhasesEnum.LOGIN, new LoginView(),
        ClientPhasesEnum.LOBBY, new LobbyView(),
        ClientPhasesEnum.WAITING_FOR_TURN, new BoardView(),
        ClientPhasesEnum.PICK_FORM_BOARD, new PickFromBoardView(),
        ClientPhasesEnum.SELECT_COLUMN, new SelectColumnView(),
        ClientPhasesEnum.FINAL_RESULTS_SHOW, new EndGameResultsView()
    );

    public Cli(StateContainer stateContainer, ClientNetworkHandler networkHandler) {
        this.systemIn = System.in;
        this.stateContainer = stateContainer;
        this.networkHandler = networkHandler;
    }

    // For testing
    public Cli(InputStream inputStream, StateContainer stateContainer, ClientNetworkHandler networkHandler) {
        this.systemIn = inputStream;
        this.stateContainer = stateContainer;
        this.networkHandler = networkHandler;
    }



    // @TODO: IMPLEMENT THREADING
    @Override
    public void run() {


        String lastInput = null;
        Scanner scanner = new Scanner(this.systemIn);

        renderCurrentPhaseDefaultView();

        lastInput = scanner.nextLine();

        // Listen for inputs until \exit command has been inserted.
        while(!lastInput.equals(EXIT_CMD_STR)){

            try {
                evaluateUserInput(lastInput);
            }catch (CliCommandException e){
                // Prints the specific error
                e.printStackTrace();
            }

            lastInput = scanner.nextLine();
        }
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
        switch (evt.getPropertyName()){
            // When currentPhase is changed, always render the default view for the new currentPhase
            case "currentPhase":
                CliView cliView = defaultViewsPerPhasesMap.get((ClientPhasesEnum) evt.getNewValue());
                this.renderCliView(cliView);
        }
    }


    /**
     * Evaluate the user input, finds the correct CommandHandler for it
     * and execute the command.
     * Ex. "/pb 3 4 3 8" --> PickFromBoardCommandHandler
     *
     * The parameters will be checked in the handler
     *
     * @see CliCommandHandler
     * @param userInput User inserted input representing the desired command.
     */
    private void evaluateUserInput(String userInput) throws CliCommandException {

        String[] commandSubStrings = userInput.split(" ");
        if(!commandSubStrings[0].equals("/show")) {
            CliCommandHandler commandHandler = inputCommandMap.get(commandSubStrings[0]);
            if (commandHandler == null)
                throw new NoCommandFoundException();

            commandHandler.execute(userInput, stateContainer.getCurrentState());
            // this.dispatchMessageToNetwork or this.renderCliView will be called by the handler.
        }else{
            // @todo: cast to ViewChangeCommandHandler
            CliCommandHandler commandHandler = inputCommandMap.get(commandSubStrings[0]+" "+commandSubStrings[1]);
            if (commandHandler == null)
                throw new NoCommandFoundException();

            commandHandler.execute(userInput, stateContainer.getCurrentState());
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


}
