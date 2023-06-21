package it.polimi.ingsw.client.view.cli.cliviews;

import it.polimi.ingsw.client.controller.stateController.ClientState;
import it.polimi.ingsw.client.view.cli.CLIMessages;
import it.polimi.ingsw.client.view.cli.Printer;

/**
 * This view is used to ask the admin of the lobby to decide to reload a game or not when a saved game is found
 * and the users match.
 * @author Luca Guffanti
 */
public class ReloadDecisionView implements CliView{
    @Override
    public void render(ClientState state) {
        Printer.title("FOUND A COMPATIBLE SAVED GAME");
        Printer.boldsSubtitle("Do you want to reload it?");
        Printer.printInfo(CLIMessages.RELOAD_DECISION);
    }
}
