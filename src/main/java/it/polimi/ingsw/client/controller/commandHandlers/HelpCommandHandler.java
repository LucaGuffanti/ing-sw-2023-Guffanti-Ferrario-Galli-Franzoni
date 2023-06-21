package it.polimi.ingsw.client.controller.commandHandlers;

import it.polimi.ingsw.client.controller.ClientPhasesEnum;
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
    private final HashSet<ClientPhasesEnum> availableStatues = new HashSet<>(Arrays.asList(
            ClientPhasesEnum.LOGIN,
            ClientPhasesEnum.NOT_JOINED,
            ClientPhasesEnum.DECIDING_FOR_RELOAD,
            ClientPhasesEnum.LOBBY,
            ClientPhasesEnum.WAITING_FOR_TURN,
            ClientPhasesEnum.PICK_FORM_BOARD,
            ClientPhasesEnum.SELECT_COLUMN,
            ClientPhasesEnum.FINAL_RESULTS_SHOW

    ));


    public final static String commandLabel = "/help";
    public final static String commandDescription = "Get every detail about the available commands";


    public HelpCommandHandler(Cli cli) {
        super(cli);
    }


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
