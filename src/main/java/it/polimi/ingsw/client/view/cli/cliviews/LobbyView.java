package it.polimi.ingsw.client.view.cli.cliviews;

import it.polimi.ingsw.client.controller.stateController.ClientState;
import it.polimi.ingsw.client.view.cli.Printer;

/**
 * The lobby view is used to draw the game lobby before the start of the game
 * @author Luca Guffanti, Daniele Ferrario
 */
public class LobbyView implements CliView {

    @Override
    public void render(ClientState state) {
        Printer.title("GAME LOBBY");

        for (String username : state.getOrderedPlayersNames()) {
            Printer.printPlayerName(username);
        }
    }
}
