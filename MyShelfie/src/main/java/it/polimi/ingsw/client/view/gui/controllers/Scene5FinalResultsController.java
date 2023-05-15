package it.polimi.ingsw.client.view.gui.controllers;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class Scene5FinalResultsController implements GameSceneController, Initializable {
    @FXML
    BorderPane borderPaneFinalResults;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        borderPaneFinalResults.setOpacity(0);
        makeFadeIn();
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

    }

    @Override
    public void drawScene(Stage stage) {

    }
}
