package it.polimi.ingsw.view;

import it.polimi.ingsw.client.controller.ClientPhasesEnum;
import it.polimi.ingsw.client.controller.stateController.ClientState;
import it.polimi.ingsw.client.controller.stateController.StateContainer;
import it.polimi.ingsw.client.view.cli.Cli;
import it.polimi.ingsw.network.ClientNetworkHandler;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;


public class CliTests {
    InputStream stdin = System.in;

    @Test
    public void runCli(){

        ClientNetworkHandler networkHandler = null;

        ClientState firstState = new ClientState();
        firstState.setCurrentPhase(ClientPhasesEnum.PICK_FORM_BOARD);
        firstState.setUsername("Daniele");

        StateContainer stateContainer = new StateContainer(firstState);
        String data = "/exit\n";
        Cli cli = new Cli(new ByteArrayInputStream(data.getBytes()), stateContainer, networkHandler);
        cli.run();





    }


}
