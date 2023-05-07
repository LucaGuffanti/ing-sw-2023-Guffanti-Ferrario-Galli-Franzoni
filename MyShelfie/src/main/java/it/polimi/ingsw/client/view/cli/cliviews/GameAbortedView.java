package it.polimi.ingsw.client.view.cli.cliviews;

import it.polimi.ingsw.client.controller.stateController.ClientState;
import it.polimi.ingsw.client.view.cli.Printer;

/**
 * The aborted game view is rendered when the game is aborted (because a client has lost connection or because
 * the server is no longer online)
 * @author Luca Guffanti
 */
public class GameAbortedView implements CliView{

    @Override
    public void render(ClientState state) {
        Printer.title("THE GAME IS ABORTED");
        Printer.title("Because the connection was lost!");
        Printer.boldsSubtitle("If you want to keep on playing the game, don't worry!\n" +
                "Simply restart the program and log back into the server with the same name.\n" +
                "Please note that if nobody has made a move yet the game will not be saved\n");
        Printer.boldsSubtitle("If you are the admin of the game, and you want to reload the game, please\n" +
                "recreate a game with the same number of players, then respond positively to the prompt when the lobby" +
                "if filled.");
        System.exit(1);
    }
}
