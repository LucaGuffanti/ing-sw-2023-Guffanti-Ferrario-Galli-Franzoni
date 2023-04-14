package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.network.messages.enums.MessageType;

/**
 * A join game message is sent by a client when it wants to join the game. It's important to notice
 * that if a game exists when the request is received the client is simply added to the game, and if a game doesn't exist
 * when the request is received the client becomes the new admin of the game and is asked to choose the number of players
 * so that the game can be created.
 * @author Luca Guffanti
 */
public class JoinGameMessage extends Message{

    private final String userToAccess;

    public JoinGameMessage(String senderUsername, String userToAccess) {
        super(MessageType.JOIN_GAME, senderUsername);
        this.userToAccess = userToAccess;
    }

    public JoinGameMessage(String senderUsername, String description, String userToAccess) {
        super(MessageType.JOIN_GAME, senderUsername, description);
        this.userToAccess = userToAccess;
    }

    public String getUserToAccess() {
        return userToAccess;
    }
}
