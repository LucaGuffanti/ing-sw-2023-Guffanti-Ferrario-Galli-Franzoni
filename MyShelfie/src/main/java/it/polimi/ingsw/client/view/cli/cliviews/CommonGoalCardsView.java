package it.polimi.ingsw.client.view.cli.cliviews;

import it.polimi.ingsw.client.controller.stateController.ClientState;
import it.polimi.ingsw.client.view.cli.Printer;
import it.polimi.ingsw.server.model.cards.goalCards.CommonGoalCard;
import it.polimi.ingsw.server.model.cards.goalCards.SimplifiedCommonGoalCard;

import java.util.List;

public class CommonGoalCardsView implements CliView {
    @Override
    public void render(ClientState state) {
        Printer.title("COMMON GOAL CARDS");
        for(SimplifiedCommonGoalCard cg : state.getCommonGoalCards()) {
            Printer.printSimplifiedCommonGoal(cg);
        }
    }
}
