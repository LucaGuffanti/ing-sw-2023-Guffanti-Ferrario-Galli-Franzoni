package it.polimi.ingsw.client.view.cli.cliviews;

import it.polimi.ingsw.client.controller.commandHandlers.PlayersNumberCommandHandler;
import it.polimi.ingsw.client.controller.stateController.ClientState;
import it.polimi.ingsw.client.view.cli.Printer;

/**
 * This view is used to prompt the player who's creating the lobby to insert the wanted number of players.
 * @author Luca Guffanti
 */
public class PickPlayersView implements CliView{

    @Override
    public void render(ClientState state) {
        Printer.title("YOU'RE THE ADMIN OF THE LOBBY");
        Printer.boldsSubtitle("Please choose the number of players by using the command\n");
        Printer.boldsSubtitle("        "+ PlayersNumberCommandHandler.commandLabel+" number of players\n");
        Printer.boldsSubtitle("to select the number of players");
    }
}
