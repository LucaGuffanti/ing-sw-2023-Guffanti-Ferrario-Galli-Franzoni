package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.client.controller.messageHandling.messageHandlers.BeginningOfTurnHandler;
import it.polimi.ingsw.client.controller.messageHandling.messageHandlers.MessagesHandler;
import it.polimi.ingsw.network.messages.enums.MessageType;

/**
 * This message is sent to all logged in players and represents the beginning of a new turn.
 * It contains the username of player that's active in that turn.
 * @author Luca Guffanti
 */
public class BeginningOfTurnMessage extends Message {

    private final String activeUser;
    public BeginningOfTurnMessage(String senderUsername, String activeUser) {
        super(MessageType.BEGINNING_OF_TURN, senderUsername);
        this.activeUser = activeUser;
    }

    public BeginningOfTurnMessage(String senderUsername, String description, String activeUser) {
        super(MessageType.BEGINNING_OF_TURN, senderUsername, description);
        this.activeUser = activeUser;
    }

    @Override
    public MessagesHandler getHandlerForClient() {
        return new BeginningOfTurnHandler();
    }

    public String getActiveUser() {
        return activeUser;
    }
}
