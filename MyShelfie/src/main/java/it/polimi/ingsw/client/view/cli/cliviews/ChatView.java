package it.polimi.ingsw.client.view.cli.cliviews;

import it.polimi.ingsw.client.controller.stateController.ClientState;
import it.polimi.ingsw.client.view.cli.Printer;
import it.polimi.ingsw.server.model.Board;

public class ChatView implements CliView {
    @Override
    public void render(ClientState state) {
        Printer.log("-----\n\nCHAT VIEW PLACEHOLDER\n\n-----");
    }
}