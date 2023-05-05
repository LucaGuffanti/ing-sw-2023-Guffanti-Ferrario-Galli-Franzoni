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

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/scene2CreateJoin.fxml"));
        Parent root = null;
        root = loader.load();
        Scene scene = new Scene(root);
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
}
