package it.polimi.ingsw.client.controller.commandHandlers;

import it.polimi.ingsw.client.controller.ClientPhasesEnum;
import it.polimi.ingsw.client.controller.exceptions.BadlyFormattedParametersException;
import it.polimi.ingsw.client.controller.exceptions.CommandNotAvailableInThisPhaseException;
import it.polimi.ingsw.client.controller.stateController.ClientState;
import it.polimi.ingsw.client.view.cli.Cli;
import it.polimi.ingsw.network.ClientNetworkHandler;
import it.polimi.ingsw.network.messages.FoundSavedGameMessage;
import it.polimi.ingsw.network.messages.FoundSavedGameResponseMessage;
import it.polimi.ingsw.network.messages.enums.ReloadGameChoice;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class ReloadGameCommandHandler extends CliCommandHandler {

    private final HashSet<ClientPhasesEnum> availablePhases = new HashSet<>(Arrays.asList(
            ClientPhasesEnum.DECIDING_FOR_RELOAD
    ));

    public final static String commandLabel = "/reload";
    public final static String commandDescription = "When asked, reload a saved game\n\n" +
            "Usage:\n"+"" +
            "/reload accept         to reload the game\n"+
            "/reload decline        to decline the reloading and start a new game from scratch";

    public ReloadGameCommandHandler(Cli cli) {
        super(cli);
    }

    @Override
    public void execute(String commandInput, ClientState state) throws BadlyFormattedParametersException, CommandNotAvailableInThisPhaseException {
        List<String> parameters = super.splitAndTrimInput(commandInput);

        if(!checkParameters(parameters)){
            throw new BadlyFormattedParametersException();
        }
        if(!super.checkAvailability(availablePhases, state)){
            throw new CommandNotAvailableInThisPhaseException();
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
