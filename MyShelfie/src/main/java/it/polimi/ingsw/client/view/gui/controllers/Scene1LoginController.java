package it.polimi.ingsw.client.view.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class Scene1LoginController {
    @FXML
    private Label labelErrorLogin;

    @FXML
    protected void serverLogin(ActionEvent actionEvent) throws IOException {
        // controlla se riesci a loggarti al server, poi cambia la scena o mostra label d'errore

        labelErrorLogin.setText("ERROR! PLEASE TRY AGAIN");

        Parent newSceneParent = FXMLLoader.load(getClass().getResource("scene2CreateJoin.fxml"));
        Scene newScene = new Scene(newSceneParent);

        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(newScene);
        window.show();
    }
}
