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
    private Label labelErrorCreateJoin;

    @FXML
    protected void createLobby(ActionEvent actionEvent) throws IOException {
        // controlla se riesci a creare una partita, poi cambia scena o mostra label d'errore

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/scene3Lobby.fxml"));
        Parent root = null;
        root = loader.load();
        Scene scene = ((Node) actionEvent.getSource()).getScene();
        Scene sceneNew = new Scene(root, scene.getWidth(), scene.getHeight());
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(sceneNew);
        stage.setFullScreen(false);
        stage.setMaximized(true);
        stage.show();

        labelErrorCreateJoin.setText("A lobby has already been created! You can only join!");
    }

    @FXML
    protected void joinLobby(ActionEvent actionEvent) throws IOException {
        // controlla se riesci a loggarti, poi cambia la scena o mostra label d'errore

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/scene3Lobby.fxml"));
        Parent root = null;
        root = loader.load();
        Scene scene = ((Node) actionEvent.getSource()).getScene();
        Scene sceneNew = new Scene(root, scene.getWidth(), scene.getHeight());
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(sceneNew);
        stage.setFullScreen(false);
        stage.setMaximized(true);
        stage.show();

        labelErrorCreateJoin.setText("A lobby hasn't been created yet! You must create one!");
    }
}
