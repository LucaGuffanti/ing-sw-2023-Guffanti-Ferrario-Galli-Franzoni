package it.polimi.ingsw.client.view.gui.controllers;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.client.view.gui.Gui;
import it.polimi.ingsw.network.messages.ChatMessage;
import it.polimi.ingsw.network.messages.FoundSavedGameResponseMessage;
import it.polimi.ingsw.network.messages.enums.ReloadGameChoice;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;

public class Scene3LoadGameController implements SceneWithChatController {
    @FXML
    private Label labelErrorLoadGame;
    @FXML
    private Slider sliderVolume;
    @FXML
    private Button buttonYesLoadGame;
    @FXML
    private Button buttonNoLoadGame;
    @FXML
    private Label labelLoadGame;

    @Override
    public void setSliderVolume(double volume) {
        sliderVolume.setValue(volume * 100);
        sliderVolume.valueProperty().addListener(observable -> Gui.instance.getMediaPlayer().setVolume(sliderVolume.getValue() / 100));
    }


    @Override
    public void setLabelErrorMessage(String message) {
        labelErrorLoadGame.setText(message);
    }

    public void yesLoad() {
        ClientManager.getInstance().getNetworkHandler().sendMessage(
                new FoundSavedGameResponseMessage(
                        ClientManager.getInstance().getStateContainer().getCurrentState().getUsername(),
                        ReloadGameChoice.ACCEPT_RELOAD
                )
        );
        buttonYesLoadGame.setDisable(true);
        buttonNoLoadGame.setDisable(true);
        labelLoadGame.setText("Loading...");
    }
    public void noLoad() {
        ClientManager.getInstance().getNetworkHandler().sendMessage(
                new FoundSavedGameResponseMessage(
                        ClientManager.getInstance().getStateContainer().getCurrentState().getUsername(),
                        ReloadGameChoice.DECLINE_RELOAD
                )
        );
        buttonYesLoadGame.setDisable(true);
        buttonNoLoadGame.setDisable(true);
        labelLoadGame.setText("Loading...");
    }

    @Override
    public void updateChat(ChatMessage chatMessage) {

    }
}
