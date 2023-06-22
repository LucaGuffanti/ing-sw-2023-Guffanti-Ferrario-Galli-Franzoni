package it.polimi.ingsw.client.view.gui.controllers;

import it.polimi.ingsw.client.controller.ClientManager;

import it.polimi.ingsw.client.view.gui.Gui;
import it.polimi.ingsw.network.messages.LoginRequestMessage;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for login scene.
 * @author Marco Galli
 */
public class Scene1LoginController implements SceneController, Initializable {
    /**
     * The label for error messages
     */
    @FXML
    private Label labelErrorLogin;

    /**
     * The text field where a player inserts his nickname
     */
    @FXML
    private TextField textFieldNickname;

    /**
     * The main container of the scene
     */
    @FXML
    private BorderPane borderPaneLogin;

    /**
     * The volume slider
     */
    @FXML
    private Slider sliderVolume;

    /**
     * This method initializes the scene making a fade-in effect
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        borderPaneLogin.setOpacity(0);
        makeFadeIn();
    }

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
     * Fade-in effect for initialization
     */
    private void makeFadeIn() {
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(1500));
        fadeTransition.setNode(borderPaneLogin);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();
    }

    /**
     * This method sets the error message in the label for error messages
     * @param message the error message
     */
    @Override
    public void setLabelErrorMessage(String message) {
        labelErrorLogin.setText(message);
    }

    /**
     * This method sends a login request message to the network handler with the player nickname
     */
    @FXML
    protected void serverLogin() {
        String nickname = textFieldNickname.getText();
        if (nickname.length() > 0) {
            ClientManager.getInstance().getNetworkHandler().sendMessage(
                    new LoginRequestMessage(
                            nickname
                    )
            );
        } else {
            labelErrorLogin.setText("INSERT A VALID NAME!");
        }
    }
}
