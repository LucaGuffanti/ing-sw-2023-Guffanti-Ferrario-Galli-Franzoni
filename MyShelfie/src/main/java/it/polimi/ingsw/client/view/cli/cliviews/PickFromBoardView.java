package it.polimi.ingsw.client.view.cli.cliviews;

import it.polimi.ingsw.client.controller.stateController.ClientState;
import it.polimi.ingsw.client.view.cli.Printer;
import it.polimi.ingsw.server.model.cards.goalCards.SimplifiedCommonGoalCard;

public class PickFromBoardView implements CliView {

    @Override
    public void render(ClientState state) {
        Printer.title("NEW TURN: "+state.getActivePlayer());
        Printer.title("PICK FROM BOARD PHASE");
        Printer.subtitle("Game Board:");
        Printer.printBoard(state.getBoard());
        Printer.subtitle("Common Goals:");
        for (SimplifiedCommonGoalCard s : state.getCommonGoalCards()) {
            Printer.printCommonGoalCard(s.getId());
            Printer.printGoalCardPoints(s.getPointCards());
        }

    }
}
