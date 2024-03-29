package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.client.controller.messageHandling.messageHandlers.MessagesHandler;
import it.polimi.ingsw.client.controller.messageHandling.messageHandlers.PickFromBoardMessageHandler;
import it.polimi.ingsw.network.messages.enums.MessageType;
import it.polimi.ingsw.network.messages.enums.ResponseResultType;

/**
 * This message is sent to the client as a validation of the PickFromBoardMessage.
 * It contains the result of the picking, as Success or Failure.
 * @author Luca Guffanti
 */
public class PickFromBoardResultMessage extends Message implements MessageWithResult{
    private final ResponseResultType resultType;
    private final String recipient;
    public PickFromBoardResultMessage(String senderUsername, ResponseResultType resultType, String recipient) {
        super(MessageType.PICK_FROM_BOARD_RESULT, senderUsername);
        this.resultType = resultType;
        this.recipient = recipient;
    }

    public PickFromBoardResultMessage(String senderUsername, String description, ResponseResultType resultType, String recipient) {
        super(MessageType.PICK_FROM_BOARD_RESULT, senderUsername, description);
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
        return new PickFromBoardMessageHandler();
    }
}
