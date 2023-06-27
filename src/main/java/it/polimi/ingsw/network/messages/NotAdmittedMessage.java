package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.client.controller.messageHandling.messageHandlers.MessagesHandler;
import it.polimi.ingsw.client.controller.messageHandling.messageHandlers.NotAdmittedMessageHandler;
import it.polimi.ingsw.network.messages.enums.MessageType;

/**
 * When the queue of players waiting to join a lobby is too big, say of size N, and
 * there are only M available places, this message is sent to the last N-M players who
 * are not admitted to the game
 * @author Luca Guffanti
 */
public class NotAdmittedMessage extends Message {
    public NotAdmittedMessage(String senderUsername) {
        super(MessageType.NOT_ADMITTED, senderUsername);
    }

    public NotAdmittedMessage(String senderUsername, String description) {
        super(MessageType.NOT_ADMITTED, senderUsername, description);
    }

    @Override
    public MessagesHandler getHandlerForClient() {
        return new NotAdmittedMessageHandler();
    }
}
