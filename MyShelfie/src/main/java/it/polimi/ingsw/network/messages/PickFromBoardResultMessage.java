package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.network.messages.enums.MessageType;
import it.polimi.ingsw.network.messages.enums.ResponseResultType;

/**
 * This message is sent to the client as a validation of the PickFromBoardMessage.
 * It contains the result of the picking, as Success or Failure.
 * @author Luca Guffanti
 */
public class PickFromBoardResultMessage extends Message{
    private final ResponseResultType resultType;
    public PickFromBoardResultMessage(String senderUsername, ResponseResultType resultType) {
        super(MessageType.PICK_FROM_BOARD, senderUsername);
        this.resultType = resultType;
    }

    public PickFromBoardResultMessage(String senderUsername, String description, ResponseResultType resultType) {
        super(MessageType.PICK_FROM_BOARD, senderUsername, description);
        this.resultType = resultType;
    }

    public ResponseResultType getResultType() {
        return resultType;
    }
}
