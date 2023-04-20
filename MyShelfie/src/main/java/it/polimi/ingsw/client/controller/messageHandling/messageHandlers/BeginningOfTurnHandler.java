package it.polimi.ingsw.client.controller.messageHandling.messageHandlers;

import it.polimi.ingsw.client.controller.ClientPhasesEnum;
import it.polimi.ingsw.client.controller.messageHandling.Creator;
import it.polimi.ingsw.client.controller.messageHandling.Reducer;
import it.polimi.ingsw.client.controller.messageHandling.Utils;
import it.polimi.ingsw.client.controller.stateController.ClientState;
import it.polimi.ingsw.network.messages.BeginningOfTurnMessage;
import it.polimi.ingsw.network.messages.LoginResponseMessage;
import it.polimi.ingsw.network.messages.Message;

public class BeginningOfTurnHandler extends Reducer implements Creator {

    /*
    public static BeginningOfTurnHandler createMessage(String username, String description){
        return new LoginRequestMessage(username, description);

    }*/

    @Override
    protected ClientState executeReduce(ClientState oldClientState, Message m){
        ClientState state = null;
        BeginningOfTurnMessage beginningOfTurnMessage = (BeginningOfTurnMessage) m;

        try {
            state = (ClientState) oldClientState.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }


        state.setActivePlayer(beginningOfTurnMessage.getActiveUser());

        // It's user turn
        if(state.getActivePlayer().equals(state.getUsername())){
                state.setCurrentPhase(ClientPhasesEnum.PICK_FORM_BOARD);
        }

        return state;
    }

}
