package it.polimi.ingsw.client.view.cli.cliviews;

import it.polimi.ingsw.client.controller.stateController.ClientState;
import it.polimi.ingsw.client.view.cli.CLIMessages;
import it.polimi.ingsw.client.view.cli.Cli;
import it.polimi.ingsw.client.view.cli.Printer;

/**
 * This view is showed for a brief period of time, after the user has logged in but isn't in a lobby yet.
 * @author Luca Guffanti
 */
public class NotJoinedView implements CliView {
    @Override
    public void render(ClientState state) {
        Printer.title("ULTIMATING LOGIN...");
    }
}
