package it.polimi.ingsw.client.view.cli.cliviews;

import it.polimi.ingsw.client.controller.stateController.ClientState;
import it.polimi.ingsw.client.view.cli.Printer;

/**
 * This view displays the name of the winner of the game and the points made by each player
 * @author Daniele Ferrario, Davide Franzoni, Luca Guffanti
 */
public class EndGameResultsView implements CliView {

    /**
     * Displays the final result of the game
     * @param state the state of the client
     */
    @Override
    public void render(ClientState state) {
        Printer.printWinner(state.getWinnerUserName());
        Printer.printPlayerPoints(state.getNameToPointMap());
    }
}
