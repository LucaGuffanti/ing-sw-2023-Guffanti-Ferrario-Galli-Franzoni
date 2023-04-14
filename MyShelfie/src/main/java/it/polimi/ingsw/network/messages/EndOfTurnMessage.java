package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.network.messages.enums.MessageType;

/**
 * This message signals the end of a turn and contains information regarding the recently finished
 * turn.
 * <ol>
 *     <li>
 *         The updated board
 *     </li>
 *     <li>
 *         The Shelf of the player who was active, properly updated
 *     </li>
 *     <li>
 *         The two common goal cards, for which the point card stack may vary.
 *     </li>
 *     <li>
 *         The first and second comm
 *     </li>
 *     <li>
 *         Boolean flags that mark whether the user has completed the common goals
 *     </li>
 *     <li>
 *         Boolean flag that marks whether the user has completed the shelf
 *     </li>
 * </ol>
 *
 * This message is sent to every connected client.
 * @author Luca Guffanti
 */
public class EndOfTurnMessage extends Message{
    //TODO ADD ATTRIBUTES
    public EndOfTurnMessage(String senderUsername) {
        super(MessageType.END_OF_TURN, senderUsername);
    }

    public EndOfTurnMessage(String senderUsername, String description) {
        super(MessageType.END_OF_TURN, senderUsername, description);
    }
}
