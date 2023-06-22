package it.polimi.ingsw.client.view.gui.controllers;

import it.polimi.ingsw.client.view.gui.Gui;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;

/**
 * Controller for waiting for lobby scene
 * @author Marco Galli
 */
public class Scene2WaitingForLobbyController implements SceneController {
    /**
     * The label for error messages
     */
    @FXML
    private Label labelErrorWaitingForLobby;

    /**
     * The volume slider
     */
    @FXML
    private Slider sliderVolume;

    /**
     * This method allows the volume slider to be set
     * @param volume the media player volume
     */
    @Override
    public void setSliderVolume(double volume) {
        sliderVolume.setValue(volume * 100);
        sliderVolume.valueProperty().addListener(observable -> Gui.instance.getMediaPlayer().setVolume(sliderVolume.getValue() / 100));
    }

    /**
     * This method sets the error message in the label for error messages
     * @param message the error message
     */
    @Override
    public void setLabelErrorMessage(String message) {
        labelErrorWaitingForLobby.setText(message);
    }
}
