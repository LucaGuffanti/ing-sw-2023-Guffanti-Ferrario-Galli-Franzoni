package it.polimi.ingsw.client.controller.messageHandling.messageHandlers;

import it.polimi.ingsw.client.controller.messageHandling.Creator;
import it.polimi.ingsw.client.controller.messageHandling.Reducer;
import it.polimi.ingsw.client.controller.stateController.ClientState;
import it.polimi.ingsw.network.messages.LoginResponseMessage;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.NewPlayerMessage;

import java.util.List;

/**
 * Handles the reception of the message relative to the joining
 * of a new player in the lobby.
 *
 * @see NewPlayerMessage
 * @author Daniele Ferrario
 */
public class NewPlayerJoinedHandler extends Reducer  {


    /**
     * Add the new joined user at the end of the ordered user lists.
     * @param oldClientState
     * @param m
     * @return
     */
    @Override
    protected ClientState executeReduce(ClientState oldClientState, Message m){
        ClientState state = null;
        NewPlayerMessage newPlayerMessage = (NewPlayerMessage) m;

        try {
            state = (ClientState) oldClientState.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }


        // Add the new player to the current ones
        state.addPlayerName(newPlayerMessage.getJoinedPlayer());


        return state;
    }

}
