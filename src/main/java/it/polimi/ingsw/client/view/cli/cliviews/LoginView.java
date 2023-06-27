package it.polimi.ingsw.client.view.cli.cliviews;

import it.polimi.ingsw.client.controller.stateController.ClientState;
import it.polimi.ingsw.client.view.cli.CLIMessages;
import it.polimi.ingsw.client.view.cli.Printer;

/**
 * The login view is used to prompt the user to log into the server with a username
 * @author Luca Guffanti, Daniele Ferrario
 */
public class LoginView implements CliView {
    /**
     * Displays the login view
     * @param state the state of the client
     */
    @Override
    public void render(ClientState state) {
        Printer.printName();
        Printer.printInfo(CLIMessages.GREETING);
    }
}
