package it.polimi.ingsw.client.view.cli.cliviews;

import it.polimi.ingsw.client.controller.stateController.ClientState;
import it.polimi.ingsw.client.view.cli.Printer;
import it.polimi.ingsw.server.model.cards.goalCards.SimplifiedCommonGoalCard;

public class PointsView implements CliView{
    @Override
    public void render(ClientState state) {
        Printer.title("POINTS");
        Printer.printShelfCompletionStatus(state);
        for (SimplifiedCommonGoalCard c : state.getCommonGoalCards()) {
            Printer.printCommonGoalCardStatus(c);
        }
    }
}
