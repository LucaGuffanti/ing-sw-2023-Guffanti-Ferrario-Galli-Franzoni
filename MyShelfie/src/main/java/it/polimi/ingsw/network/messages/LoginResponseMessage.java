package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.network.messages.enums.MessageType;
import it.polimi.ingsw.network.messages.enums.ResponseResultType;

/**
 * A login response is sent after a login request is reached, and indicates that the login failed or happened with
 * success.
 * @author Luca Guffanti
 */
public class LoginResponseMessage extends Message{
    ResponseResultType resultType;

    public LoginResponseMessage(String senderUsername, ResponseResultType resultType) {
        super(MessageType.LOGIN_RESPONSE, senderUsername);
        this.resultType = resultType;
    }

    public LoginResponseMessage(String senderUsername, String description, ResponseResultType resultType) {
        super(MessageType.LOGIN_RESPONSE, senderUsername, description);
        this.resultType = resultType;
    }
}
