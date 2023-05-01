package it.polimi.ingsw.client.view.cli.cliviews;

import it.polimi.ingsw.client.controller.stateController.ClientState;
import it.polimi.ingsw.client.view.cli.Printer;

public class LobbyView implements CliView {

    @Override
    public void render(ClientState state) {
        Printer.title("GAME LOBBY");

        for (String username : state.getOrderedPlayersNames()) {
            Printer.printPlayerName(username);
        }
    }
}
