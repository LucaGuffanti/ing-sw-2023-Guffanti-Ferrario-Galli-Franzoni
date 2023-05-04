package it.polimi.ingsw.client.controller.messageHandling.messageHandlers;

import it.polimi.ingsw.client.controller.messageHandling.Reducer;
import it.polimi.ingsw.client.controller.stateController.ClientState;
import it.polimi.ingsw.client.view.cli.CLIMessages;
import it.polimi.ingsw.client.view.cli.Printer;
import it.polimi.ingsw.network.messages.EndOfTurnMessage;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.server.model.cards.ObjectTypeEnum;

import java.util.HashMap;

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

        state.setCommonGoalCards(
                endOfTurnMessage.getClientCommonGoalCards()
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

        Printer.title(CLIMessages.END_OF_TURN);
        state.setServerLastMessage(endOfTurnMessage.getDescription());


        return state;
    }
}
