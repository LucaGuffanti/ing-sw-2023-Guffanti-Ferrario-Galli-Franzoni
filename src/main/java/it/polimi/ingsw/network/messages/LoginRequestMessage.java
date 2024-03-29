package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.client.controller.messageHandling.messageHandlers.LoginMessageHandler;
import it.polimi.ingsw.client.controller.messageHandling.messageHandlers.MessagesHandler;
import it.polimi.ingsw.network.messages.enums.MessageType;

/**
 * A login request is a message sent to the server by a client that wants to log in with a username.
 * Checks will be made so that there every username is unique. If the username is valid (so no previous player logged
 * in with the same username) a positive result is sent back to the client.
 * @author Luca Guffanti
 */
public class LoginRequestMessage extends Message{

    public LoginRequestMessage(String senderUsername) {
        super(MessageType.LOGIN_REQUEST, senderUsername);
    }
    public LoginRequestMessage(String senderUsername, String description) {
        super(MessageType.LOGIN_REQUEST, senderUsername, description);
    }

    @Override
    public MessagesHandler getHandlerForClient() {
        return new LoginMessageHandler();
    }
}
