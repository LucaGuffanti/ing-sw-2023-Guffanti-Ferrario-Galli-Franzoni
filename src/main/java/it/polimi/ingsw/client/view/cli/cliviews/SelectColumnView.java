package it.polimi.ingsw.client.view.cli.cliviews;

import it.polimi.ingsw.client.controller.stateController.ClientState;
import it.polimi.ingsw.client.view.cli.Printer;

/**
 * This view shows the player its own shelf and displays a message requesting the insertion of the column in which
 * to put the picked cards
 * @author Luca Guffanti, Daniele Ferrario
 */
public class SelectColumnView implements CliView {

    /**
     * Prompts the client to enter a column in which to insert the list picked tiles
     * @param state the state of the client
     */
    @Override
    public void render(ClientState state) {
        Printer.title("COLUMN SELECTION PHASE");
        Printer.subtitle("Select the column in which to put the cards you chose");
        Printer.printShelf(state.getShelves().get(state.getOrderedPlayersNames().indexOf(state.getActivePlayer())));
    }
}
