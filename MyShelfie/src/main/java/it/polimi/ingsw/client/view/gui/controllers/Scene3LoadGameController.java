package it.polimi.ingsw.client.view.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class Scene3LoadGameController {
    @FXML
    Label labelErrorLoadGame;
    public void yesLoad() {
        labelErrorLoadGame.setText("FAILED TO LOAD THE GAME");
    }

    public void noLoad() {
        labelErrorLoadGame.setText("FAILED TO START THE GAME");
    }
}
