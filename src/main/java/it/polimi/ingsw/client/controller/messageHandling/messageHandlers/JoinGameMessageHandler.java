package it.polimi.ingsw.client.controller.messageHandling.messageHandlers;

import it.polimi.ingsw.client.controller.ClientPhasesEnum;
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
public class JoinGameMessageHandler extends Reducer implements Creator {

    public static JoinGameMessage createMessage(String username){
        return new JoinGameMessage(username, username);

    }

    /**
     * This method handles the result of a join operation, based on whether the access
     * was permitted or not
     * @param oldClientState The old state
     * @param m The received message
     * @return the new state of the client
     */
    @Override
    public ClientState reduce(ClientState oldClientState, Message m){
        ClientState state;
        AccessResultMessage accessResultMessage = (AccessResultMessage) m;

        try {
            state = (ClientState) oldClientState.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }

        // Join the lobby
        if(MessageHandlersUtils.isSuccessful(accessResultMessage)){
            state.setCurrentPhase(ClientPhasesEnum.LOBBY);
            state.setOrderedPlayersNames(accessResultMessage.getPlayersUsernames());
        }else{
            state.setCurrentPhase(ClientPhasesEnum.ALREADY_STARTED);
        }
        return state;
    }

}
