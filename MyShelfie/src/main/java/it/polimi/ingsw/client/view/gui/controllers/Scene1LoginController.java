package it.polimi.ingsw.client.view.gui.controllers;

import it.polimi.ingsw.client.controller.ClientManager;

import it.polimi.ingsw.network.messages.LoginRequestMessage;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class Scene1LoginController implements SceneController {
    @FXML
    private Label labelErrorLogin;

    @FXML
    private TextField textFieldNickname;

    @Override
    public void setLabelErrorMessage(String message) {
        labelErrorLogin.setText(message);
    }

    @FXML
    protected void serverLogin() {
        String nickname = textFieldNickname.getText();
        if (nickname.length() > 0) {
            ClientManager.getInstance().getNetworkHandler().sendMessage(
                    new LoginRequestMessage(
                            nickname
                    )
            );
        } else {
            labelErrorLogin.setText("INSERT A VALID NAME!");
        }
    }
}
