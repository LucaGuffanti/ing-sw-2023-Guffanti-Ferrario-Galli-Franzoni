package it.polimi.ingsw.client.view.gui.controllers;

import it.polimi.ingsw.network.messages.ChatMessage;

public interface SceneWithChatController extends SceneController{
    void updateChat(ChatMessage chatMessage);
}
