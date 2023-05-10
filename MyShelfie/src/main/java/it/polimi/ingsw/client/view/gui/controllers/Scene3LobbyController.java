package it.polimi.ingsw.client.view.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class Scene3LobbyController implements SceneController {
    @FXML
    private Label labelErrorStartGame;

    @Override
    public void setLabelErrorMessage(String message) {
        labelErrorStartGame.setText(message);
    }

}
