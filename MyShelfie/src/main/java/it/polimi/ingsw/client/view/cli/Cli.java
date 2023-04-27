package it.polimi.ingsw.client.view.cli;


import it.polimi.ingsw.client.controller.ClientPhasesEnum;
import it.polimi.ingsw.client.controller.stateController.StateContainer;
import it.polimi.ingsw.client.view.UserInterface;
import it.polimi.ingsw.client.view.cli.cliviews.CliView;
import it.polimi.ingsw.client.view.cli.commandHandlers.CliCommandHandler;
import it.polimi.ingsw.client.view.cli.commandHandlers.PickFromBoardCommandHandler;
import it.polimi.ingsw.network.ClientNetworkHandler;
import it.polimi.ingsw.network.messages.Message;

import java.util.Map;
import java.util.Scanner;

public class Cli extends UserInterface {

    private final String EXIT_CMD_STR = "/exit";

    private StateContainer stateContainer;
    private ClientNetworkHandler networkHandler;
    private final Map<String, Class<? extends CliCommandHandler>> inputCommandMap = Map.of(
            PickFromBoardCommandHandler.commandLabel, PickFromBoardCommandHandler.class
            //...
            //..
    );

    //@todo: Complete the commands
    private Map<ClientPhasesEnum, CliView> defaultViewPerStatusMap;

    public Cli(StateContainer stateContainer, ClientNetworkHandler networkHandler) {
        this.stateContainer = stateContainer;
        this.networkHandler = networkHandler;
    }


    @Override
    public void run() {
        String lastInput = null;
        Scanner scanner = new Scanner(System.in);

        lastInput = scanner.nextLine();
        while(!lastInput.equals(EXIT_CMD_STR)){

            evaluateUserInput(lastInput);
            lastInput = scanner.nextLine();

        }
    }

    private void evaluateUserInput(String userInput){

        //
        //  @todo: get the CLiCommandHandler given the userInput. If there are no matches raise NoCommandFoundException
        //
        CliCommandHandler commandHandler = null; // @todo: to implement

        commandHandler.execute(userInput); // this.handleCommandResponse will be called with the result.
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
     * Change the current view.
     * Called after a Command handler execution.
     *
     * @param view
     * @see CliCommandHandler
     */
    public void handleCommandResponse(CliView view){

    }
}
