package it.polimi.ingsw.client.controller.messageHandling.messageHandlers;

import it.polimi.ingsw.client.controller.ClientPhasesEnum;
import it.polimi.ingsw.client.controller.messageHandling.Reducer;
import it.polimi.ingsw.client.controller.stateController.ClientState;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.ConnectionEstablishedMessage;

/**
 * Handles the reception of the message representing
 * the success of the connection between the client and the server.
 *
 * @see ConnectionEstablishedMessage
 * @author Daniele Ferrario
 */
public class ConnectionHandler extends Reducer{

    @Override
    protected ClientState executeReduce(ClientState oldClientState, Message m){
        ClientState state = null;
        ConnectionEstablishedMessage connectionEstablishedMessage= (ConnectionEstablishedMessage) m;

        try {
            state = (ClientState) oldClientState.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }

        state.setCurrentPhase(ClientPhasesEnum.CONNECTED);

        return state;
    }

}
