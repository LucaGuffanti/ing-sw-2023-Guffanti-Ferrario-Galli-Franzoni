package it.polimi.ingsw.client.controller.messageHandling.messageHandlers;

import it.polimi.ingsw.client.controller.messageHandling.Creator;
import it.polimi.ingsw.client.controller.messageHandling.Reducer;
import it.polimi.ingsw.client.controller.stateController.ClientState;
import it.polimi.ingsw.network.messages.LoginResponseMessage;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.NewPlayerMessage;

import java.util.List;

public class NewPlayerJoinedHandler extends Reducer implements Creator {

    /*
    public static BeginningOfTurnHandler createMessage(String username, String description){
        return new LoginRequestMessage(username, description);

    }*/

    @Override
    protected ClientState executeReduce(ClientState oldClientState, Message m){
        ClientState state = null;
        NewPlayerMessage newPlayerMessage = (NewPlayerMessage) m;

        try {
            state = (ClientState) oldClientState.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }

        List<String> playersList = state.getOrderedPlayersNames();
        playersList.add(newPlayerMessage.getJoinedPlayer());
        state.setOrderedPlayersNames(playersList);

        return state;
    }

}
