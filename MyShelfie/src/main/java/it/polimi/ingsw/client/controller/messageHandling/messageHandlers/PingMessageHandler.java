package it.polimi.ingsw.client.controller.messageHandling.messageHandlers;

import it.polimi.ingsw.client.controller.messageHandling.Creator;
import it.polimi.ingsw.client.controller.messageHandling.Reducer;
import it.polimi.ingsw.client.controller.stateController.ClientState;
import it.polimi.ingsw.network.ServerNetworkHandler;
import it.polimi.ingsw.network.messages.*;

/**
 * Handles the creation and the reception of Ping messages sent between client and server.
 *
 * @see PingRequestMessage // Request from server to client
 * @author Daniele Ferrario
 */
public class PingMessageHandler extends Reducer implements Creator {


    public static PingRequestMessage createMessage(String username){
        return new PingRequestMessage(username, ServerNetworkHandler.HOSTNAME);
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
