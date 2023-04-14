package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.network.messages.enums.MessageType;
/**
 * This message signals the end of the game and contains information regarding the recently finished
 * game.
 * <ol>
 *     <li>
 *         The points of every player
 *     </li>
 *     <li>
 *         The nickname of the winner
 *     </li>
 * </ol>
 *
 * This message is sent to every connected client.
 * @author Luca Guffanti
 */
public class EndOfGameMessage extends Message{
    //TODO ADD ATTRIBUTES
    public EndOfGameMessage(String senderUsername) {
        super(MessageType.END_OF_GAME, senderUsername);
    }

    public EndOfGameMessage(String senderUsername, String description) {
        super(MessageType.END_OF_GAME, senderUsername, description);
    }
}
