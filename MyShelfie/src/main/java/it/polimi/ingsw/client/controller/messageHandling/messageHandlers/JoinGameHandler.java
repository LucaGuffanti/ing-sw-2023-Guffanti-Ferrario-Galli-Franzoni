package it.polimi.ingsw.client.controller.messageHandling.messageHandlers;

import it.polimi.ingsw.client.controller.ClientPhasesEnum;
import it.polimi.ingsw.client.controller.messageHandling.Creator;
import it.polimi.ingsw.client.controller.messageHandling.Reducer;
import it.polimi.ingsw.client.controller.messageHandling.Utils;
import it.polimi.ingsw.client.controller.stateController.ClientState;
import it.polimi.ingsw.network.messages.*;

public class JoinGameHandler extends Reducer implements Creator {

    public static JoinGameMessage createMessage(String username){
        return new JoinGameMessage(username, username);

    }

    @Override
    protected ClientState executeReduce(ClientState oldClientState, Message m){
        ClientState state;
        AccessResultMessage accessResultMessage = (AccessResultMessage) m;

        try {
            state = (ClientState) oldClientState.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }


        if(Utils.isSuccess(accessResultMessage)){
            state.setOrderedPlayersNames(accessResultMessage.getPlayersUsernames());
        }

        return state;
    }

}
