package it.polimi.ingsw.client.view.cli;


import it.polimi.ingsw.client.controller.ClientPhasesEnum;
import it.polimi.ingsw.client.controller.stateController.StateContainer;
import it.polimi.ingsw.client.view.UserInterface;
import it.polimi.ingsw.client.view.cli.cliviews.*;
import it.polimi.ingsw.client.view.cli.commandHandlers.CliCommandHandler;
import it.polimi.ingsw.client.view.cli.commandHandlers.PickFromBoardCommandHandler;
import it.polimi.ingsw.network.ClientNetworkHandler;
import it.polimi.ingsw.network.messages.Message;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.InputStream;
import java.util.Map;
import java.util.Scanner;

public class Cli extends UserInterface implements PropertyChangeListener {

    private InputStream systemIn;
    private final String EXIT_CMD_STR = "/exit";

    private StateContainer stateContainer;
    private ClientNetworkHandler networkHandler;

    private CliView currentView;

    private final Map<String, CliCommandHandler> inputCommandMap = Map.of(
            PickFromBoardCommandHandler.commandLabel, new PickFromBoardCommandHandler(this)
            //...
            //..
    );

    private final Map<ClientPhasesEnum, CliView> defaultViewsPerPhasesMap = Map.of(
        ClientPhasesEnum.DISCONNECTED, new ConnectionView(),
        ClientPhasesEnum.CONNECTED, new ConnectionView(),
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
        this.currentView = defaultViewsPerPhasesMap.get(stateContainer.getCurrentState().getCurrentPhase());
    }

    // For testing
    public Cli(InputStream inputStream, StateContainer stateContainer, ClientNetworkHandler networkHandler) {
        this.systemIn = inputStream;
        this.stateContainer = stateContainer;
        this.networkHandler = networkHandler;
        this.currentView = defaultViewsPerPhasesMap.get(stateContainer.getCurrentState().getCurrentPhase());
    }



    // @TODO: IMPLEMENT THREADING
    @Override
    public void run() {


        String lastInput = null;
        Scanner scanner = new Scanner(this.systemIn);

        currentView.render(stateContainer.getCurrentState());
        lastInput = scanner.nextLine();
        while(!lastInput.equals(EXIT_CMD_STR)){

            evaluateUserInput(lastInput);
            lastInput = scanner.nextLine();
            currentView.render(stateContainer.getCurrentState());

        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()){
            // When currentPhase is changed, render the default view for the new currentPhase
            case "currentPhase":
                currentView = defaultViewsPerPhasesMap.get((ClientPhasesEnum) evt.getNewValue());
                currentView.render(stateContainer.getCurrentState());

        }
    }


    private void evaluateUserInput(String userInput){

        //
        //  @todo: get the CLiCommandHandler given the userInput. If there are no matches raise NoCommandFoundException
        //
        CliCommandHandler commandHandler = inputCommandMap.get("/pb"); // @todo: to implement

        commandHandler.execute(userInput, stateContainer.getCurrentState()); // this.handleCommandResponse will be called with the result.
    }

    /**
     * Dispatch the message to the network handler.
     * Called after a Command handler execution.
     *
     * @param message
     * @see CliCommandHandler
     */
    public void handleCommandResponse(Message message){
        networkHandler.sendMessage(message);
    }

    /**
     * Render the requested view.
     * Called after a Command handler execution.
     *
     * @param view
     * @see CliCommandHandler
     */
    public void handleCommandResponse(CliView view){
        currentView = view;
        currentView.render(stateContainer.getCurrentState());
    }


}
