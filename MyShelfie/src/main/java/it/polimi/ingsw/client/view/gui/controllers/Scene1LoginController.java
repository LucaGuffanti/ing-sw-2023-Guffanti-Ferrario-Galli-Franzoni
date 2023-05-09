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

public class Scene1LoginController implements GuiController {
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
        // todo controlla se riesci a loggarti al server, poi cambia la scena o mostra label d'errore

        String nickname = textFieldNickname.getText();
        ClientManager.getInstance().getNetworkHandler().sendMessage(
                new LoginRequestMessage(
                        nickname
                )
        );


        // ESEGUI SE NON RIESCI A LOGGARTI
/*
        labelErrorLogin.setText("ERROR! PLEASE TRY AGAIN");

*/

        // ESEGUI SE SEI IL PRIMO CHE LOGGA AL SERVER
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
        stage.show();

*/

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

    @Override
    public void render(ClientState state, ClientNetworkHandler clientNetworkHandler, Stage stage, Scene scene) throws IOException {
        this.state = state;
        this.clientNetworkHandler = clientNetworkHandler;

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/scene1Login.fxml"));
        Parent root = null;
        root = loader.load();
        Scene sceneNew = new Scene(root, scene.getWidth(), scene.getHeight());
        stage.setScene(sceneNew);
        stage.setMaximized(true);
        stage.setFullScreen(false);
        stage.show();
    }
}
