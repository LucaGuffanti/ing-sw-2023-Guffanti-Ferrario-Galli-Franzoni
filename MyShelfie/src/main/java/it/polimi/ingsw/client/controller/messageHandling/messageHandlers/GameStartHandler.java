package it.polimi.ingsw.client.controller.messageHandling.messageHandlers;

import it.polimi.ingsw.client.controller.messageHandling.Creator;
import it.polimi.ingsw.client.controller.messageHandling.Reducer;
import it.polimi.ingsw.client.controller.stateController.ClientState;
import it.polimi.ingsw.network.messages.BeginningOfTurnMessage;
import it.polimi.ingsw.network.messages.GameStartMessage;
import it.polimi.ingsw.network.messages.Message;

public class GameStartHandler extends Reducer implements Creator {

    /*
    public static BeginningOfTurnHandler createMessage(String username, String description){
        return new LoginRequestMessage(username, description);

    }*/

    @Override
    protected ClientState executeReduce(ClientState oldClientState, Message m){
        ClientState state = null;
        GameStartMessage gameStartMessage = (GameStartMessage) m;

        try {
            state = (ClientState) oldClientState.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }



        return state;
    }


}
