package it.polimi.ingsw.client.controller.messageHandling.messageHandlers;

import it.polimi.ingsw.client.controller.ClientPhasesEnum;
import it.polimi.ingsw.client.controller.messageHandling.Reducer;
import it.polimi.ingsw.client.controller.stateController.ClientState;
import it.polimi.ingsw.network.messages.Message;

/**
 * This handler manages the receiving of a Wait for lobby message by setting the phase of the client to
 * WAITING FOR LOBBY
 * @author Luca Guffanti
 */
public class WaitForLobbyMessageHandler extends Reducer {

    /**
     * This method sets the client state to WAITING FOR LOBBY
     * @param oldState The old state
     * @param m The received message
     * @return the new state of the client
     */
    @Override
    public ClientState reduce(ClientState oldState, Message m) {
        ClientState state;

        try {
            state = (ClientState) oldState.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }

        state.setCurrentPhase(ClientPhasesEnum.WAITING_FOR_LOBBY);
        return state;
    }
}
