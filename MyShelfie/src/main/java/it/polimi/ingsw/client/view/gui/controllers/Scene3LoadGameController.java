package it.polimi.ingsw.client.view.gui.controllers;

import it.polimi.ingsw.client.view.gui.Gui;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;

public class Scene3LoadGameController implements SceneController {
    @FXML
    private Label labelErrorLoadGame;
    @FXML
    private Slider sliderVolume;

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

    }
    public void noLoad() {

    }
}
