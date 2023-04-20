package it.polimi.ingsw.client.controller.messageHandling.messageHandlers;

import it.polimi.ingsw.client.controller.messageHandling.Creator;
import it.polimi.ingsw.client.controller.messageHandling.Reducer;
import it.polimi.ingsw.client.controller.messageHandling.MessageHandlersUtils;
import it.polimi.ingsw.client.controller.stateController.ClientState;
import it.polimi.ingsw.network.messages.*;

/**
 * Handles the creation and the reception of the messages relative to the
 * game lobby joining.
 *
 * @see JoinGameMessage // From client to server
 * @see AccessResultMessage // From server to client
 * @author Daniele Ferrario
 */
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


        if(MessageHandlersUtils.isSuccessful(accessResultMessage)){
            state.setOrderedPlayersNames(accessResultMessage.getPlayersUsernames());
        }else{
            state.setServerErrorMessage(accessResultMessage.getDescription());
        }

        return state;
    }

}
