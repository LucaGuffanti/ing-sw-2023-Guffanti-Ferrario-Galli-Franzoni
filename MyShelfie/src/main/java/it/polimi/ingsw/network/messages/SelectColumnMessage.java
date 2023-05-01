package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.client.controller.messageHandling.messageHandlers.MessagesHandler;
import it.polimi.ingsw.client.controller.messageHandling.messageHandlers.SelectColumnMessageHandler;
import it.polimi.ingsw.network.messages.enums.MessageType;

/**
 * This message represents the choice of the shelf column in which to put
 * the cards picked from the board. It contains the index, as an integer, of the shelf column,
 * and precedes a SelectColumnMessageResult message, sent to the interest client, that contains
 * the result of the column choice
 * @author Luca Guffanti
 */
public class SelectColumnMessage extends Message{

    private final int columnIndex;
    public SelectColumnMessage(String senderUsername, int columnIndex) {
        super(MessageType.SELECT_COLUMN, senderUsername);
        this.columnIndex = columnIndex;
    }

    public SelectColumnMessage(String senderUsername, String description, int columnIndex) {
        super(MessageType.SELECT_COLUMN, senderUsername, description);
        this.columnIndex = columnIndex;
    }

    public int getColumnIndex() {
        return columnIndex;
    }

    @Override
    public MessagesHandler getHandlerForClient() {
        return new SelectColumnMessageHandler();
    }
}
