package it.polimi.ingsw.client.view.cli.cliviews;

import it.polimi.ingsw.client.controller.stateController.ClientState;
import it.polimi.ingsw.client.view.cli.Printer;
import it.polimi.ingsw.server.model.cards.ObjectTypeEnum;

/**
 * This view displays the shelf of the player.
 * @author Luca Guffanti, Daniele Ferrario, Davide Franzoni
 */
public class ShelfView implements CliView {
    /**
     * Shows the shelf of the player
     * @param state the state of the client
     */
    @Override
    public void render(ClientState state) {
        Printer.title("YOUR SHELF");
        Printer.printShelf(getOwnShelf(state));
    }

    /**
     *
     * @param state the state of the client
     * @return returns the own shelf
     */
    ObjectTypeEnum[][] getOwnShelf(ClientState state) {
        for (int i = 0; i < state.getOrderedPlayersNames().size(); i++) {
            if (state.getOrderedPlayersNames().get(i).equals(state.getUsername())) {
                return state.getShelves().get(i);
            }
        }
        return null;
    }
}
