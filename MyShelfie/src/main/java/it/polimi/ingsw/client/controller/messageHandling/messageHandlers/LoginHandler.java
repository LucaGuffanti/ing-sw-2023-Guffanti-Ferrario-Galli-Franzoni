package it.polimi.ingsw.client.controller.messageHandling.messageHandlers;

import it.polimi.ingsw.client.controller.ClientPhasesEnum;
import it.polimi.ingsw.client.controller.messageHandling.Creator;
import it.polimi.ingsw.client.controller.messageHandling.Reducer;
import it.polimi.ingsw.client.controller.messageHandling.MessageHandlersUtils;
import it.polimi.ingsw.client.controller.stateController.ClientState;
import it.polimi.ingsw.network.messages.*;


/**
 * Handles the creation and the reception of the messages relative to the
 * login requests and responses.
 *
 * @see LoginRequestMessage // From client to server
 * @see LoginResponseMessage // From server to client
 * @author Daniele Ferrario
 */
public class LoginHandler extends Reducer implements Creator {

    public static LoginRequestMessage createMessage(String username){
        return new LoginRequestMessage(username);

    }

    @Override
    protected ClientState executeReduce(ClientState oldClientState, Message m){
        ClientState state;
        LoginResponseMessage loginMessage = (LoginResponseMessage) m;

        try {
            state = (ClientState) oldClientState.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }

        if(MessageHandlersUtils.isSuccessful(loginMessage)){
            state.setCurrentPhase(ClientPhasesEnum.LOBBY);
        }else{
            state.setServerErrorMessage(m.getDescription());
        }

        return state;
    }

}
