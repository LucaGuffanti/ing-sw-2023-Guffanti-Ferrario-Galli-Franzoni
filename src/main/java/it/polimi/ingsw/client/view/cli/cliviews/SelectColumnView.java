package it.polimi.ingsw.client.view.cli.cliviews;

import it.polimi.ingsw.client.controller.stateController.ClientState;
import it.polimi.ingsw.client.view.cli.Printer;

public class SelectColumnView implements CliView {

    @Override
    public void render(ClientState state) {
        Printer.title("COLUMN SELECTION PHASE");
        Printer.subtitle("Select the column in which to put the cards you chose");
        Printer.printShelf(state.getShelves().get(state.getOrderedPlayersNames().indexOf(state.getActivePlayer())));
    }
}
