package it.polimi.ingsw.client.controller.messageHandling.messageHandlers;

import it.polimi.ingsw.client.controller.ClientPhasesEnum;
import it.polimi.ingsw.client.controller.messageHandling.Creator;
import it.polimi.ingsw.client.controller.messageHandling.Reducer;
import it.polimi.ingsw.client.controller.messageHandling.MessageHandlersUtils;
import it.polimi.ingsw.client.controller.stateController.ClientState;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.SelectColumnMessage;
import it.polimi.ingsw.network.messages.SelectColumnResultMessage;

/**
 * Handles the creation and the reception of messages used in the
 * selection of a column during the user's turn.
 *
 * @see SelectColumnMessage
 * @see SelectColumnResultMessage
 * @author Daniele Ferrario
 */
public class SelectColumnHandler extends Reducer implements Creator {


    public static SelectColumnMessage createMessage(String username, int columnIndex){
        return new SelectColumnMessage(username, columnIndex);

    }

    /**
     * Get if the column request has been validated by the server.
     * @param oldClientState
     * @param m
     * @return
     */
    @Override
    protected ClientState executeReduce(ClientState oldClientState, Message m){
        ClientState state = null;
        SelectColumnResultMessage selectColumnResultMessage = (SelectColumnResultMessage) m;

        try {
            state = (ClientState) oldClientState.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }

        if(MessageHandlersUtils.isSuccessful(selectColumnResultMessage)){
            state.setCurrentPhase(ClientPhasesEnum.WAITING_FOR_TURN);
        }else {
            state.setServerErrorMessage(selectColumnResultMessage.getDescription());
        }


        return state;
    }

}
