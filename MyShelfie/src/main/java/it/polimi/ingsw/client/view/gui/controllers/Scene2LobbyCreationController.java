package it.polimi.ingsw.client.view.gui.controllers;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.client.controller.stateController.ClientState;
import it.polimi.ingsw.client.view.gui.Gui;
import it.polimi.ingsw.network.ClientNetworkHandler;
import it.polimi.ingsw.network.messages.LoginRequestMessage;
import it.polimi.ingsw.network.messages.NumberOfPlayersSelectionMessage;
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
import javafx.stage.Stage;

import java.io.IOException;

public class Scene2LobbyCreationController {
    @FXML
    private Label labelCreationLobby;
    @FXML
    private TextField textFieldNumberPlayers;
    @FXML
    private Button buttonJoinLobby;
    private ClientState state;
    private ClientNetworkHandler clientNetworkHandler;

    @FXML
    protected void createLobby(ActionEvent actionEvent) throws IOException {
        String numberPlayers = textFieldNumberPlayers.getText();
        int num = 0;
        try {
            num = Integer.parseInt(numberPlayers);
        } catch (NumberFormatException e) {
            labelCreationLobby.setText("INVALID NUMBER!");
        }
        if (num < 2 || num > 4) {
            labelCreationLobby.setText("INVALID NUMBER!");
        } else {
            ClientManager.getInstance().getNetworkHandler().sendMessage(
                    new NumberOfPlayersSelectionMessage(
                            ClientManager.getInstance().getStateContainer().getCurrentState().getUsername(),
                            num
                    )
            );
        }
    }
}