package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.network.messages.enums.MessageType;

/**
 * This message is sent by each client when pinged as a confirmation of them being connected.
 * @author Luca Guffanti
 */
public class PingReplyMessage extends Message{
    public PingReplyMessage(String senderUsername) {
        super(MessageType.PING_REPLY, senderUsername);
    }

    public PingReplyMessage(String senderUsername, String description) {
        super(MessageType.PING_REPLY, senderUsername, description);
    }
}
