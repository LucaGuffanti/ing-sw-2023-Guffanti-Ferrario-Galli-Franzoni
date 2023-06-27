package it.polimi.ingsw.client.controller.commandHandlers;

import it.polimi.ingsw.client.controller.ClientPhasesEnum;
import it.polimi.ingsw.client.controller.exceptions.BadlyFormattedParametersException;
import it.polimi.ingsw.client.controller.exceptions.CommandNotAvailableInThisPhaseException;
import it.polimi.ingsw.client.controller.stateController.ClientState;
import it.polimi.ingsw.client.view.cli.Cli;
import it.polimi.ingsw.network.messages.FoundSavedGameResponseMessage;
import it.polimi.ingsw.network.messages.enums.ReloadGameChoice;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * This handler manages the decision of reloading a saved game when found.
 * @author Luca Guffanti
 */
public class ReloadGameCommandHandler extends CliCommandHandler {

    /**
     * Game phases in which the command is available
     */
    private final HashSet<ClientPhasesEnum> availablePhases = new HashSet<>(Arrays.asList(
            ClientPhasesEnum.DECIDING_FOR_RELOAD
    ));
    /**
     * The label of the command: the string that should be inserted to invoke the command
     */
    public final static String commandLabel = "/reload";
    /**
     * The description of the command
     */
    public final static String commandDescription = "When asked, reload a saved game\n\n" +
            "Usage:\n"+"" +
            "/reload accept         to reload the game\n"+
            "/reload decline        to decline the reloading and start a new game from scratch";

    public ReloadGameCommandHandler(Cli cli) {
        super(cli);
    }

    /**
     * After the correct checks are made, this method submits the result of a reload request
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

        ReloadGameChoice choice;

        if (parameters.get(0).equals("accept")) {
            choice = ReloadGameChoice.ACCEPT_RELOAD;
        } else {
            choice = ReloadGameChoice.DECLINE_RELOAD;
        }

        super.getCli().dispatchMessageToNetwork(new FoundSavedGameResponseMessage(
                state.getUsername(),
                choice
        ));
    }

    @Override
    protected boolean checkParameters(List<String> parameters) {
        return parameters.size() == 1 && parameters.get(0).equals("accept") || parameters.get(0).equals("decline");
    }

    @Override
    public String getCommandDescription() {
        return null;
    }
}
