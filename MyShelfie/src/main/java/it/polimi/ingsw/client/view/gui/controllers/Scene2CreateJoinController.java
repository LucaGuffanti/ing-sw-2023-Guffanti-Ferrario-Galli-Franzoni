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

public class Scene2CreateJoinController {
    @FXML
    private Stage stage;
    @FXML
    private Scene scene;
    @FXML
    private Parent root;
    @FXML
    private Label labelErrorCreateJoin;
    @FXML
    protected void createGame(ActionEvent actionEvent) throws IOException {
        // controlla se riesci a creare una partita, poi cambia scena o mostra label d'errore

        labelErrorCreateJoin.setText("A lobby has already been created! You can only join!");

        root = FXMLLoader.load(getClass().getResource("scene3Lobby.fxml"));
        stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.show();
    }

    @FXML
    protected void joinGame(ActionEvent actionEvent) throws IOException {
        // controlla se riesci a loggarti, poi cambia la scena o mostra label d'errore

        labelErrorCreateJoin.setText("A lobby hasn't been created yet! You must create one!");

        root = FXMLLoader.load(getClass().getResource("scene3Lobby.fxml"));
        stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.show();
    }
}
