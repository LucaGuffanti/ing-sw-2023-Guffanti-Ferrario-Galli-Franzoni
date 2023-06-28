package it.polimi.ingsw.client.view.gui;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.client.controller.stateController.ClientState;
import it.polimi.ingsw.network.messages.ChatMessage;
import it.polimi.ingsw.server.model.cards.ObjectTypeEnum;
import it.polimi.ingsw.server.model.cards.PointEnumeration;
import it.polimi.ingsw.server.model.cards.goalCards.SimplifiedCommonGoalCard;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This class contains methods that render gui elements.
 * @author Luca Guffanti, Marco Galli
 */
public class Renderer {
    /**
     *  The map which contains list of point enumeration mapped to number of players.
     */
    public static Map<Integer, List<PointEnumeration>> nPlayersToPoints =
            Map.of(
                    2, List.of(PointEnumeration.FOUR_POINTS, PointEnumeration.EIGHT_POINTS),
                    3, List.of(PointEnumeration.FOUR_POINTS, PointEnumeration.SIX_POINTS, PointEnumeration.EIGHT_POINTS),
                    4, List.of(PointEnumeration.TWO_POINTS, PointEnumeration.FOUR_POINTS, PointEnumeration.SIX_POINTS, PointEnumeration.EIGHT_POINTS)
            );

    /**
     * This method renders shelves and calls the method that renders common goal cards with their points
     * located on the left side of the window.
     * @param shelvesBox the container where shelves are rendered
     * @param cg1Points the container of points for the first common goal
     * @param cg2Points the container of points for the second common goal
     * @param cg1View the image of the first common goal
     * @param cg2View the image of the second common goal
     */
    public static void renderShelvesAndCards(VBox shelvesBox,
                                             GridPane cg1Points,
                                             GridPane cg2Points,
                                             ImageView cg1View,
                                             ImageView cg2View) {
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
        int tileSide = 19;
        int endOfGameCardSide = 30;
        int commonFirstPointCardSize = 30;
        int commonSecondPointCardSize = 30;

        int hm = 5;
        int vm = 2;
        int gridLayoutX = 88;
        int gridLayoutY = 5;


        int playerIndex = 0;

        for (String playerName: playerNames) {
            System.out.println("SHELF FOR " + playerName);

            int _tileSide;
            int _gridLayoutX;
            int _gridLayoutY;
            int _hm;
            int _vm;

            GridPane shelfGrid = new GridPane();
            if(playerName.equals(state.getUsername())){
                _tileSide = Math.round(tileSide*zoom);
                _gridLayoutX = Math.round(gridLayoutX/zoom);
                _gridLayoutY = Math.round(gridLayoutY*zoom);
                _hm = Math.round((hm*zoom)+1);
                _vm = Math.round((vm*zoom)+1);

                // building the GRIDPANE with IMAGEVIEWS
                shelfGrid.setHgap(_hm);
                shelfGrid.setVgap(_vm);
                shelfGrid.setLayoutX(_gridLayoutX);
                shelfGrid.setLayoutY(_gridLayoutY);

                ObjectTypeEnum[][] shelf = state.getShelves().get(playerIndex);
                for (int i = 0; i < 6; i++) {
                    for (int j = 0; j < 5; j++) {
                        ImageView imageView = new ImageView(MediaManager.tileToImage.get(shelf[i][j]));
                        imageView.setFitWidth(_tileSide);
                        imageView.setFitHeight(_tileSide);
                        if (i == 4) {
                            imageView.setFitHeight(_tileSide-1);
                        }
                        shelfGrid.add(imageView, j, i);
                    }
                }
            } else {
                _tileSide = tileSide;
                _gridLayoutX = gridLayoutX;
                _gridLayoutY = gridLayoutY;
                _hm = hm;
                _vm = vm;

                // building the GRIDPANE with IMAGEVIEWS
                shelfGrid.setHgap(_hm+1);
                shelfGrid.setVgap(_vm);
                shelfGrid.setLayoutX(_gridLayoutX);
                shelfGrid.setLayoutY(_gridLayoutY);

                ObjectTypeEnum[][] shelf = state.getShelves().get(playerIndex);
                for (int i = 0; i < 6; i++) {
                    for (int j = 0; j < 5; j++) {
                        ImageView imageView = new ImageView(MediaManager.tileToImage.get(shelf[i][j]));
                        imageView.setFitWidth(_tileSide+1);
                        imageView.setFitHeight(_tileSide+1);
                        if (j == 2 || j == 3) {
                            Pane paneContainingImage = new Pane(imageView);
                            imageView.setLayoutX(-1);

                            paneContainingImage.setMaxWidth(_tileSide + 1);
                            paneContainingImage.setMinWidth(_tileSide + 1);
                            paneContainingImage.setPrefWidth(_tileSide + 1);

                            paneContainingImage.setMaxHeight(_tileSide + 1);
                            paneContainingImage.setMinHeight(_tileSide + 1);
                            paneContainingImage.setPrefHeight(_tileSide + 1);

                            shelfGrid.add(paneContainingImage, j, i);
                        } else if (j == 4) {
                            Pane paneContainingImage = new Pane(imageView);
                            imageView.setLayoutX(-2);

                            paneContainingImage.setMaxWidth(_tileSide + 1);
                            paneContainingImage.setMinWidth(_tileSide + 1);
                            paneContainingImage.setPrefWidth(_tileSide + 1);

                            paneContainingImage.setMaxHeight(_tileSide + 1);
                            paneContainingImage.setMinHeight(_tileSide + 1);
                            paneContainingImage.setPrefHeight(_tileSide + 1);

                            shelfGrid.add(paneContainingImage, j, i);
                        } else {
                            shelfGrid.add(imageView, j, i);
                        }
                    }
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
            if (playerName.equals(firstToCompleteTheShelf)) {
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



        renderCards(cg1Points, cg2Points, cg1View, cg2View);
    }

    /**
     * This method renders the common goal cards and their relative points.
     * @param cg1Points the container of points for the first common goal
     * @param cg2Points the container of points for the second common goal
     * @param commonGoal1 the image of the first common goal
     * @param commonGoal2 the image of the second common goal
     */
    public static void renderCards(
            GridPane cg1Points,
            GridPane cg2Points,
            ImageView commonGoal1,
            ImageView commonGoal2
    ) {

        ClientState state = ClientManager.getInstance().getStateContainer().getCurrentState();

        SimplifiedCommonGoalCard cg1 = ClientManager.getInstance().getStateContainer().getCurrentState().getCommonGoalCards().get(0);
        SimplifiedCommonGoalCard cg2 = ClientManager.getInstance().getStateContainer().getCurrentState().getCommonGoalCards().get(1);

        // clearing the view
        cg1Points.getChildren().clear();
        cg2Points.getChildren().clear();

        System.out.println(cg1.getNickToEarnedPoints().toString());
        System.out.println(cg2.getNickToEarnedPoints().toString());

        // calculating the number of points made with every card
        int cg1MadePoints = 0;
        for (String player : cg1.getNickToEarnedPoints().keySet()) {
            if (cg1.getNickToEarnedPoints().get(player) != null) {
                cg1MadePoints++;
            }
        }
        System.out.println("cg1 made:" + cg1MadePoints);
        int cg2MadePoints = 0;
        for (String player : cg2.getNickToEarnedPoints().keySet()) {
            if (cg2.getNickToEarnedPoints().get(player) != null) {
                cg2MadePoints++;
            }
        }
        System.out.println("cg2 made:" + cg2MadePoints);

        int nPlayers = cg1.getNickToEarnedPoints().size();

        int cg1RemainingPoints = nPlayers - cg1MadePoints;
        int cg2RemainingPoints = nPlayers - cg2MadePoints;

        System.out.println("cg1:" + cg1RemainingPoints);
        System.out.println("cg2:" + cg2RemainingPoints);

        List<PointEnumeration> cg1AssignablePoints = nPlayersToPoints.get(nPlayers);
        List<PointEnumeration> cg2AssignablePoints = nPlayersToPoints.get(nPlayers);

        for (int i = 0; i < cg1RemainingPoints; i++) {
            Pane p = new Pane();
            ImageView imageView = new ImageView(MediaManager.pointToImage.get(cg1AssignablePoints.get(i)));
            imageView.setFitWidth(60);
            imageView.setFitHeight(60);
            p.getChildren().add(imageView);
            cg1Points.add(p, i, 0);
        }


        for (int i = 0; i < cg2RemainingPoints; i++) {
            Pane p = new Pane();
            ImageView imageView = new ImageView(MediaManager.pointToImage.get(cg2AssignablePoints.get(i)));
            imageView.setFitWidth(60);
            imageView.setFitHeight(60);
            p.getChildren().add(imageView);
            cg2Points.add(p, i, 0);
        }

        // displaying the image of the common goals

        Image cg1Image = MediaManager.commonGoalToImage.get(MediaManager.jsonCommonGoalIdToResourceId.get(cg1.getId()));
        commonGoal1.setImage(cg1Image);

        Image cg2Image = MediaManager.commonGoalToImage.get(MediaManager.jsonCommonGoalIdToResourceId.get(cg2.getId()));
        commonGoal2.setImage(cg2Image);

    }

    /**
     * This method prints chat messages.
     * @param c the chat message
     * @param ownUsername username of the player
     * @return
     */
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

    /**
     * Prints previous messages when changing between scenes.
     * @param messages chat container
     * @param username username of player
     */
    public static void renderMessages(ListView<Label> messages, String username) {
        List<ChatMessage> messageList;
        List<ChatMessage> chat = ClientManager.getInstance().getStateContainer().getCurrentState().getChatHistory();
        synchronized (chat) {
            messageList = new ArrayList<>(chat);
        }

        messages.getItems().clear();

        for(ChatMessage msg : messageList) {
            Label messageText = new Label(Renderer.printChatMessage(msg, username));
            //.setWrapText(true);
            messageText.setPrefWidth(Region.USE_COMPUTED_SIZE);
            //messageText.setPrefWidth(300);
            messages.getItems().add(0, messageText);
        }
    }

    /**
     * This method renders a new message to the chat
     * @param chatMessage the chat message
     * @param name the name of the user
     * @param messages the list of messages
     */
    public static void renderNewMessage(ChatMessage chatMessage, String name, ListView<Label> messages) {
        Label label = new Label(Renderer.printChatMessage(chatMessage, name));
        label.setPrefWidth(Region.USE_COMPUTED_SIZE);
        messages.getItems().add(0, label);
    }
}