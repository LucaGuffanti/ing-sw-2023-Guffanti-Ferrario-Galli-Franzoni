package it.polimi.ingsw.client.view.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class Scene3LobbyController {
    @FXML
    private Label labelErrorStartGame;
    @FXML
    private Button buttonStartGame;

    @FXML
    public void startGame(ActionEvent actionEvent) throws IOException {
        // todo controlla se riesci a startare la partita, poi cambia la scena o mostra label d'errore

        // ESEGUI SE NON RIESCI A STARTARE IL GIOCO
        labelErrorStartGame.setText("ERROR, COULDN'T START GAME, TRY AGAIN!");

        //ESEGUI SE IL NUMERO DI GIOCATORI CORRISPONDE AI GIOCATORI LOGGATI
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/scene4Game.fxml"));
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

    // todo se il numero di giocatori nella lobby corrisponde al numero di giocatori della partita attiva il bottone
    protected void correctNumberOfPlayers() {
        buttonStartGame.setDisable(false);
    }
}
