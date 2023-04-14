package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.network.messages.enums.MessageType;

/**
 * This message is broadcast to every player and contains information regarding the various game
 * elements: the representation of the board, the shelves, the common and personal goal cards,
 * the ordered list of players.
 * @author Luca Guffanti
 */
public class GameStartMessage extends Message{


    //TODO ADD GAME-DEPENDENT PARAMETERS based on design choices
    public GameStartMessage(String senderUsername) {
        super(MessageType.GAME_START, senderUsername);
    }

    public GameStartMessage(String senderUsername, String description) {
        super(MessageType.GAME_START, senderUsername, description);
    }
}
