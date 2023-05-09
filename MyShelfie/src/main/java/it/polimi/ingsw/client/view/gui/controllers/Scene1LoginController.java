package it.polimi.ingsw.client.view.gui.controllers;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.client.controller.stateController.ClientState;
import it.polimi.ingsw.client.controller.stateController.StateContainer;
import it.polimi.ingsw.network.ClientNetworkHandler;
import it.polimi.ingsw.network.messages.LoginRequestMessage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Scene1LoginController {
    @FXML
    private Label labelErrorLogin;
    @FXML
    private TextField textFieldNickname;
    @FXML
    private Button buttonServerLogin;

    private ClientState state;
    private ClientNetworkHandler clientNetworkHandler;

    @FXML
    protected void serverLogin(ActionEvent actionEvent) throws IOException {
        String nickname = textFieldNickname.getText();
        if (nickname.length() > 0) {
            ClientManager.getInstance().getNetworkHandler().sendMessage(
                    new LoginRequestMessage(
                            nickname
                    )
            );
        } else {
            labelErrorLogin.setText("ERROR! PLEASE TRY AGAIN");
        }
    }
}
