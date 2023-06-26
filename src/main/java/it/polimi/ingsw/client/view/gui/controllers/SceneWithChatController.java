package it.polimi.ingsw.client.view.gui.controllers;

import it.polimi.ingsw.network.messages.ChatMessage;

/**
 * Interface for scene controllers with chat.
 * @see SceneController
 * @author Luca Guffanti, Marco Galli
 */
public interface SceneWithChatController extends SceneController{
    /**
     * This method updates the chat receiving a message
     * @param chatMessage the message to display on the chat
     */
    void updateChat(ChatMessage chatMessage);

    /**
     * This method gets the message that was being typed before the change of the scene
     * @return the message
     */
    String getTypedMessage();

    /**
     * This method returns the username of the player that a player was chatting with
     * @return the player's username
     */
    String getChatPlayer();

    /**
     * This method sets the message that was being typed before the change of the scene
     */
    void setTypedMessagePlayer(String message, String player);
}
