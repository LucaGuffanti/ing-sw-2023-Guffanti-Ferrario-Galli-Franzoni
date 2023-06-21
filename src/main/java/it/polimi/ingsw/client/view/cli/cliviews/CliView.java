package it.polimi.ingsw.client.view.cli.cliviews;

import it.polimi.ingsw.client.controller.stateController.ClientState;

/**
 * Interface describing a generic view of the text user interface
 * @author Daniele Ferrario
 */
public interface CliView {
    /**
     * Based on the state of the client a specific view is drawn to screen.
     * @param state the state of the client
     */
    public void render(ClientState state);
}
