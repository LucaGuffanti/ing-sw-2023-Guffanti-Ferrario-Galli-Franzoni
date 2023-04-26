package it.polimi.ingsw.client.view.cli;


import it.polimi.ingsw.client.controller.stateController.StateContainer;
import it.polimi.ingsw.client.view.UserInterface;
import it.polimi.ingsw.client.view.cli.cliviews.CliView;
import it.polimi.ingsw.client.view.cli.cliviews.ClientStatusEnum;
import it.polimi.ingsw.client.view.cli.commandHandlers.CliCommandHandler;
import it.polimi.ingsw.network.ClientNetworkHandler;
import it.polimi.ingsw.network.messages.Message;

import java.util.Map;

public class Cli extends UserInterface {
        private StateContainer stateContainer;
        private ClientNetworkHandler networkHandler;
        private Map<String, CliCommandHandler> inputCommandMap;
        //@todo make the map final, choose the commands
        private Map<ClientStatusEnum, CliView> defaultViewPerStatusMap;

        public Cli(StateContainer stateContainer, ClientNetworkHandler networkHandler, Map<String, CliCommandHandler> inputCommandMap) {
            this.stateContainer = stateContainer;
            this.networkHandler = networkHandler;
        }


    @Override
    public void run() {

    }

    private void evaluateUserInput(String userInput){

        //
        //  @todo: get the CLiCommandHandler given the userInput. If there are no matches raise NoCommandFoundException
        //
        CliCommandHandler commandHandler = null; // @todo: to implement

        commandHandler.execute(userInput); // this.handleCommandResponse will be called with the result.
    }

    private void handleCommandResponse(Message message){

    }
    private void handleCommandResponse(CliView view){

    }
}
