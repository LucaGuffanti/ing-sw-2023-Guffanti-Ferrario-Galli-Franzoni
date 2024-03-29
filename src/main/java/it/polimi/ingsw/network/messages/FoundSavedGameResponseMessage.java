package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.client.controller.messageHandling.messageHandlers.FoundSavedGameMessageHandler;
import it.polimi.ingsw.client.controller.messageHandling.messageHandlers.MessagesHandler;
import it.polimi.ingsw.network.messages.enums.MessageType;
import it.polimi.ingsw.network.messages.enums.ReloadGameChoice;

/**
 * This message is sent from the client as a response to the request of reloading a new game, and it contains
 * a response result, which may be positive or negative.
 * In the case of a positive response the old game is loaded, otherwise a new game is created.
 * @author Luca Guffanti
 */
public class FoundSavedGameResponseMessage extends Message {

    private final ReloadGameChoice choice;
    public FoundSavedGameResponseMessage(String senderUsername, ReloadGameChoice choice) {
        super(MessageType.FOUND_SAVED_GAME_RESPONSE, senderUsername);
        this.choice = choice;
    }

    public FoundSavedGameResponseMessage(String senderUsername, String description, ReloadGameChoice choice) {
        super(MessageType.FOUND_SAVED_GAME_RESPONSE, description, senderUsername);
        this.choice = choice;
    }
    @Override
    public MessagesHandler getHandlerForClient() {
        return new FoundSavedGameMessageHandler();
    }

    public ReloadGameChoice getChoice() {
        return choice;
    }
}
