package it.polimi.ingsw.client.controller.messageHandling.messageHandlers;

import it.polimi.ingsw.client.controller.ClientPhasesEnum;
import it.polimi.ingsw.client.controller.messageHandling.Reducer;
import it.polimi.ingsw.client.controller.stateController.ClientState;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.NotAdmittedMessage;

/**
 * This object handles the receiving of a {@link NotAdmittedMessage}
 */
public class NotAdmittedMessageHandler extends Reducer {

    /**
     * This method sets the phase to NOT_ADMITTED
     * @param oldState The old state
     * @param m The received message
     * @return the new state of the client
     */
    @Override
    public ClientState reduce(ClientState oldState, Message m) {
        ClientState state = null;

        try {
            state = (ClientState) oldState.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }

        state.setCurrentPhase(ClientPhasesEnum.NOT_ADMITTED);
        return state;
    }
}
