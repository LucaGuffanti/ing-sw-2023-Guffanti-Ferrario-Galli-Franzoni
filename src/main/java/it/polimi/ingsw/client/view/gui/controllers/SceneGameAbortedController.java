package it.polimi.ingsw.client.view.gui.controllers;

import it.polimi.ingsw.client.view.gui.Gui;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.stage.Stage;

/**
 * Controller for game aborted scene
 * @author Marco Galli
 */
public class SceneGameAbortedController implements SceneController {
    /**
     * The close button that closes the window
     */
    @FXML
    private Button buttonClose;

    /**
     * The volume slider
     */
    @FXML
    private Slider sliderVolume;

    /**
     * The label for error messages
     */
    @FXML
    private Label labelErrorGameAborted;

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
    public void setLabelErrorMessage(String message) {labelErrorGameAborted.setText(message);}

    /**
     * The method that is invoked when the close button is pressed
     */
    public void close() {
        Stage stage = (Stage) buttonClose.getScene().getWindow();
        stage.close();
        System.exit(0);
    }
}
