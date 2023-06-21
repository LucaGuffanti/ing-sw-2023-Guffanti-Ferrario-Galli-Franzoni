package it.polimi.ingsw.client.view.cli.cliviews;

import it.polimi.ingsw.client.controller.stateController.ClientState;
import it.polimi.ingsw.client.view.cli.Printer;

/**
 * This view is shown to logged in player who haven't joined a lobby yet as it's being created.
 * @author Luca Guffanti
 */
public class WaitingForLobbyView implements CliView{

    @Override
    public void render(ClientState state) {
        Printer.title("WAITING FOR THE CREATION OF THE LOBBY");
        Printer.boldsSubtitle("A player is creating the lobby...");
        Printer.boldsSubtitle("Please wait: when the lobby is ready you'll be automatically added");
    }
}
