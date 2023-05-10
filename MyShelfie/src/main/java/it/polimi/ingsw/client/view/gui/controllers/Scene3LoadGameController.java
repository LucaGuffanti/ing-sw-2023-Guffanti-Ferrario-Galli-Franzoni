package it.polimi.ingsw.client.view.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class Scene3LoadGameController implements SceneController {
    @FXML
    Label labelErrorLoadGame;

    @Override
    public void setLabelErrorMessage(String message) {
        labelErrorLoadGame.setText(message);
    }

    public void yesLoad() {

    }
    public void noLoad() {

    }
}
