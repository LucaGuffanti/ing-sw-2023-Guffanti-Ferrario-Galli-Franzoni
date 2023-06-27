package it.polimi.ingsw.client.controller.commandHandlers;

import it.polimi.ingsw.client.controller.ClientPhasesEnum;
import it.polimi.ingsw.client.controller.exceptions.BadlyFormattedParametersException;
import it.polimi.ingsw.client.controller.exceptions.CommandNotAvailableInThisPhaseException;
import it.polimi.ingsw.client.controller.messageHandling.messageHandlers.JoinGameMessageHandler;
import it.polimi.ingsw.client.controller.stateController.ClientState;
import it.polimi.ingsw.client.view.cli.Cli;
import it.polimi.ingsw.network.messages.JoinGameMessage;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * Cli command to join the game after a successful login
 */
public class JoinGameCommandHandler extends CliCommandHandler{
    /**
     * Game phases in which the command is available
     */
    private final HashSet<ClientPhasesEnum> availablePhases = new HashSet<>(Arrays.asList(
            ClientPhasesEnum.NOT_JOINED
    ));

    /**
     * The label of the command: the string that should be inserted to invoke the command
     */
    public final static String commandLabel = "/join";
    /**
     * The description of the command
     */
    public final static String commandDescription = "Join the current game on server\n\n" +
            "Usage: /join";


    public JoinGameCommandHandler(Cli cli) {
        super(cli);
    }

    /**
     * After the correct checks are made, this method executes the request of joining a game
     * @param commandInput The user's input
     * @param state the state of the client
     * @throws CommandNotAvailableInThisPhaseException thrown if the command is not available in a given phase
     * @throws BadlyFormattedParametersException thrown if the command presents badly formatted parameters
     * */
    @Override
    public void execute(String commandInput, ClientState state) throws BadlyFormattedParametersException, CommandNotAvailableInThisPhaseException {

        List<String> parameters = super.splitAndTrimInput(commandInput);
        if(!super.checkAvailability(availablePhases, state)){
            throw new CommandNotAvailableInThisPhaseException();
        }
        if(!checkParameters(parameters)){
            throw new BadlyFormattedParametersException();
        }
        JoinGameMessage msg = JoinGameMessageHandler.createMessage(state.getUsername());

        super.getCli().dispatchMessageToNetwork(msg);
    }

    @Override
    protected boolean checkParameters(List<String> parameters) {
        return parameters.get(0).equals("");
    }

    public static String getCommandLabel() {
        return commandLabel;
    }

    public String getCommandDescription() {
        return commandDescription;
    }

}
