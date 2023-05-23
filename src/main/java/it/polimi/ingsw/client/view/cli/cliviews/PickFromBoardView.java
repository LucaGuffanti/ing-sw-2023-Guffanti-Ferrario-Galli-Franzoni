package it.polimi.ingsw.client.view.cli.cliviews;

import it.polimi.ingsw.client.controller.stateController.ClientState;
import it.polimi.ingsw.client.view.cli.Printer;
import it.polimi.ingsw.server.model.cards.goalCards.SimplifiedCommonGoalCard;

public class PickFromBoardView implements CliView {

    @Override
    public void render(ClientState state) {
        Printer.printPlayerName("Your Turn, "+state.getActivePlayer());
        Printer.title("PICK FROM BOARD PHASE");
        Printer.subtitle("Pick the cards from the board by selecting the coordinates");
        Printer.printBoard(state.getBoard());

        int currentIndex = state.getOrderedPlayersNames().indexOf(state.getActivePlayer());
        Printer.subtitle(state.getActivePlayer()+"'s Shelf");
        Printer.printShelf(state.getShelves().get(currentIndex));
    }
}
