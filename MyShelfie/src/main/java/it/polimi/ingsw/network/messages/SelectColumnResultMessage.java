package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.client.controller.messageHandling.messageHandlers.MessagesHandler;
import it.polimi.ingsw.client.controller.messageHandling.messageHandlers.SelectColumnHandler;
import it.polimi.ingsw.network.messages.enums.MessageType;
import it.polimi.ingsw.network.messages.enums.ResponseResultType;

/**
 * This message contains the result of the choice of the shelf column coming from the user.
 * @author Luca Guffanti
 */
public class SelectColumnResultMessage extends Message implements MessageWithResult{

    private final ResponseResultType resultType;
    private final String recipient;
    public SelectColumnResultMessage(String senderUsername, ResponseResultType resultType, String recipient) {
        super(MessageType.SELECT_COLUMN_RESULT, senderUsername);
        this.resultType = resultType;
        this.recipient = recipient;
    }

    public SelectColumnResultMessage(String senderUsername, String description, ResponseResultType resultType, String recipient) {
        super(MessageType.SELECT_COLUMN_RESULT, senderUsername, description);
        this.resultType = resultType;
        this.recipient = recipient;
    }

    public ResponseResultType getResultType() {
        return resultType;
    }

    public String getRecipient() {
        return recipient;
    }

    @Override
    public MessagesHandler getHandlerForClient() {
        return new SelectColumnHandler();
    }
}
