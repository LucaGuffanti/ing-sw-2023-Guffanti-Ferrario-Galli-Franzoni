package it.polimi.ingsw.client.view.cli.cliviews;

import it.polimi.ingsw.client.controller.stateController.ClientState;
import it.polimi.ingsw.client.view.cli.Printer;

public class WaitingForLobbyView implements CliView{

    @Override
    public void render(ClientState state) {
        Printer.title("WAITING FOR THE CREATION OF THE LOBBY");
        Printer.boldsSubtitle("A player is creating the lobby...");
        Printer.boldsSubtitle("Please wait: when the lobby is ready you'll be automatically added");
    }
}
