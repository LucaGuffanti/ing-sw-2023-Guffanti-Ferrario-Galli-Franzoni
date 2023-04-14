package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.network.messages.enums.MessageType;

/**
 * The new player message contains the username of a newly joined player and is
 * broadcast to all the players who've already joined.
 * @author Luca Guffanti
 */
public class NewPlayerMessage extends Message{

    private final String joinedPlayer;
    public NewPlayerMessage(String senderUsername, String joinedPlayer) {
        super(MessageType.NEW_PLAYER, senderUsername);
        this.joinedPlayer = joinedPlayer;
    }

    public NewPlayerMessage(String senderUsername, String description, String joinedPlayer) {
        super(MessageType.NEW_PLAYER, senderUsername, description);
        this.joinedPlayer = joinedPlayer;
    }

    public String getJoinedPlayer() {
        return joinedPlayer;
    }
}
