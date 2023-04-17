package it.polimi.ingsw.client.controller.messagesHandlers;

import it.polimi.ingsw.client.controller.ClientPhasesEnum;
import it.polimi.ingsw.client.controller.stateController.ClientState;
import it.polimi.ingsw.network.messages.LoginRequestMessage;
import it.polimi.ingsw.network.messages.LoginResponseMessage;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.enums.ResponseResultType;

public class LoginHandler extends Reducer implements Creator{

    public static LoginRequestMessage createMessage(String username, String description){
        return new LoginRequestMessage(username, description);

    }

    @Override
    protected ClientState executeReduce(ClientState oldClientState, Message m){
        ClientState state;
        LoginResponseMessage message = (LoginResponseMessage) m;

        try {
            state = (ClientState) oldClientState.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }


        if(m.equals(ResponseResultType.SUCCESS)){
            state.setCurrentPhase(ClientPhasesEnum.LOBBY);
        }

        return state;
    }





}
