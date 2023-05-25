package it.polimi.ingsw.client.view.gui;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.client.controller.stateController.ClientState;
import it.polimi.ingsw.client.view.cli.Printer;
import it.polimi.ingsw.network.messages.ChatMessage;
import it.polimi.ingsw.server.model.cards.ObjectTypeEnum;
import it.polimi.ingsw.server.model.cards.PointEnumeration;
import it.polimi.ingsw.server.model.cards.goalCards.SimplifiedCommonGoalCard;
import it.polimi.ingsw.server.model.cells.Coordinates;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.css.PseudoClass;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Renderer {

    public static void renderShelves(VBox shelvesBox) {
        ClientState state = ClientManager.getInstance().getStateContainer().getCurrentState();
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

        float zoom = 1.5f;

        int shelfSide = 150;
        int tileSide = 18;
        int endOfGameCardSide = 30;
        int commonFirstPointCardSize = 30;
        int commonSecondPointCardSize = 30;

        int hm = 6;
        int vm = 3;
        int gridLayoutX = 88;
        int gridLayoutY = 5;


        int playerIndex = 0;

        for (String playerName: playerNames) {
            System.out.println("SHELF FOR " + playerName);



            int _tileSide = tileSide;
            int _gridLayoutX = gridLayoutX;
            int _gridLayoutY = gridLayoutY;
            int _hm = hm;
            int _vm = vm;

            if(playerName.equals(state.getUsername())){
                _tileSide = Math.round(tileSide*zoom);
                _gridLayoutX = Math.round(gridLayoutX/zoom);
                _gridLayoutY = Math.round(gridLayoutY*zoom);
                _hm = Math.round(hm*zoom);
                _vm = Math.round(vm*zoom);
            }

            // building the GRIDPANE with IMAGEVIEWS
            GridPane shelfGrid = new GridPane();
            shelfGrid.setHgap(_hm);
            shelfGrid.setVgap(_vm);
            shelfGrid.setLayoutX(_gridLayoutX);
            shelfGrid.setLayoutY(_gridLayoutY);

            ObjectTypeEnum[][] shelf = state.getShelves().get(playerIndex);
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 5; j++) {
                    ImageView imageView = new ImageView(MediaManager.tileToImage.get(shelf[i][j]));

                    // the shelf is badly drawn

                    // j: Columns
                    if (j == 2) {
                        imageView.setFitWidth(_tileSide);
                    } else {
                        imageView.setFitWidth(_tileSide + Math.round(2*zoom));
                    }

                    // i: Rows
                    if (i == 1 || i == 3) {
                        imageView.setFitHeight(_tileSide);
                    } else if (i == 4) {
                        imageView.setFitHeight(_tileSide + Math.round(1*zoom));
                    } else {
                        imageView.setFitHeight(_tileSide + Math.round(2*zoom));
                    }


                    shelfGrid.add(imageView, j, i);
                }
            }

            // building the pane containing the grid and setting its class to show the shelf as a background
            Pane paneContainingShelf = new Pane(shelfGrid);
            int _shelfSide = shelfSide;

            if(playerName.equals(state.getUsername())){
                _shelfSide = Math.round(shelfSide*zoom);
                paneContainingShelf.getStyleClass().add("ownShelfInPickOrWaiting");
            }else{
                paneContainingShelf.getStyleClass().add("shelfInPickOrWaiting");
            }

            // Width
            paneContainingShelf.setMinWidth(shelfSide*2);
            paneContainingShelf.setPrefWidth(shelfSide*2);
            paneContainingShelf.setMaxWidth(shelfSide*2);

            // Height
            paneContainingShelf.setMinHeight(_shelfSide);
            paneContainingShelf.setPrefHeight(_shelfSide);
            paneContainingShelf.setMaxHeight(_shelfSide);

            // building the label containing the name of the player
            Label playerNameLabel = new Label();
            playerNameLabel.setText(playerName);
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
                completedShelfPoint.setFitHeight(endOfGameCardSide);
                completedShelfPoint.setFitWidth(endOfGameCardSide);
            }
            // checking if the player was awarded any first common goal points
            if (state.getCommonGoalCards().get(0).getNickToEarnedPoints().get(playerName) != null) {
                firstCommonPoint = true;
                PointEnumeration point = state.getCommonGoalCards().get(0).getNickToEarnedPoints().get(playerName).getType();
                firstCommonPointImage = new ImageView(MediaManager.pointToImage.get(point));
                firstCommonPointImage.setFitHeight(commonFirstPointCardSize);
                firstCommonPointImage.setFitWidth(commonSecondPointCardSize);
            }
            // checking if the player was awarded any second common goal points
            if (state.getCommonGoalCards().get(1).getNickToEarnedPoints().get(playerName) != null) {
                secondCommonPoint = true;
                PointEnumeration point = state.getCommonGoalCards().get(1).getNickToEarnedPoints().get(playerName).getType();
                secondCommonPointImage = new ImageView(MediaManager.pointToImage.get(point));
                secondCommonPointImage.setFitHeight(commonSecondPointCardSize);
                secondCommonPointImage.setFitWidth(commonSecondPointCardSize);
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
            playerData.getChildren().add(playerNameLabel);

            // finally building the vbox containing everything
            VBox shelfAndPlayerData = new VBox();
            shelfAndPlayerData.setSpacing(2);
            shelfAndPlayerData.setAlignment(Pos.CENTER);
            shelfAndPlayerData.getChildren().add(paneContainingShelf);
            shelfAndPlayerData.getChildren().add(playerData);

            // and adding this vbox to the external one containing all the shelves
            shelvesBox.getChildren().add(shelfAndPlayerData);
            playerIndex ++;
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

        ClientState state = ClientManager.getInstance().getStateContainer().getCurrentState();

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

    public static String printChatMessage(ChatMessage c, String ownUsername) {
        boolean isPrivate = c.getRecipients().size() > 0;
        int numOfOtherActivePlayers = ClientManager.getInstance().getStateContainer().getCurrentState().getOrderedPlayersNames().size() - 1;
        StringBuilder builder = new StringBuilder();
        builder.append("[" + c.getTime() + "]");
        if (isPrivate) {
            if (c.getSenderUsername().equals(ownUsername)) {
                StringBuilder people = new StringBuilder();
                if (c.getRecipients().size() > 1) {
                    people.append("{ ");
                    people.append(c.getRecipients().get(0) + ", ");
                    for (int i = 1; i < c.getRecipients().size() - 1; i++) {
                        people.append(c.getRecipients().get(i) + ", ");
                    }
                    people.append(c.getRecipients().get(c.getRecipients().size() - 1) + " ");
                    if (c.getRecipients().size() > 1) {
                        people.append("}");
                    }
                } else {
                    people.append(c.getRecipients().get(0));
                }
                if (c.getRecipients().size() > 1) {
                    builder.append(" you -> all");
                } else {
                    builder.append(" you -> " + people);
                }
            } else {
                if (c.getRecipients().size() == numOfOtherActivePlayers && numOfOtherActivePlayers != 1) {
                    builder.append(" " + c.getSenderUsername() + " -> all");
                } else {
                    builder.append(" " + c.getSenderUsername() + " -> you");
                }
            }
        } else {
            builder.append(" " + c.getSenderUsername());
        }
        builder.append(" : " + c.getBody());
        return builder.toString();
    }
}
