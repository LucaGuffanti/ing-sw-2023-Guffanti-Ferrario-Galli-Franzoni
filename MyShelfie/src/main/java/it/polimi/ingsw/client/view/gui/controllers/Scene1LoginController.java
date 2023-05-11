package it.polimi.ingsw.client.view.gui.controllers;

import it.polimi.ingsw.client.controller.ClientManager;

import it.polimi.ingsw.network.messages.LoginRequestMessage;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class Scene1LoginController implements SceneController, Initializable {
    @FXML
    private Label labelErrorLogin;
    @FXML
    private TextField textFieldNickname;
    @FXML
    private BorderPane borderPaneLogin;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        borderPaneLogin.setOpacity(0);
        makeFadeIn();
    }

    private void makeFadeIn() {
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(1500));
        fadeTransition.setNode(borderPaneLogin);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();
    }

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
