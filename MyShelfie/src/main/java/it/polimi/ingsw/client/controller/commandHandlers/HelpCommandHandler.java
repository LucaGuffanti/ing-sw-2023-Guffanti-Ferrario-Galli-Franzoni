package it.polimi.ingsw.client.controller.commandHandlers;

import it.polimi.ingsw.client.controller.ClientPhasesEnum;
import it.polimi.ingsw.client.controller.stateController.ClientState;
import it.polimi.ingsw.client.view.cli.Cli;
import it.polimi.ingsw.client.view.cli.cliviews.HelpView;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class HelpCommandHandler extends CliCommandHandler{
    private final HashSet<ClientPhasesEnum> availableStatues = new HashSet<>(Arrays.asList(
            ClientPhasesEnum.PICK_FORM_BOARD,
            ClientPhasesEnum.SELECT_COLUMN
            //..@todo: Add every phase
    ));


    public final static String commandLabel = "/show help";
    public final static String commandDescription = "Get every details about the available commands";


    public HelpCommandHandler(Cli cli) {
        super(cli);
    }


    @Override
    public void execute(String commandInput, ClientState state) {

        super.getCli().renderCliView(new HelpView());
    }

    @Override
    protected boolean checkParameters(List<String> parameters) {

        return true;
    }

    public static String getCommandLabel() {
        return commandLabel;
    }

    public String getCommandDescription() {
        return commandDescription;
    }

}
