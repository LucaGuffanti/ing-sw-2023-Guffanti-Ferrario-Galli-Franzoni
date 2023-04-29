package it.polimi.ingsw.client.view.cli.cliviews;

import it.polimi.ingsw.client.controller.stateController.ClientState;
import it.polimi.ingsw.client.view.cli.Printer;
import it.polimi.ingsw.server.model.cards.ObjectTypeEnum;
import it.polimi.ingsw.server.model.player.Player;

import java.util.List;

public class ShelfView implements CliView {
    @Override
    public void render(ClientState state) {
        Printer.title("YOUR SHELF");
        Printer.printShelf(getOwnShelf(state));
    }

    ObjectTypeEnum[][] getOwnShelf(ClientState state) {
        for (int i = 0; i < state.getOrderedPlayersNames().size(); i++) {
            if (state.getOrderedPlayersNames().get(i).equals(state.getUsername())) {
                return state.getShelfs().get(i);
            }
        }
        return null;
    }
}
