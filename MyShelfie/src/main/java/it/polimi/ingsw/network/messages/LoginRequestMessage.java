package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.network.messages.enums.MessageType;

/**
 * A login request is a message sent to the server by a client that wants to log in with a username.
 * Checks will be made so that there every username is unique. If the username is valid (so no previous player logged
 * in with the same username) a positive result is sent back to the client.
 * @author Luca Guffanti
 */
public class LoginRequestMessage extends Message{

    private final String requestedUsername;
    public LoginRequestMessage(String senderUsername, String requestedUsername) {
        super(MessageType.LOGIN_REQUEST, senderUsername);
        this.requestedUsername = requestedUsername;
    }
    public LoginRequestMessage(String senderUsername, String description, String requestedUsername) {
        super(MessageType.LOGIN_REQUEST, senderUsername, description);
        this.requestedUsername = requestedUsername;
    }
    public String getRequestedUsername() {
        return requestedUsername;
    }
}
