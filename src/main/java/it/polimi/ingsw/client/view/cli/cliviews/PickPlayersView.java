package it.polimi.ingsw.client.view.cli.cliviews;

import it.polimi.ingsw.client.controller.commandHandlers.PlayersNumberCommandHandler;
import it.polimi.ingsw.client.controller.stateController.ClientState;
import it.polimi.ingsw.client.view.cli.Printer;

public class PickPlayersView implements CliView{

    @Override
    public void render(ClientState state) {
        Printer.title("YOU'RE THE ADMIN OF THE LOBBY");
        Printer.boldsSubtitle("Please choose the number of players by using the command\n");
        Printer.boldsSubtitle("        "+ PlayersNumberCommandHandler.commandLabel+" number of players\n");
        Printer.boldsSubtitle("to select the number of players");
    }
}
