package it.polimi.ingsw.client.view.gui.controllers;

import it.polimi.ingsw.network.messages.ChatMessage;

/**
 * Interface for scene controllers with chat.
 * @see SceneController
 * @author Luca Guffanti
 */
public interface SceneWithChatController extends SceneController{
    /**
     * This method updates the chat receiving a message
     * @param chatMessage the message to display on the chat
     */
    void updateChat(ChatMessage chatMessage);
}
