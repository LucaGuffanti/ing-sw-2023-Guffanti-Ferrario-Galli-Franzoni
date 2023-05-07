package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.client.controller.messageHandling.messageHandlers.FoundSavedGameMessageHandler;
import it.polimi.ingsw.client.controller.messageHandling.messageHandlers.MessagesHandler;
import it.polimi.ingsw.network.messages.enums.MessageType;

/**
 * This message is sent to the admin of the game as a notification that a game is saved.
 * A saved game is considered available if
 * <ul>
 *     <li>The number of logged in players is equal to the number of logged in players from the previous game</li>
 *     <li>All the names of the players match the names of the players of the previous game</li>
 * </ul>
 * @author Luca Guffanti
 */
public class FoundSavedGameMessage extends Message{
    public FoundSavedGameMessage(String senderUsername) {
        super(MessageType.FOUND_SAVED_GAME, senderUsername);
    }

    public FoundSavedGameMessage(String senderUsername, String description) {
        super(MessageType.FOUND_SAVED_GAME, senderUsername, description);
    }

    @Override
    public MessagesHandler getHandlerForClient() {
        return new FoundSavedGameMessageHandler();
    }
}
