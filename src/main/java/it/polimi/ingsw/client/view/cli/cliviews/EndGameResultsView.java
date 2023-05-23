package it.polimi.ingsw.client.view.cli.cliviews;

import it.polimi.ingsw.client.controller.stateController.ClientState;
import it.polimi.ingsw.client.view.cli.Printer;

public class EndGameResultsView implements CliView {

    @Override
    public void render(ClientState state) {
        Printer.printWinner(state.getWinnerUserName());
    }
}
