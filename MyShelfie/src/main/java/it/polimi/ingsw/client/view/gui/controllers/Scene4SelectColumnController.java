package it.polimi.ingsw.client.view.gui.controllers;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.client.controller.stateController.ClientState;
import it.polimi.ingsw.client.view.cli.Printer;
import it.polimi.ingsw.client.view.gui.Gui;
import it.polimi.ingsw.client.view.gui.MediaManager;
import it.polimi.ingsw.client.view.gui.Renderer;
import it.polimi.ingsw.network.messages.ChatMessage;
import it.polimi.ingsw.network.messages.SelectColumnMessage;
import it.polimi.ingsw.server.model.cards.ObjectTypeEnum;
import it.polimi.ingsw.server.model.cards.goalCards.SimplifiedCommonGoalCard;
import javafx.beans.property.BooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Scene4SelectColumnController implements GameSceneController {
    @FXML
    private Slider sliderVolume;
    @FXML
    private Button b0, b1, b2, b3, b4;
    @FXML
    private Label phaseDescription;
    @FXML
    private Label textError;
    @FXML
    private VBox shelvesBox;
    @FXML
    private GridPane gameShelf;
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
    private List<ImageView> shelfCells = new ArrayList<>();
    private HashMap<ImageView, BooleanProperty> imageToClickedProperty = new HashMap<>();

    @Override
    public void setSliderVolume(double volume) {
        sliderVolume.setValue(volume * 100);
        sliderVolume.valueProperty().addListener(observable -> Gui.instance.getMediaPlayer().setVolume(sliderVolume.getValue() / 100));
    }

    @Override
    public void drawScene(Stage stage) {

        ClientState state = ClientManager.getInstance().getStateContainer().getCurrentState();

        // Displaying personal goals and available points
        SimplifiedCommonGoalCard cg1 = state.getCommonGoalCards().get(0);
        SimplifiedCommonGoalCard cg2 = state.getCommonGoalCards().get(1);

        Image cg1Image = MediaManager.commonGoalToImage.get(MediaManager.jsonCommonGoalIdToResourceId.get(cg1.getId()));
        commonGoal1.setImage(cg1Image);

        if(cg1.getPointCards().size()>=1) {
            cg_1_1.setImage(MediaManager.pointToImage.get(cg1.getPointCards().get(0).getType()));
        }
        if(cg1.getPointCards().size()>=2) {
            cg_1_2.setImage(MediaManager.pointToImage.get(cg1.getPointCards().get(1).getType()));
        }
        if(cg1.getPointCards().size()>=3) {
            cg_1_3.setImage(MediaManager.pointToImage.get(cg1.getPointCards().get(2).getType()));
        }
        if(cg1.getPointCards().size()==4) {
            cg_1_4.setImage(MediaManager.pointToImage.get(cg1.getPointCards().get(3).getType()));
        }

        Image cg2Image = MediaManager.commonGoalToImage.get(MediaManager.jsonCommonGoalIdToResourceId.get(cg2.getId()));
        commonGoal2.setImage(cg2Image);

        if(cg2.getPointCards().size()>=1) {
            cg_2_1.setImage(MediaManager.pointToImage.get(cg2.getPointCards().get(0).getType()));
        }
        if(cg2.getPointCards().size()>=2) {
            cg_2_2.setImage(MediaManager.pointToImage.get(cg2.getPointCards().get(1).getType()));
        }
        if(cg2.getPointCards().size()>=3) {
            cg_2_3.setImage(MediaManager.pointToImage.get(cg2.getPointCards().get(2).getType()));
        }
        if(cg2.getPointCards().size()==4) {
            cg_2_4.setImage(MediaManager.pointToImage.get(cg2.getPointCards().get(3).getType()));
        }

        // Displaying the personal goal card
        Image pgImage = MediaManager.personalGoalToImage.get(state.getPersonalGoalCardId());
        personalGoal.setImage(pgImage);

        phaseDescription.setText("Select a column");


        gameShelf.getChildren().clear();
        // Displaying the shelf
        ObjectTypeEnum[][] shelf = state.getShelves().get(state.getOrderedPlayersNames().indexOf(state.getActivePlayer()));
        Printer.printShelf(shelf);
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                if (shelf[i][j] != null) {
                    System.out.println(shelf[i][j]);

                    // Dynamically generate an ImageView

                    ImageView imgView = new ImageView(MediaManager.tileToImage.get(shelf[i][j]));
                    shelfCells.add(imgView);

                    // if the scene is for the pick from board phase, set the property regarding the
                    // clicking of the image.

                    gameShelf.add(imgView ,j, i);
                } else {
                    shelfCells.add(new ImageView());
                }

            }
        }

        shelvesBox.getChildren().clear();
        renderShelves();
    }

    public void renderShelves() {
        Renderer.renderShelves(shelvesBox);
    }

    public void submitSelection(javafx.event.ActionEvent actionEvent) {
        int col;
        String buttonId = ((Button) actionEvent.getSource()).getText();
        System.out.println(buttonId);
        if (buttonId.equals("0")) {
            col = 0;
        } else if (buttonId.equals("1")) {
            col = 1;
        } else if (buttonId.equals("2")) {
            col = 2;
        } else if (buttonId.equals("3")) {
            col = 3;
        } else {
            col = 4;
        }
        String name = ClientManager.getInstance().getStateContainer().getCurrentState().getUsername();
        System.out.println("Submitting selection for "+ name);
        ClientManager.getInstance().getNetworkHandler().sendMessage(
                new SelectColumnMessage(
                        name,
                        col
                )
        );
    }

    @Override
    public void setLabelErrorMessage(String message) {
        textError.setText(message);
    }

    public void renderName(String name) {
        phaseDescription.setText("It's " + name + "'s turn");
    }

    @Override
    public void updateChat(ChatMessage chatMessage) {

    }
}
