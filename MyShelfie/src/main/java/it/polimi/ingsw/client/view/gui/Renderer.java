package it.polimi.ingsw.client.view.gui;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.client.controller.stateController.ClientState;
import it.polimi.ingsw.client.view.cli.Printer;
import it.polimi.ingsw.server.model.cards.ObjectTypeEnum;
import it.polimi.ingsw.server.model.cards.PointEnumeration;
import it.polimi.ingsw.server.model.cards.goalCards.SimplifiedCommonGoalCard;
import it.polimi.ingsw.server.model.cells.Coordinates;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.css.PseudoClass;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Renderer {

    public static void renderShelves(VBox shelvesBox) {
        ClientState state = ClientManager.getInstance().getStateContainer().getCurrentState();
        int numOfPlayers = state.getOrderedPlayersNames().size();
        List<String> playerNames = state.getOrderedPlayersNames();
        String firstToCompleteTheShelf = state.getFirstToCompleteShelf();

        shelvesBox.getChildren().clear();

        // =SHELVES RENDERING SCHEMA=
        //  VBOX containing all the shelves
        //      ->VBOX containing the single shelf, the name of player and the points made
        //                  |                                   |
        //       PANE with GRIDPANE with IMAGEVIEWS   HBOX with 3 IMAGEVIEWS and LABEL
        // ===========SIZES==========
        //  Single shelf -> 150x150 as seen in CSS
        //  Gridpane containing cards -> 18x18 tiles with hgap = 6, vgap = 3, padding from below = 8
        //  ImageViews for the object cards -> 18x18
        //  ImageViews for the points ->

        for (int player = 0; player < numOfPlayers; player++) {
            System.out.println("SHELF FOR " + playerNames.get(player));
            // building the GRIDPANE with IMAGEVIEWS
            GridPane shelfGrid = new GridPane();
            shelfGrid.setHgap(6);
            shelfGrid.setVgap(3);
            shelfGrid.setLayoutX(88);
            shelfGrid.setLayoutY(5);

            ObjectTypeEnum[][] shelf = state.getShelves().get(player);
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 5; j++) {
                    ImageView imageView = new ImageView(MediaManager.tileToImage.get(shelf[i][j]));
                    // the shelf is badly drawn
                    if (j == 2) {
                        imageView.setFitWidth(18);
                    } else {
                        imageView.setFitWidth(20);
                    }

                    if (i == 1 || i == 3) {
                        imageView.setFitHeight(18);
                    } else if (i == 4) {
                        imageView.setFitHeight(19);
                    } else {
                        imageView.setFitHeight(20);
                    }

                    shelfGrid.add(imageView, j, i);
                }
            }

            // building the pane containing the grid and setting its class to show the shelf as a background
            Pane paneContainingShelf = new Pane(shelfGrid);
            paneContainingShelf.setMinWidth(300);
            paneContainingShelf.setPrefWidth(300);
            paneContainingShelf.setMaxWidth(300);
            paneContainingShelf.setMinHeight(150);
            paneContainingShelf.setPrefHeight(150);
            paneContainingShelf.setMaxHeight(150);

            paneContainingShelf.getStyleClass().add("shelfInPickOrWaiting");
            // building the label containing the name of the player
            Label playerName = new Label();
            playerName.setText(playerNames.get(player));
            //playerName.setFont(); SETTING THE FONT, WILL BE DONE LATER

            // based on the achievements, build appropriate imageviews
            ImageView completedShelfPoint = null;
            boolean completedShelf = false;
            ImageView firstCommonPointImage = null;
            boolean firstCommonPoint = false;
            ImageView secondCommonPointImage = null;
            boolean secondCommonPoint = false;

            // checking if the player is the first completing the shelf
            if (state.getUsername().equals(firstToCompleteTheShelf)) {
                completedShelf = true;
                completedShelfPoint = new ImageView(MediaManager.endOfGamePoint);
                completedShelfPoint.setFitHeight(30);
                completedShelfPoint.setFitWidth(30);
            }
            // checking if the player was awarded any first common goal points
            if (state.getCommonGoalCards().get(0).getNickToEarnedPoints().get(playerNames.get(player)) != null) {
                firstCommonPoint = true;
                PointEnumeration point = state.getCommonGoalCards().get(0).getNickToEarnedPoints().get(playerNames.get(player)).getType();
                firstCommonPointImage = new ImageView(MediaManager.pointToImage.get(point));
                firstCommonPointImage.setFitHeight(30);
                firstCommonPointImage.setFitWidth(30);
            }
            // checking if the player was awarded any second common goal points
            if (state.getCommonGoalCards().get(1).getNickToEarnedPoints().get(playerNames.get(player)) != null) {
                secondCommonPoint = true;
                PointEnumeration point = state.getCommonGoalCards().get(1).getNickToEarnedPoints().get(playerNames.get(player)).getType();
                secondCommonPointImage = new ImageView(MediaManager.pointToImage.get(point));
                secondCommonPointImage.setFitHeight(30);
                secondCommonPointImage.setFitWidth(30);
            }


            // Setting the Hbox containing the label and the imageviews
            HBox playerData = new HBox();
            playerData.setSpacing(5);
            playerData.setAlignment(Pos.CENTER);
            if (completedShelf) {
                playerData.getChildren().add(completedShelfPoint);
            }
            if (firstCommonPoint) {
                playerData.getChildren().add(firstCommonPointImage);
            }
            if (secondCommonPoint) {
                playerData.getChildren().add(secondCommonPointImage);
            }
            playerData.getChildren().add(playerName);

            // finally building the vbox containing everything
            VBox shelfAndPlayerData = new VBox();
            shelfAndPlayerData.setSpacing(2);
            shelfAndPlayerData.setAlignment(Pos.CENTER);
            shelfAndPlayerData.getChildren().add(paneContainingShelf);
            shelfAndPlayerData.getChildren().add(playerData);

            // and adding this vbox to the external one containing all the shelves
            shelvesBox.getChildren().add(shelfAndPlayerData);
        }
    }

    public static void renderCards(
            ImageView commonGoal1,
            ImageView cg_1_1,
            ImageView cg_1_2,
            ImageView cg_1_3,
            ImageView cg_1_4,
            ImageView commonGoal2,
            ImageView cg_2_1,
            ImageView cg_2_2,
            ImageView cg_2_3,
            ImageView cg_2_4
    ) {
        SimplifiedCommonGoalCard cg1 = ClientManager.getInstance().getStateContainer().getCurrentState().getCommonGoalCards().get(0);
        SimplifiedCommonGoalCard cg2 = ClientManager.getInstance().getStateContainer().getCurrentState().getCommonGoalCards().get(1);

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
    }
}
