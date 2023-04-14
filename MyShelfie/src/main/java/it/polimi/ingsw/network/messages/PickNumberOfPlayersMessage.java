package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.network.messages.enums.MessageType;

/**
 * this message is sent when a client tries to join a game that doesn't exist yet:
 * the client becomes the admin and creator of the game, and has to decide the number of player
 * the game will have.
 * @author Luca Guffanti
 */
public class PickNumberOfPlayersMessage extends Message {
    public PickNumberOfPlayersMessage(String senderUsername) {
        super(MessageType.PICK_NUMBER_OF_PLAYERS, senderUsername);
    }

    public PickNumberOfPlayersMessage(String senderUsername, String description) {
        super(MessageType.PICK_NUMBER_OF_PLAYERS, senderUsername, description);
    }
}
