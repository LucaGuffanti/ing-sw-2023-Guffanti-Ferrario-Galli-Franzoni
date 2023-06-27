package it.polimi.ingsw.client.controller.commandHandlers;

import it.polimi.ingsw.client.controller.ClientPhasesEnum;
import it.polimi.ingsw.client.controller.exceptions.BadlyFormattedParametersException;
import it.polimi.ingsw.client.controller.exceptions.CommandNotAvailableInThisPhaseException;
import it.polimi.ingsw.client.controller.stateController.ClientState;
import it.polimi.ingsw.client.view.cli.Cli;
import it.polimi.ingsw.network.messages.SelectColumnMessage;

import java.util.*;

/**
 * This handler manages the command regarding the selection of the column after tiles are picked from the board
 * @author Daniele Ferrario
 */
public class SelectColumnCommandHandler extends CliCommandHandler{
    /**
     * Game phases in which the command is available
     */
    private final HashSet<ClientPhasesEnum> availablePhases = new HashSet<>(Arrays.asList(
            ClientPhasesEnum.SELECT_COLUMN
    ));

    /**
     * The label of the command: the string that should be inserted to invoke the command
     */
    public final static String commandLabel = "/sc";
    /**
     * The description of the command
     */
    public final static String commandDescription = "Select shelf's column command\n\n" +
            "Usage: "+commandLabel+" x where x is the number of the column. 0 <= x <= 4";


    public SelectColumnCommandHandler(Cli cli) {
        super(cli);
    }

    /**
     * After the correct checks are made, this method submits a column selection to the server
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

        SelectColumnMessage msg = new SelectColumnMessage(state.getUsername(), Integer.parseInt(parameters.get(0)));

        super.getCli().dispatchMessageToNetwork(msg);
    }

    @Override
    protected boolean checkParameters(List<String> parameters) {
        if(parameters.size() != 1)
            return false;

        try {

            Integer.parseInt(parameters.get(0));
            // value is an Integer
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public static String getCommandLabel() {
        return commandLabel;
    }

    public String getCommandDescription() {
        return commandDescription;
    }

}
