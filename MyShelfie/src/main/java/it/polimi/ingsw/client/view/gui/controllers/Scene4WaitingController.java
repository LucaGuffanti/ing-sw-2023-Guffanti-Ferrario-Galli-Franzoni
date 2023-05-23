package it.polimi.ingsw.client.view.gui.controllers;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.client.controller.stateController.ClientState;
import it.polimi.ingsw.client.view.gui.Gui;
import it.polimi.ingsw.client.view.gui.MediaManager;
import it.polimi.ingsw.client.view.gui.Renderer;
import it.polimi.ingsw.server.model.cards.ObjectTypeEnum;
import it.polimi.ingsw.server.model.cards.goalCards.SimplifiedCommonGoalCard;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Scene4WaitingController implements GameSceneController {

    // Player's username displayed on top
    @FXML
    private Label usernameLabel;
    @FXML
    private Label phaseDescription;
    @FXML
    private Label textError;
    @FXML
    private Slider sliderVolume;
    @FXML
    private VBox shelvesBox;
    @FXML
    private GridPane gameBoard;
    @FXML
    private ImageView cg_2_1;
    @FXML
    private ImageView cg_2_2;
    @FXML
    private ImageView cg_2_3;
    @FXML
    private ImageView cg_2_4;
    @FXML
    private ImageView cg_1_1;
    @FXML
    private ImageView cg_1_2;
    @FXML
    private ImageView cg_1_3;
    @FXML
    private ImageView cg_1_4;
    @FXML
    private ImageView personalGoal;
    @FXML
    private ImageView commonGoal1;
    @FXML
    private ImageView commonGoal2;

    @Override
    public void setSliderVolume(double volume) {
        sliderVolume.setValue(volume * 100);
        sliderVolume.valueProperty().addListener(observable -> Gui.instance.getMediaPlayer().setVolume(sliderVolume.getValue() / 100));
    }
    @Override
    public void drawScene(Stage stage) {
        ClientState state = ClientManager.getInstance().getStateContainer().getCurrentState();

        // Displaying personal goals and available points


        // Displaying the personal goal card
        Image pgImage = MediaManager.personalGoalToImage.get(state.getPersonalGoalCardId());
        personalGoal.setImage(pgImage);

        phaseDescription.setText("It's "+ state.getActivePlayer()+"'s turn");
        usernameLabel.setText("Hi, "+state.getUsername());

        renderCards();
        // Displaying the board
        renderBoard();

        // Displaying the shelves
        renderShelves();

    }

    public void renderShelves() {
        Renderer.renderShelves(shelvesBox);
    }

    public void renderCards() {
        Renderer.renderCards(commonGoal1,
                cg_1_1,
                cg_1_2,
                cg_1_3,
                cg_1_4,
                commonGoal2,
                cg_2_1,
                cg_2_2,
                cg_2_3,
                cg_2_4
        );
    }

    public void renderBoard() {
        // destroying the previously built board
        gameBoard.getChildren().clear();
        // Displaying the board
        ObjectTypeEnum[][] board = ClientManager.getInstance().getStateContainer().getCurrentState().getBoard();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] != null) {

                    // Dynamically generate an ImageView and associate it
                    // to a property referring to the image being pressed.
                    // the image is contained in a slightly bigger pane that
                    // permits the highlighting of the image when pressed.


                    ImageView imgView = new ImageView(MediaManager.tileToImage.get(board[i][j]));

                    Pane paneContainingImage = new Pane(imgView);
                    imgView.setLayoutX(3);
                    imgView.setLayoutY(3);

                    paneContainingImage.setMaxWidth(65.0);
                    paneContainingImage.setMinWidth(65.0);
                    paneContainingImage.setPrefWidth(65.0);

                    paneContainingImage.setMaxHeight(65.0);
                    paneContainingImage.setMinHeight(65.0);
                    paneContainingImage.setPrefHeight(65.0);

                    gameBoard.add(paneContainingImage, j, i);
                }
            }
        }
    }

    @Override
    public void setLabelErrorMessage(String message) {
        textError.setText(message);
    }

    public void renderName() {
        phaseDescription.setText("It's " + ClientManager.getInstance().getStateContainer().getCurrentState().getActivePlayer() + "'s turn");
    }
}
