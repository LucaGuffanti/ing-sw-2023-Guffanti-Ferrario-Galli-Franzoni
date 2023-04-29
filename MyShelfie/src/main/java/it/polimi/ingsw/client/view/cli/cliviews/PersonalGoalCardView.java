package it.polimi.ingsw.client.view.cli.cliviews;

import it.polimi.ingsw.client.controller.stateController.ClientState;
import it.polimi.ingsw.client.view.cli.Printer;
import it.polimi.ingsw.server.model.cards.ObjectTypeEnum;
import it.polimi.ingsw.server.model.cards.goalCards.PersonalGoalCard;

public class PersonalGoalCardView implements CliView {
    @Override
    public void render(ClientState state) {
        Printer.title("YOUR PERSONAL GOAL CARD");
        Printer.printPersonalGoalCard(state.getPersonalGoalCardId());
    }
}
