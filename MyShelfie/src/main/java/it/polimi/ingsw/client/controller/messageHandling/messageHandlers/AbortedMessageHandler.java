package it.polimi.ingsw.client.controller.messageHandling.messageHandlers;

import it.polimi.ingsw.client.controller.ClientPhasesEnum;
import it.polimi.ingsw.client.controller.messageHandling.Reducer;
import it.polimi.ingsw.client.controller.stateController.ClientState;
import it.polimi.ingsw.network.messages.AbortedGameMessage;
import it.polimi.ingsw.network.messages.ChatMessage;
import it.polimi.ingsw.network.messages.Message;

/**
 * This message handler manages the reception of an Aborted Game message, sent when,
 * due to connectivity issues, a client disconnects.
 * @author Luca Guffanti
 */
public class AbortedMessageHandler extends Reducer {
    @Override
    protected ClientState executeReduce(ClientState oldState, Message m) {
        ClientState state = null;
        AbortedGameMessage abortedGameMessage = (AbortedGameMessage) m;

        state = new ClientState();
        state.setCurrentPhase(ClientPhasesEnum.ABORTED_GAME);
        return state;
    }
}
