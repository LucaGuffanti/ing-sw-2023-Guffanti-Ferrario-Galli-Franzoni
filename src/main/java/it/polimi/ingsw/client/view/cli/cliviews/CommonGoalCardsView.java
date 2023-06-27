package it.polimi.ingsw.client.view.cli.cliviews;

import it.polimi.ingsw.client.controller.stateController.ClientState;
import it.polimi.ingsw.client.view.cli.Printer;
import it.polimi.ingsw.server.model.cards.goalCards.SimplifiedCommonGoalCard;


/**
 * The common goal card view displays all the common goal cards
 * with the relative points.
 * @author Luca Guffanti
 */
public class CommonGoalCardsView implements CliView {
    /**
     * Display the common goals
     * @param state the state of the client
     */
    @Override
    public void render(ClientState state) {
        Printer.title("COMMON GOAL CARDS");
        for(SimplifiedCommonGoalCard cg : state.getCommonGoalCards()) {
            Printer.printSimplifiedCommonGoal(cg);
        }
    }
}
