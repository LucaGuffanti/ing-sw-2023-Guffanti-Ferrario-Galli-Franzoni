package it.polimi.ingsw.client.controller.messageHandling.messageHandlers;

import it.polimi.ingsw.client.controller.messageHandling.Creator;
import it.polimi.ingsw.client.controller.messageHandling.Reducer;
import it.polimi.ingsw.client.controller.stateController.ClientState;
import it.polimi.ingsw.network.messages.ChatMessage;
import it.polimi.ingsw.network.messages.Message;

import java.time.LocalDateTime;
import java.util.List;


/**
 * Handles the reception and the creation of chat messages.

 * @see it.polimi.ingsw.network.messages.ChatMessage
 * @author Daniele Ferrario
 */
public class ChatMessageHandler extends Reducer implements Creator {

    @Override
    public ClientState reduce(ClientState oldClientState, Message m){


        ClientState state = null;
        ChatMessage chatMessage = (ChatMessage) m;

        try {
            state = (ClientState) oldClientState.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        synchronized (state.getChatHistory()) {
            state.addToChatHistory(chatMessage);
        }
        state.setLastChatMessage(chatMessage);
        return state;
    }

    public static ChatMessage createMessage(String username, String value, List<String> recipients){
        return new ChatMessage(value, username, LocalDateTime.now(), recipients);
    }
}
