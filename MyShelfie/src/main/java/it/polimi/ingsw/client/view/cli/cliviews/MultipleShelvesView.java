package it.polimi.ingsw.client.view.cli.cliviews;

import it.polimi.ingsw.client.controller.stateController.ClientState;
import it.polimi.ingsw.client.view.cli.Printer;

public class MultipleShelvesView implements CliView{
    @Override
    public void render(ClientState state) {
        Printer.title("ALL THE SHELVES");
        for (int i = 0; i < state.getShelfs().size(); i++) {
            Printer.subtitle(state.getOrderedPlayersNames().get(i)+"'s Shelf");
            Printer.printShelf(state.getShelfs().get(i));
        }
    }
}
