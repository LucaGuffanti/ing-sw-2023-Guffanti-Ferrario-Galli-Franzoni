package it.polimi.ingsw.client.controller.commandHandlers;

import it.polimi.ingsw.client.controller.ClientPhasesEnum;
import it.polimi.ingsw.client.controller.exceptions.BadlyFormattedParametersException;
import it.polimi.ingsw.client.controller.exceptions.CommandNotAvailableInThisPhaseException;
import it.polimi.ingsw.client.controller.stateController.ClientState;
import it.polimi.ingsw.client.view.cli.Cli;
import it.polimi.ingsw.client.view.cli.cliviews.HelpView;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * This object handles the help command, and calls the render of the help view
 * @author Luca Guffanti, Daniele Ferrario
 */
public class HelpCommandHandler extends CliCommandHandler{
    /**
     * Game phases in which the command is available
     */
    private final HashSet<ClientPhasesEnum> availableStatuses = new HashSet<>(Arrays.asList(
            ClientPhasesEnum.LOGIN,
            ClientPhasesEnum.NOT_JOINED,
            ClientPhasesEnum.DECIDING_FOR_RELOAD,
            ClientPhasesEnum.LOBBY,
            ClientPhasesEnum.WAITING_FOR_TURN,
            ClientPhasesEnum.PICK_FORM_BOARD,
            ClientPhasesEnum.SELECT_COLUMN,
            ClientPhasesEnum.FINAL_RESULTS_SHOW

    ));

    /**
     * The label of the command: the string that should be inserted to invoke the command
     */
    public final static String commandLabel = "/help";
    /**
     * The description of the command
     */
    public final static String commandDescription = "Get every detail about the available commands";


    public HelpCommandHandler(Cli cli) {
        super(cli);
    }


    /**
     * After the correct checks are made, this method prints all the required details about other commands
     * @param commandInput The user's input
     * @param state the state of the client
     * @throws CommandNotAvailableInThisPhaseException thrown if the command is not available in a given phase
     */
    @Override
    public void execute(String commandInput, ClientState state) throws CommandNotAvailableInThisPhaseException {
        checkParameters(super.splitAndTrimInput(commandInput));
        super.getCli().renderCliView(new HelpView());
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
