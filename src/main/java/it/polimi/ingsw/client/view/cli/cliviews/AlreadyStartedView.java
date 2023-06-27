package it.polimi.ingsw.client.view.cli.cliviews;

import it.polimi.ingsw.client.controller.stateController.ClientState;
import it.polimi.ingsw.client.view.cli.Printer;

/**
 * This view is displayed when a player tries to join a game which has already started
 * @author Luca Guffanti
 */
public class AlreadyStartedView implements CliView{
    /**
     * This view displays an error message
     * @param state the state of the client
     */
    @Override
    public void render(ClientState state) {
        Printer.error("The game you tried to join was already started!");
        Printer.subtitle("You may want to start your own server... try again!");
        System.exit(0);
    }
}
