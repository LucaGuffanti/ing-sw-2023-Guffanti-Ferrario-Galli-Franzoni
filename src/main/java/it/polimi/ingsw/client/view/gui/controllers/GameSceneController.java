package it.polimi.ingsw.client.view.gui.controllers;

import javafx.stage.Stage;

/**
 * Interface for game phases scene controllers.
 * @see SceneWithChatController
 * @author Luca Guffanti
 */
public interface GameSceneController extends SceneWithChatController{
    /**
     * This method draws the scene according to previous events and game state
     * @param stage the stage of the gui
     */
    void drawScene(Stage stage);
}
