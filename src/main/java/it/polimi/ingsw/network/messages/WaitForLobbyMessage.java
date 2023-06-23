package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.client.controller.messageHandling.messageHandlers.MessagesHandler;
import it.polimi.ingsw.client.controller.messageHandling.messageHandlers.WaitForLobbyMessageHandler;
import it.polimi.ingsw.network.messages.enums.MessageType;

/**
 * This message is sent to every client who logged while a player is deciding the number of players that should
 * participate in the game
 * @author Luca Guffanti
 */
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
