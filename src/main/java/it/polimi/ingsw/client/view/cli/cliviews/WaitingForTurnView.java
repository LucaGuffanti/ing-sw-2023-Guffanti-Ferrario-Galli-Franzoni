package it.polimi.ingsw.client.view.cli.cliviews;

import it.polimi.ingsw.client.controller.stateController.ClientState;
import it.polimi.ingsw.client.view.cli.Printer;

/**
 * This view is shown to each player when it's not their turn to play. Although there isn't much printed to
 * the screen, each player is able to use the different provided commands.
 * @author Luca Guffanti
 */
public class WaitingForTurnView implements CliView{
    /**
     * Tells the player to wait and try commands while another client is trying the game
     * @param state the state of the client
     */
    @Override
    public void render(ClientState state) {
        Printer.title("It's " + state.getActivePlayer() + "'s turn! - Write /help for a list of all the commands");
    }
}
