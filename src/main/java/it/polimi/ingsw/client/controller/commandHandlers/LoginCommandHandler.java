package it.polimi.ingsw.client.controller.commandHandlers;

import it.polimi.ingsw.client.controller.ClientPhasesEnum;
import it.polimi.ingsw.client.controller.exceptions.BadlyFormattedParametersException;
import it.polimi.ingsw.client.controller.exceptions.CommandNotAvailableInThisPhaseException;
import it.polimi.ingsw.client.controller.messageHandling.messageHandlers.LoginMessageHandler;
import it.polimi.ingsw.client.controller.stateController.ClientState;
import it.polimi.ingsw.client.view.cli.Cli;
import it.polimi.ingsw.network.messages.LoginRequestMessage;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

/**
 * Object that handles the login command execution
 * @author Daniele Ferrario, Luca Guffanti
 */
public class LoginCommandHandler extends CliCommandHandler{
    /**
     * Game phases in which the command is available
     */
    private final HashSet<ClientPhasesEnum> availablePhases = new HashSet<>(Arrays.asList(
            ClientPhasesEnum.LOGIN
    ));

    /**
     * The label of the command: the string that should be inserted to invoke the command
     */
    public final static String commandLabel = "/login";
    /**
     * The description of the command
     */
    public final static String commandDescription = "Login into a game.\n\n" +
            "Usage: /login USERNAME   Remember that you can't use escape codes as a username (\\n is not permitted)";


    public LoginCommandHandler(Cli cli) {
        super(cli);
    }

    /**
     * After the correct checks are made, this method submits the request of a player to log into the server
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

        System.out.println(commandInput);
        LoginRequestMessage msg = LoginMessageHandler.createMessage(commandInput);

        super.getCli().dispatchMessageToNetwork(msg);
    }

    @Override
    protected boolean checkParameters(List<String> parameters) {

        return parameters.size() >= 1 && !Objects.equals(parameters.get(0), "") && parameters.get(0).trim().charAt(0) != '\\';

    }

    public static String getCommandLabel() {
        return commandLabel;
    }

    public String getCommandDescription() {
        return commandDescription;
    }

}
