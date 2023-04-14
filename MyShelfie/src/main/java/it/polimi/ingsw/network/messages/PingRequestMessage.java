package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.network.messages.enums.MessageType;

/**
 * This message is sent to each client by the server. It's used to ping the client and check for it being connected.
 * @author Luca Guffanti
 */
public class PingRequestMessage extends Message{
    public PingRequestMessage(MessageType type, String senderUsername) {
        super(type, senderUsername);
    }

    public PingRequestMessage(MessageType type, String senderUsername, String description) {
        super(type, senderUsername, description);
    }
}
