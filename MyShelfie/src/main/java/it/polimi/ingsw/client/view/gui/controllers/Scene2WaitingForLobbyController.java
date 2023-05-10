package it.polimi.ingsw.client.view.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class Scene2WaitingForLobbyController implements SceneController {
    @FXML
    Label labelErrorWaitingForLobby;

    @Override
    public void setLabelErrorMessage(String message) {
        labelErrorWaitingForLobby.setText(message);
    }
}
