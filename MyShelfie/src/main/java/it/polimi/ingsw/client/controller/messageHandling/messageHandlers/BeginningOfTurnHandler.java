package it.polimi.ingsw.client.controller.messageHandling.messageHandlers;

import it.polimi.ingsw.client.controller.ClientPhasesEnum;
import it.polimi.ingsw.client.controller.messageHandling.Reducer;
import it.polimi.ingsw.client.controller.stateController.ClientState;
import it.polimi.ingsw.network.messages.BeginningOfTurnMessage;
import it.polimi.ingsw.network.messages.Message;


/**
 * Handles the reception of the message representing
 * the beginning of the turn of some user

 * @see BeginningOfTurnMessage
 * @author Daniele Ferrario
 */
public class BeginningOfTurnHandler extends Reducer {

    @Override
    protected ClientState executeReduce(ClientState oldClientState, Message m){

        // @TODO: Check if messages are sent in the right order?
        // Check if Game data has not been received yet
        if(oldClientState.getCurrentPhase() != ClientPhasesEnum.WAITING_FOR_TURN)
            return oldClientState;

        ClientState state = null;
        BeginningOfTurnMessage beginningOfTurnMessage = (BeginningOfTurnMessage) m;

        try {
            state = (ClientState) oldClientState.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }

        // Refresh the active player.
        state.setActivePlayer(beginningOfTurnMessage.getActiveUser());

        // Check if the active user is the client itself.
        if(state.getActivePlayer().equals(state.getUsername())){
                state.setCurrentPhase(ClientPhasesEnum.PICK_FORM_BOARD);
        }

        return state;
    }

}
