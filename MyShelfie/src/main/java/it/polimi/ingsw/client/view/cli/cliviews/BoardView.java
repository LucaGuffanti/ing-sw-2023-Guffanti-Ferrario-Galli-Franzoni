package it.polimi.ingsw.client.view.cli.cliviews;

import it.polimi.ingsw.client.controller.stateController.ClientState;
import it.polimi.ingsw.client.view.cli.Printer;
import it.polimi.ingsw.server.model.Board;

/**
 * The BoardView manages the printing of the board.
 * @author Daniele Ferrario, Davide Franzoni, Luca Guffanti
 */
public class BoardView implements CliView {

    @Override
    public void render(ClientState state) {
        Printer.title("THE BOARD");
        Printer.printBoard(state.getBoard());
    }
}
