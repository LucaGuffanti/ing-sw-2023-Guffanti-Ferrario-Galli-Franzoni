package it.polimi.ingsw.client.view.cli.cliviews;

import it.polimi.ingsw.client.controller.commandHandlers.*;
import it.polimi.ingsw.client.controller.stateController.ClientState;
import it.polimi.ingsw.client.view.cli.Printer;

import java.util.HashMap;

public class HelpView implements CliView {
    private static final HashMap<String, String> inputCommandMapToDescription = new HashMap<>();

    static {
        inputCommandMapToDescription.put(LoginCommandHandler.commandLabel, LoginCommandHandler.commandDescription);
        inputCommandMapToDescription.put(JoinGameCommandHandler.commandLabel, JoinGameCommandHandler.commandDescription);
        inputCommandMapToDescription.put(PlayersNumberCommandHandler.commandLabel, PlayersNumberCommandHandler.commandDescription);
        inputCommandMapToDescription.put(PickFromBoardCommandHandler.commandLabel, PickFromBoardCommandHandler.commandDescription);
        inputCommandMapToDescription.put(ShowViewCommandHandler.commandLabel, ShowViewCommandHandler.commandDescription);
        inputCommandMapToDescription.put(SelectColumnCommandHandler.commandLabel, SelectColumnCommandHandler.commandDescription);
        inputCommandMapToDescription.put(ChatCommandHandler.commandLabel, ChatCommandHandler.commandDescription);
        inputCommandMapToDescription.put(HelpCommandHandler.commandLabel, HelpCommandHandler.commandDescription);
        inputCommandMapToDescription.put(ReloadGameCommandHandler.commandLabel, ReloadGameCommandHandler.commandDescription);

    }
    @Override
    public void render(ClientState state) {
        Printer.title("HELP PAGE");

        for (String label: inputCommandMapToDescription.keySet()) {
            Printer.boldsSubtitle(label);
            Printer.subtitle(inputCommandMapToDescription.get(label));
        }
    }
}
