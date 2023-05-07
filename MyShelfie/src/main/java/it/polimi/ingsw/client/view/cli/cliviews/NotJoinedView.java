package it.polimi.ingsw.client.view.cli.cliviews;

import it.polimi.ingsw.client.controller.stateController.ClientState;
import it.polimi.ingsw.client.view.cli.CLIMessages;
import it.polimi.ingsw.client.view.cli.Cli;
import it.polimi.ingsw.client.view.cli.Printer;

public class NotJoinedView implements CliView {
    @Override
    public void render(ClientState state) {
        Printer.title("ULTIMATING LOGIN...");
    }
}
