package it.polimi.ingsw.client.view.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class Scene2LobbyCreationController {
    @FXML
    private Label labelCreationLobby;
    @FXML
    private TextField textFieldNumberPlayers;
    @FXML
    private Button buttonJoinLobby;

    @FXML
    protected void createLobby(ActionEvent actionEvent) throws IOException {
        // todo controlla se riesci a creare una partita, poi cambia scena o mostra label d'errore

        String numberPlayers = textFieldNumberPlayers.getText();

        // ESEGUI SE VIENE INSERITO UN NUMERO NON VALIDO DI GIOCATORI
        labelCreationLobby.setText("INVALID NUMBER!");

        // ESEGUI QUANDO IL NUMERO DI GIOCATORI È VALIDO
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/scene3Lobby.fxml"));
        Parent root = null;
        root = loader.load();
        Scene scene = ((Node) actionEvent.getSource()).getScene();
        Scene sceneNew = new Scene(root, scene.getWidth(), scene.getHeight());
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(sceneNew);
        stage.setMaximized(true);
        stage.setFullScreen(false);
        stage.show();
    }

    @FXML
    public void joinLobby(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/scene3Lobby.fxml"));
        Parent root = null;
        root = loader.load();
        Scene scene = ((Node) actionEvent.getSource()).getScene();
        Scene sceneNew = new Scene(root, scene.getWidth(), scene.getHeight());
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(sceneNew);
        stage.setMaximized(true);
        stage.setFullScreen(false);
        Button buttonStartGame = (Button) sceneNew.lookup("#buttonStartGame");
        buttonStartGame.setVisible(false);
        Label labelLobby = (Label) sceneNew.lookup("#labelLobby");
        labelLobby.setText("Waiting for host to start a new game!");
        stage.show();
    }

    // todo se non sono l'host, una volta creata la lobby esegui il medoto induceActionEvent
    public void induceActionEvent() {
        ActionEvent actionEvent = new ActionEvent(buttonJoinLobby, null);
        buttonJoinLobby.fireEvent(actionEvent);
    }
}