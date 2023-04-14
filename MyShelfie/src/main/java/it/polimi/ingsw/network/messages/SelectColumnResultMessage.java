package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.network.messages.enums.MessageType;
import it.polimi.ingsw.network.messages.enums.ResponseResultType;

/**
 * This message contains the result of the choice of the shelf column coming from the user.
 * @author Luca Guffanti
 */
public class SelectColumnResultMessage extends Message{

    private final ResponseResultType resultType;
    public SelectColumnResultMessage(String senderUsername, ResponseResultType resultType) {
        super(MessageType.SELECT_COLUMN_RESULT, senderUsername);
        this.resultType = resultType;
    }

    public SelectColumnResultMessage(String senderUsername, String description, ResponseResultType resultType) {
        super(MessageType.SELECT_COLUMN_RESULT, senderUsername, description);
        this.resultType = resultType;
    }

    public ResponseResultType getResultType() {
        return resultType;
    }
}
