package it.polimi.ingsw.client.view.cli.cliviews;

import it.polimi.ingsw.client.controller.stateController.ClientState;

public class LobbyView implements CliView {

    @Override
    public void render(ClientState state) {
        System.out.println("------LOBBY VIEW PLACEHOLDER----");
        for (String username: state.getOrderedPlayersNames()
             ) {
            System.out.println(username);
        }
        System.out.println("--------------------------------");

    }
}
