package it.polimi.ingsw.client.controller.commandHandlers;

import it.polimi.ingsw.client.controller.ClientPhasesEnum;
import it.polimi.ingsw.client.controller.exceptions.BadlyFormattedParametersException;
import it.polimi.ingsw.client.controller.exceptions.CommandNotAvailableInThisPhaseException;
import it.polimi.ingsw.client.controller.messageHandling.messageHandlers.LoginHandler;
import it.polimi.ingsw.client.controller.stateController.ClientState;
import it.polimi.ingsw.client.view.cli.Cli;
import it.polimi.ingsw.network.messages.LoginRequestMessage;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class ChatCommandHandler extends CliCommandHandler{
    private final HashSet<ClientPhasesEnum> availablePhases = new HashSet<>(Arrays.asList(
            ClientPhasesEnum.LOGIN
    ));


    public final static String commandLabel = "/send";
    public final static String commandDescription = "Send a chat message to a user or in brodcast.\n\n" +
            "Usage: /send 'Hi every one' or\n/send 'Hi Pippo' --to Pippo";


    public ChatCommandHandler(Cli cli) {
        super(cli);
    }


    @Override
    public void execute(String commandInput, ClientState state) throws BadlyFormattedParametersException, CommandNotAvailableInThisPhaseException {

        List<String> parameters = Arrays.asList(commandInput.split(" "));

        if(!checkParameters(parameters)){
            throw new BadlyFormattedParametersException();
        }

        // @todo implement private message
        LoginRequestMessage msg;
        if(parameters.size() == 2)
            msg = LoginHandler.createMessage(parameters.get(1));
        else
            // @todo: Private message not implemented yet
            msg = LoginHandler.createMessage(parameters.get(2));

        super.getCli().dispatchMessageToNetwork(msg);
    }

    @Override
    protected boolean checkParameters(List<String> parameters) {

        return parameters.size() == 2 || (parameters.size() == 3 && parameters.get(2).equals("--to"));

    }

    public static String getCommandLabel() {
        return commandLabel;
    }

    public String getCommandDescription() {
        return commandDescription;
    }

}
