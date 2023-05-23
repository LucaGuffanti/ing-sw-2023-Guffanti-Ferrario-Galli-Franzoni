package it.polimi.ingsw.client.controller.messageHandling.messageHandlers;

import it.polimi.ingsw.client.controller.messageHandling.Reducer;
import it.polimi.ingsw.client.controller.stateController.ClientState;
import it.polimi.ingsw.client.view.cli.CLIMessages;
import it.polimi.ingsw.client.view.cli.Printer;
import it.polimi.ingsw.network.messages.EndOfTurnMessage;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.server.model.cards.ObjectTypeEnum;
import it.polimi.ingsw.server.model.cards.goalCards.CommonGoalCard;
import it.polimi.ingsw.server.model.cards.goalCards.SimplifiedCommonGoalCard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Handles the reception of the message representing
 * the end of the turn by the active player
 *
 * @see EndOfTurnMessage
 * @author Daniele Ferrario
 */
public class EndOfTurnMessageHandler extends Reducer {

    @Override
    protected ClientState executeReduce(ClientState oldClientState, Message m){
        ClientState state = null;
        EndOfTurnMessage endOfTurnMessage = (EndOfTurnMessage) m;

        try {
            state = (ClientState) oldClientState.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }



        state.setBoard(
                endOfTurnMessage.getCurrentBoard()
        );

        state.setActivePlayerShelf(
                endOfTurnMessage.getActivePlayerShelf()
        );

        List<SimplifiedCommonGoalCard> cgNew = new ArrayList<>(endOfTurnMessage.getClientCommonGoalCards());

        state.setCommonGoalCards(
                cgNew
        );

        state.setHasCompletedFirstCommonGoal(
                endOfTurnMessage.isCompletedFirstCommonGoal()
        );

        state.setHasCompletedSecondCommonGoal(
                endOfTurnMessage.isCompletedSecondCommonGoal()
        );

        if (state.getFirstToCompleteShelf()==null && endOfTurnMessage.isCompletedShelf()) {
            state.setFirstToCompleteShelf(endOfTurnMessage.getActivePlayer());
        }

        state.setServerLastMessage(endOfTurnMessage.getDescription());


        return state;
    }
}
