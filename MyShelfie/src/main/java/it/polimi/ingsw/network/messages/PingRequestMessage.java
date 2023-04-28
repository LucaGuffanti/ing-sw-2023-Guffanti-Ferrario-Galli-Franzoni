package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.client.controller.messageHandling.messageHandlers.MessagesHandler;
import it.polimi.ingsw.client.controller.messageHandling.messageHandlers.PingHandler;
import it.polimi.ingsw.network.messages.enums.MessageType;

/**
 * This message is sent to each client by the server. It's used to ping the client and check for it being connected.
 * @author Luca Guffanti
 */
public class PingRequestMessage extends Message{
    private final String recipient;
    public PingRequestMessage(String senderUsername, String recipient) {
        super(MessageType.PING_REQUEST, senderUsername);
        this.recipient = recipient;
    }

    public PingRequestMessage(String senderUsername, String description, String recipient) {
        super(MessageType.PING_REQUEST, senderUsername, description);
        this.recipient = recipient;
    }

    @Override
    public MessagesHandler getHandlerForClient() {
        return new PingHandler();
    }
}
