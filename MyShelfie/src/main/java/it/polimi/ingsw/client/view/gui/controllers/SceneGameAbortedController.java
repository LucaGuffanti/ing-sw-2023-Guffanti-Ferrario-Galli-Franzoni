package it.polimi.ingsw.client.view.gui.controllers;

import it.polimi.ingsw.client.view.gui.Gui;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.stage.Stage;

public class SceneGameAbortedController implements SceneController {
    @FXML
    private Button buttonClose;
    @FXML
    private Slider sliderVolume;

    @Override
    public void setSliderVolume(double volume) {
        sliderVolume.setValue(volume * 100);
        sliderVolume.valueProperty().addListener(observable -> Gui.instance.getMediaPlayer().setVolume(sliderVolume.getValue() / 100));
    }

    @Override
    public void setLabelErrorMessage(String message) {}

    public void close(ActionEvent actionEvent) {
        Stage stage = (Stage) buttonClose.getScene().getWindow();
        stage.close();
    }
}
