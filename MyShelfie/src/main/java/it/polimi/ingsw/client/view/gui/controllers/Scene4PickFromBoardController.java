package it.polimi.ingsw.client.view.gui.controllers;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.client.controller.stateController.ClientState;
import it.polimi.ingsw.client.view.gui.MediaManager;
import javafx.fxml.FXML;
import javafx.scene.layout.HBox;

import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class Scene4PickFromBoardController implements GameSceneController {

    @FXML
    private ImageView personalGoal;
    @FXML
    private ImageView commonGoal1;
    @FXML
    private ImageView commonGoal2;
    @FXML
    private HBox cardContainer;


    @Override
    public void drawScene(Stage stage) {
        ClientState state = ClientManager.getInstance().getStateContainer().getCurrentState();
        // showing the personal goals and common goals
        personalGoal.setImage(MediaManager.personalGoalToImage.get(state.getPersonalGoalCardId()));
        commonGoal1.setImage(MediaManager.commonGoalToImage.get(MediaManager.jsonCommonGoalIdToResourceId.get(state.getCommonGoalCards().get(0).getId())));
        commonGoal2.setImage(MediaManager.commonGoalToImage.get(MediaManager.jsonCommonGoalIdToResourceId.get(state.getCommonGoalCards().get(1).getId())));

    }


    @Override
    public void setLabelErrorMessage(String message) {

    }
}
