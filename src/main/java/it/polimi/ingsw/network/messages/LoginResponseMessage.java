package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.client.controller.messageHandling.messageHandlers.LoginMessageHandler;
import it.polimi.ingsw.client.controller.messageHandling.messageHandlers.MessagesHandler;
import it.polimi.ingsw.network.messages.enums.MessageType;
import it.polimi.ingsw.network.messages.enums.ResponseResultType;

/**
 * A login response is sent after a login request is reached, and indicates that the login failed or happened with
 * success.
 * @author Luca Guffanti
 */
public class LoginResponseMessage extends Message implements MessageWithResult{
    private final ResponseResultType resultType;
    private final String recipient;

    public LoginResponseMessage(String senderUsername, ResponseResultType resultType, String recipient) {
        super(MessageType.LOGIN_RESPONSE, senderUsername);
        this.resultType = resultType;
        this.recipient = recipient;
    }

    public LoginResponseMessage(String senderUsername, String description, ResponseResultType resultType, String recipient) {
        super(MessageType.LOGIN_RESPONSE, senderUsername, description);
        this.resultType = resultType;
        this.recipient = recipient;
    }

    public ResponseResultType getResultType() {
        return resultType;
    }

    @Override
    public MessagesHandler getHandlerForClient() {
        return new LoginMessageHandler();
    }

    public String getRecipient() {
        return recipient;
    }
}
