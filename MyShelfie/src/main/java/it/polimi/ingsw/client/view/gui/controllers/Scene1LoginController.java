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
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.IOException;

public class Scene1LoginController {
    @FXML
    private Label labelErrorLogin;
    @FXML
    private TextField textFieldNickname;

    @FXML
    protected void serverLogin(ActionEvent actionEvent) throws IOException {
        // todo controlla se riesci a loggarti al server, poi cambia la scena o mostra label d'errore

        String nickname = textFieldNickname.getText();

        // ESEGUI SE NON RIESCI A LOGGARTI
        labelErrorLogin.setText("ERROR! PLEASE TRY AGAIN");

        // ESEGUI SE SEI IL PRIMO CHE LOGGA AL SERVER
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/scene2LobbyCreation.fxml"));
        Parent root = null;
        root = loader.load();
        Scene scene = ((Node) actionEvent.getSource()).getScene();
        Scene sceneNew = new Scene(root, scene.getWidth(), scene.getHeight());
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(sceneNew);
        stage.setMaximized(true);
        stage.setFullScreen(false);
        stage.show();

        // SE NON SEI IL PRIMO CHE LOGGA AL SERVER MA LA LOBBY NON È ANCORA STATA CREATA ESEGUI QUESTO
/*
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/scene2LobbyCreation.fxml"));
        Parent root = null;
        root = loader.load();
        Scene scene = ((Node) actionEvent.getSource()).getScene();
        Scene sceneNew = new Scene(root, scene.getWidth(), scene.getHeight());
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(sceneNew);
        stage.setMaximized(true);
        stage.setFullScreen(false);
        Button buttonCreateLobby = (Button) sceneNew.lookup("#buttonCreateLobby");
        buttonCreateLobby.setVisible(false);
        TextField textFieldNumberPlayers = (TextField) sceneNew.lookup("#textFieldNumberPlayers");
        textFieldNumberPlayers.setVisible(false);
        Label labelCreationLobby = (Label) sceneNew.lookup("#labelCreationLobby");
        labelCreationLobby.setTextFill(Paint.valueOf("#ffedaa"));
        labelCreationLobby.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC,24));
        labelCreationLobby.setText("The host is creating a new lobby. You will enter soon!");
        stage.show();
*/

        // SE NON SEI IL PRIMO CHE LOGGA AL SERVER MA LA LOBBY È GIA STATA CREATA ESEGUI QUESTO
/*
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
*/
    }
}
