package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.client.controller.messageHandling.messageHandlers.MessagesHandler;
import it.polimi.ingsw.network.messages.enums.MessageType;

/**
 * The ReJoinGameMessage is received when a client tries to rejoin the game after a disconnection.
 * Its elaboration by the server produces a ReAccessResultMessage, which contains all the useful information
 * from the game state.
 */
public class ReJoinGameMessage extends Message{
    public ReJoinGameMessage(String senderUsername) {
        super(MessageType.REJOIN_GAME, senderUsername);
    }

    public ReJoinGameMessage(String senderUsername, String description) {
        super(MessageType.REJOIN_GAME, senderUsername, description);
    }

    @Override
    public MessagesHandler getHandlerForClient() {
        return null;
    }
}
