package it.polimi.ingsw.client.view.gui.controllers;

import it.polimi.ingsw.client.view.gui.Gui;
import it.polimi.ingsw.network.messages.ChatMessage;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class Scene5FinalResultsController implements GameSceneController, Initializable {
    @FXML
    private BorderPane borderPaneFinalResults;
    @FXML
    private Slider sliderVolume;
    @FXML
    private Label labelErrorFinalResults;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        borderPaneFinalResults.setOpacity(0);
        makeFadeIn();
    }

    @Override
    public void setSliderVolume(double volume) {
        sliderVolume.setValue(volume * 100);
        sliderVolume.valueProperty().addListener(observable -> Gui.instance.getMediaPlayer().setVolume(sliderVolume.getValue() / 100));
    }

    private void makeFadeIn() {
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(2000));
        fadeTransition.setNode(borderPaneFinalResults);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();
    }
    @Override
    public void setLabelErrorMessage(String message) {
        labelErrorFinalResults.setText(message);
    }

    @Override
    public void drawScene(Stage stage) {

    }

    @Override
    public void updateChat(ChatMessage chatMessage) {

    }
}
