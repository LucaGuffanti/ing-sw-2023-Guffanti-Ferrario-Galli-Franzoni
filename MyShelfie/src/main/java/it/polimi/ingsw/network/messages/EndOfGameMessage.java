package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.network.messages.enums.MessageType;

import java.util.HashMap;

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
    private final HashMap<String, Integer> nameToPointsMap;
    private final String winner;
    public EndOfGameMessage(String senderUsername,
                            HashMap<String, Integer> nameToPointsMap,
                            String winner) {
        super(MessageType.END_OF_GAME, senderUsername);
        this.nameToPointsMap = nameToPointsMap;
        this.winner = winner;
    }

    public EndOfGameMessage(String senderUsername,
                            String description,
                            HashMap<String, Integer> nameToPointsMap,
                            String winner) {
        super(MessageType.END_OF_GAME, senderUsername, description);
        this.nameToPointsMap = nameToPointsMap;
        this.winner = winner;
    }

    public HashMap<String, Integer> getNameToPointsMap() {
        return nameToPointsMap;
    }

    public String getWinner() {
        return winner;
    }
}
