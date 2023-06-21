package it.polimi.ingsw.client.view.cli.cliviews;

import it.polimi.ingsw.client.controller.stateController.ClientState;
import it.polimi.ingsw.client.view.cli.CLIMessages;
import it.polimi.ingsw.client.view.cli.Printer;
import it.polimi.ingsw.server.model.cards.goalCards.PersonalGoalCard;

/**
 * The login view is used to prompt the user to log into the server with a username
 * @author Luca Guffanti, Daniele Ferrario
 */
public class LoginView implements CliView {
    @Override
    public void render(ClientState state) {
        Printer.printName();
        Printer.printInfo(CLIMessages.GREETING);
    }
}
