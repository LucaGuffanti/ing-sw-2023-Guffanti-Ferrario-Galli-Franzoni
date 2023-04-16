package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.network.messages.enums.MessageType;

/**
 * This message is sent to each client by the server. It's used to ping the client and check for it being connected.
 * @author Luca Guffanti
 */
public class PingRequestMessage extends Message{
    private final String recipient;
    public PingRequestMessage(MessageType type, String senderUsername, String recipient) {
        super(type, senderUsername);
        this.recipient = recipient;
    }

    public PingRequestMessage(MessageType type, String senderUsername, String description, String recipient) {
        super(type, senderUsername, description);
        this.recipient = recipient;
    }
}
