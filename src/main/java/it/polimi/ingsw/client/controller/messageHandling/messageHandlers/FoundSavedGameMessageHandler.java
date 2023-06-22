package it.polimi.ingsw.client.controller.messageHandling.messageHandlers;

import it.polimi.ingsw.client.controller.ClientPhasesEnum;
import it.polimi.ingsw.client.controller.messageHandling.Reducer;
import it.polimi.ingsw.client.controller.stateController.ClientState;
import it.polimi.ingsw.network.messages.FoundSavedGameMessage;
import it.polimi.ingsw.network.messages.Message;

/**
 * This handler manages the reception of a {@link FoundSavedGameMessage}, so that the admin of the lobby can c
 * @author Luca Guffanti
 */
public class FoundSavedGameMessageHandler extends Reducer {
    @Override
    public ClientState reduce(ClientState oldState, Message m) {
        ClientState state = null;

        try {
            state = (ClientState) oldState.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }

        state.setCurrentPhase(ClientPhasesEnum.DECIDING_FOR_RELOAD);

        return state;
    }
}
