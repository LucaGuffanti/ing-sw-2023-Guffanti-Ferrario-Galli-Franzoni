package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.client.controller.messageHandling.messageHandlers.MessagesHandler;
import it.polimi.ingsw.client.controller.messageHandling.messageHandlers.PlayerNumberHandler;
import it.polimi.ingsw.network.messages.enums.MessageType;

/**
 * this message is sent when a client tries to join a game that doesn't exist yet:
 * the client becomes the admin and creator of the game, and has to decide the number of player
 * the game will have.
 * @author Luca Guffanti
 */
public class PickNumberOfPlayersMessage extends Message {

    private final String recipient;
    public PickNumberOfPlayersMessage(String senderUsername, String recipient) {
        super(MessageType.PICK_NUMBER_OF_PLAYERS, senderUsername);
        this.recipient = recipient;
    }

    public PickNumberOfPlayersMessage(String senderUsername, String description, String recipient) {
        super(MessageType.PICK_NUMBER_OF_PLAYERS, senderUsername, description);
        this.recipient = recipient;
    }

    @Override
    public MessagesHandler getHandler() {
        return new PlayerNumberHandler();
    }
    public String getRecipient() {
        return recipient;
    }
}
