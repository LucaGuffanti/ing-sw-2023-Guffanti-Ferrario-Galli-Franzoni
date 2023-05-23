package it.polimi.ingsw.client.view.cli.cliviews;

import it.polimi.ingsw.client.controller.stateController.ClientState;

public interface CliView {
    public void render(ClientState state);
}
