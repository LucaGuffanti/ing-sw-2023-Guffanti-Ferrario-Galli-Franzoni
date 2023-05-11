package it.polimi.ingsw.client.view.cli.cliviews;

import it.polimi.ingsw.client.controller.stateController.ClientState;
import it.polimi.ingsw.client.view.cli.Printer;

public class WaitingForTurnView implements CliView{
    @Override
    public void render(ClientState state) {
        Printer.title("It's " + state.getActivePlayer() + "'s turn!");
        Printer.printBoard(state.getBoard());
    }
}
