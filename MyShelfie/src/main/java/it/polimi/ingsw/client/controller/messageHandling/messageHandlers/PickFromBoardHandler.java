package it.polimi.ingsw.client.controller.messageHandling.messageHandlers;

import it.polimi.ingsw.client.controller.ClientPhasesEnum;
import it.polimi.ingsw.client.controller.messageHandling.Creator;
import it.polimi.ingsw.client.controller.messageHandling.Reducer;
import it.polimi.ingsw.client.controller.messageHandling.Utils;
import it.polimi.ingsw.client.controller.stateController.ClientState;
import it.polimi.ingsw.network.messages.LoginResponseMessage;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.PickFromBoardMessage;
import it.polimi.ingsw.network.messages.PickFromBoardResultMessage;

public class PickFromBoardHandler extends Reducer implements Creator {

    /*
    public static BeginningOfTurnHandler createMessage(String username, String description){
        return new LoginRequestMessage(username, description);

    }*/

    @Override
    protected ClientState executeReduce(ClientState oldClientState, Message m){
        ClientState state = null;
        PickFromBoardResultMessage resultMessage = (PickFromBoardResultMessage) m;

        try {
            state = (ClientState) oldClientState.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }

        if(Utils.isSuccess(resultMessage)){
            state.setCurrentPhase(ClientPhasesEnum.COLUMN_CHOICE);
        }else {
            // Se the error message
            state.setServerErrorMessage(resultMessage.getDescription());
        }
        return state;
    }

}
