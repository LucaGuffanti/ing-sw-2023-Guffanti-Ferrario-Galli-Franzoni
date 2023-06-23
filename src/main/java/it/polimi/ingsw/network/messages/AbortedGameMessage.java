package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.client.controller.messageHandling.messageHandlers.AbortedMessageHandler;
import it.polimi.ingsw.client.controller.messageHandling.messageHandlers.MessagesHandler;
import it.polimi.ingsw.network.messages.enums.MessageType;

/**
 * This message is sent from the server to connected clients when a connection error between a client and the server
 * occurs
 * @author Luca Guffanti
 */
public class AbortedGameMessage extends Message{
    public AbortedGameMessage(String senderUsername) {
        super(MessageType.ABORTED_GAME, senderUsername);
    }

    public AbortedGameMessage(String senderUsername, String description) {
        super(MessageType.ABORTED_GAME, senderUsername, description);
    }

    @Override
    public MessagesHandler getHandlerForClient() {
        return new AbortedMessageHandler();
    }
}
