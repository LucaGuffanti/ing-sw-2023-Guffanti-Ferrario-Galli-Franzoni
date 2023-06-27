package it.polimi.ingsw.client.controller.messageHandling.messageHandlers;

import it.polimi.ingsw.client.controller.messageHandling.Reducer;
import it.polimi.ingsw.client.controller.stateController.ClientState;
import it.polimi.ingsw.network.messages.EndOfTurnMessage;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.server.model.cards.goalCards.SimplifiedCommonGoalCard;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles the reception of the message representing
 * the end of the turn by the active player
 *
 * @see EndOfTurnMessage
 * @author Daniele Ferrario
 */
public class EndOfTurnMessageHandler extends Reducer {
    /**
     * This method updates client side data with information about the newly ended turn
     * @param oldClientState The old state
     * @param m The received message
     * @return the new state of the client
     */
    @Override
    public ClientState reduce(ClientState oldClientState, Message m){
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
