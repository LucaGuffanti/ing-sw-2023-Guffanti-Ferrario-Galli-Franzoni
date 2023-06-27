package it.polimi.ingsw.client.view.cli.cliviews;

import it.polimi.ingsw.client.controller.stateController.ClientState;
import it.polimi.ingsw.client.view.cli.Printer;
import it.polimi.ingsw.server.model.cards.goalCards.SimplifiedCommonGoalCard;

/**
 * This view is used to show the points made by each player
 * @author Luca Guffanti
 */
public class PointsView implements CliView{
    /**
     * Displays the points achieved by every player
     * @param state the state of the client
     */
    @Override
    public void render(ClientState state) {
        Printer.title("POINTS");
        Printer.printShelfCompletionStatus(state);
        for (SimplifiedCommonGoalCard c : state.getCommonGoalCards()) {
            Printer.printCommonGoalCardStatus(c);
        }
    }
}
