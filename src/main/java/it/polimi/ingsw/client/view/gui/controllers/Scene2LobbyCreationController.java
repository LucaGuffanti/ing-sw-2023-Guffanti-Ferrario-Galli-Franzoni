package it.polimi.ingsw.client.view.gui.controllers;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.client.view.gui.Gui;
import it.polimi.ingsw.network.messages.NumberOfPlayersSelectionMessage;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;

public class Scene2LobbyCreationController implements SceneController {
    @FXML
    private Label labelErrorCreationLobby;
    @FXML
    private TextField textFieldNumberPlayers;
    @FXML
    private Slider sliderVolume;

    @Override
    public void setLabelErrorMessage(String message) {
        labelErrorCreationLobby.setText(message);
    }

    @Override
    public void setSliderVolume(double volume) {
        sliderVolume.setValue(volume * 100);
        sliderVolume.valueProperty().addListener(observable -> Gui.instance.getMediaPlayer().setVolume(sliderVolume.getValue() / 100));
    }

    @FXML
    protected void createLobby() {
        String numberPlayers = textFieldNumberPlayers.getText();
        int num = 0;
        try {
            num = Integer.parseInt(numberPlayers);
        } catch (NumberFormatException e) {
            labelErrorCreationLobby.setText("INVALID NUMBER!");
        }
        if (numberPlayers.length() == 0) {
            labelErrorCreationLobby.setText("INSERT THE NUMBER OF PLAYERS!");
        } else if(num < 2 || num > 4) {
            labelErrorCreationLobby.setText("THE NUMBER MUST BE FROM 2 TO 4!");
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