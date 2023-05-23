package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.client.controller.messageHandling.messageHandlers.MessagesHandler;
import it.polimi.ingsw.client.controller.messageHandling.messageHandlers.WaitForLobbyMessageHandler;
import it.polimi.ingsw.network.messages.enums.MessageType;

public class WaitForLobbyMessage extends Message{
    public WaitForLobbyMessage(String senderUsername) {
        super(MessageType.WAIT_FOR_LOBBY, senderUsername);
    }

    public WaitForLobbyMessage(String senderUsername, String description) {
        super(MessageType.WAIT_FOR_LOBBY, senderUsername, description);
    }

    @Override
    public MessagesHandler getHandlerForClient() {
        return new WaitForLobbyMessageHandler();
    }
}
