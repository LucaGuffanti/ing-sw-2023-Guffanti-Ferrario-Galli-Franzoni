package it.polimi.ingsw.client.controller.messageHandling.messageHandlers;

import it.polimi.ingsw.client.controller.messageHandling.Creator;
import it.polimi.ingsw.client.controller.messageHandling.Reducer;
import it.polimi.ingsw.client.controller.stateController.ClientState;
import it.polimi.ingsw.network.messages.*;

/**
 * Handles the creation and the reception of Ping messages sent between client and server.
 *
 * @see PingRequestMessage // Request from server to client
 * @see PingReplyMessage // Reply by client to server
 * @author Daniele Ferrario
 */
public class PingHandler extends Reducer implements Creator {


    public static PingReplyMessage createMessage(String username){
        return new PingReplyMessage(username);

    }

    @Override
    protected ClientState executeReduce(ClientState oldClientState, Message m){
        ClientState state = null;
        PingRequestMessage pingRequestMessage = (PingRequestMessage) m;

        try {
            state = (ClientState) oldClientState.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }

        // @TODO: Handle the ping request


        return state;
    }

}
