package it.polimi.ingsw.client.controller.messageHandling.messageHandlers;

import it.polimi.ingsw.client.controller.ClientPhasesEnum;
import it.polimi.ingsw.client.controller.messageHandling.Reducer;
import it.polimi.ingsw.client.controller.stateController.ClientState;
import it.polimi.ingsw.network.messages.AccessResultMessage;
import it.polimi.ingsw.network.messages.Message;

public class WaitForLobbyMessageHandler extends Reducer {

    @Override
    protected ClientState executeReduce(ClientState oldState, Message m) {
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
