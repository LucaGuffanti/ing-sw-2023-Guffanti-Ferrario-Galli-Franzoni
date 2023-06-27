package it.polimi.ingsw.client.view.cli.cliviews;

import it.polimi.ingsw.client.controller.stateController.ClientState;
import it.polimi.ingsw.client.view.cli.Printer;

/**
 * This view is displayed when a player is not admitted to a game after waiting in queue
 * @author Luca Guffanti
 */
public class NotAdmittedView implements CliView{

    /**
     * Displays an error message because the client was not admitted to the game
     * @param state the state of the client
     */
    @Override
    public void render(ClientState state) {
        System.out.flush();
        Printer.error("YOU WERE NOT ADMITTED TO THE GAME");
        Printer.boldsSubtitle("because there were other waiting players... You may want to start your own server!");
        System.exit(0);
    }
}
