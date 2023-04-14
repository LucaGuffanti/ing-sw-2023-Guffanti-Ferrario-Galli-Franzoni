package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.network.messages.enums.MessageType;

/**
 * A ConnectionEstablished message is sent to a client when a connection between the
 * client and the server is established and the client can proceed with the login operations.
 * @author Luca Guffanti
 */
public class ConnectionEstablishedMessage extends Message{
    public ConnectionEstablishedMessage(String senderUsername) {
        super(MessageType.CONNECTION_ESTABLISHED, senderUsername);
    }

    public ConnectionEstablishedMessage(String senderUsername, String description) {
        super(MessageType.CONNECTION_ESTABLISHED, senderUsername, description);
    }
}
