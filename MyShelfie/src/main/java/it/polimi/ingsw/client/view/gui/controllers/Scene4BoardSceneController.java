package it.polimi.ingsw.client.view.gui.controllers;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.client.controller.ClientPhasesEnum;
import it.polimi.ingsw.client.controller.stateController.ClientState;
import it.polimi.ingsw.client.controller.utils.PickChecker;
import it.polimi.ingsw.client.view.cli.Printer;
import it.polimi.ingsw.client.view.gui.MediaManager;
import it.polimi.ingsw.network.messages.PickFromBoardMessage;
import it.polimi.ingsw.server.model.cards.ObjectTypeEnum;
import it.polimi.ingsw.server.model.cards.PointEnumeration;
import it.polimi.ingsw.server.model.cards.goalCards.SimplifiedCommonGoalCard;
import it.polimi.ingsw.server.model.cells.Coordinates;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;

import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Scene4BoardSceneController implements GameSceneController {
    @FXML
    private Label phaseDescription;
    @FXML
    private Label textError;
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
    private List<ImageView> boardCells = new ArrayList<>();
    private List<Coordinates> clicked = new ArrayList<>();
    private HashMap<ImageView, BooleanProperty> imageToClickedProperty = new HashMap<>();

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

        phaseDescription.setText("It's your turn");

        // Displaying the board
        ObjectTypeEnum[][] board = state.getBoard();
        Printer.printBoard(board);
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] != null) {

                    // Dynamically generate an ImageView and associate it
                    // to a property referring to the image being pressed.
                    // the image is contained in a slightly bigger pane that
                    // permits the highlighting of the image when pressed.


                    ImageView imgView = new ImageView(MediaManager.tileToImage.get(board[i][j]));
                    boardCells.add(imgView);

                    Pane paneContainingImage = new Pane(imgView);
                    imgView.setLayoutX(3);
                    imgView.setLayoutY(3);

                    paneContainingImage.setMaxWidth(65.0);
                    paneContainingImage.setMinWidth(65.0);
                    paneContainingImage.setPrefWidth(65.0);

                    paneContainingImage.setMaxHeight(65.0);
                    paneContainingImage.setMinHeight(65.0);
                    paneContainingImage.setPrefHeight(65.0);

                    // if the scene is for the pick from board phase, set the property regarding the
                    // clicking of the image.


                    PseudoClass imageViewBorder = PseudoClass.getPseudoClass("border");
                    paneContainingImage.getStyleClass().add("boardCell");
                    BooleanProperty imageViewBorderActive = new SimpleBooleanProperty() {
                        @Override
                        protected void invalidated() {
                            paneContainingImage.pseudoClassStateChanged(imageViewBorder, get());
                        }
                    };

                    imageToClickedProperty.put(imgView, imageViewBorderActive);

                    // set the event to be asynchronously invoked when the imageView is pressed
                    imgView.setOnMouseClicked(event -> {

                        // the border is set
                        boolean selected = !imageViewBorderActive.get(); // if false the tile is unselected
                        // retrieve the x,y coordinates of the clicked image by accessing the index in the linearized imageView list
                        // and by calculating it
                        // indexOfCurrentImage = 9*rowIndex + colIndex
                        // <==> colIndex = indexOfCurrentImage % 9 and rowIndex = (indexOfCurrentImage-colIndex) / 9
                        // example:
                        //  current = 39
                        //      colIndex = 39 % 9 = 3
                        //      rowIndex = (39-3) / 9 = 4

                        Coordinates coordsOfImage = getCoordinatesInBoardMatrix(imgView);


                        if (selected) {
                            System.out.println("Clicked Image on (" + coordsOfImage.getX() + ", " + coordsOfImage.getY() + ")");
                            if (checkCoordinateSelection(clicked, coordsOfImage)) {
                                System.out.println("Valid selection");
                                clicked.add(coordsOfImage);
                                setLabelErrorMessage("");
                                imageViewBorderActive.set(true);
                            } else {
                                System.out.println("Invalid selection");
                                setLabelErrorMessage("You are trying to pick tiles in an incorrect way. Remember that you can pick tiles that have \n at least one free side before the pick " +
                                        "and that all the tiles must be in a straight line");
                            }
                        } else {
                            System.out.println("Unclicked Image on (" + coordsOfImage.getX() + ", " + coordsOfImage.getY() + ")");
                            clicked.remove(coordsOfImage);
                            setLabelErrorMessage("");
                            imageViewBorderActive.set(false);
                        }
                    });

                    gameBoard.add(paneContainingImage ,j, i);
                } else {
                    boardCells.add(new ImageView());
                }
            }
        }

        // Displaying the shelves
        int numOfPlayers = state.getOrderedPlayersNames().size();
        List<String> playerNames = state.getOrderedPlayersNames();
        String firstToCompleteTheShelf = state.getFirstToCompleteShelf();

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
            shelfGrid.setLayoutX(91.5);
            shelfGrid.setLayoutY(9);

            System.out.println("BUILDING SHELF");
            ObjectTypeEnum[][] shelf = state.getShelves().get(player);
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 5; j++) {
                    ImageView imageView = new ImageView(MediaManager.tileToImage.get(shelf[i][j]));
                    imageView.setFitWidth(18);
                    imageView.setFitHeight(18);

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
            System.out.println("BUILT");

            System.out.println("CHECKING ACHIEVEMENTS");
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
            System.out.println("DONE");


            System.out.println("FINAL TOUCHES");
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
            System.out.println("DONE");
        }

    }

    private boolean checkCoordinateSelection(List<Coordinates> clicked, Coordinates coordsOfImage) {
        ObjectTypeEnum[][] board = ClientManager.getInstance().getStateContainer().getCurrentState().getBoard();

        int cx = coordsOfImage.getX();
        int cy = coordsOfImage.getY();

        if (!PickChecker.hasFreeSides(board, cx, cy)) {
            return false;
        }

        return PickChecker.checkAdjacencies(clicked, coordsOfImage);
    }


    private Coordinates getCoordinatesInBoardMatrix(ImageView imgView) {
        int indexOfCurrentImage = boardCells.indexOf(imgView);
        int colIndex = indexOfCurrentImage % 9;
        int rowIndex = (indexOfCurrentImage - colIndex) / 9;

        return new Coordinates(colIndex, rowIndex);
    }


    @Override
    public void setLabelErrorMessage(String message) {
        textError.setText(message);
    }

    public void resetSelection() {
        System.out.println(clicked.toString());
        for (Coordinates i : clicked) {
            int index = i.getY()*9 + i.getX();
            ImageView imgView = boardCells.get(index);
            imageToClickedProperty.get(imgView).set(false);
        }
        clicked.clear();
        setLabelErrorMessage("");
        System.out.println(clicked.toString());
    }

    public void submitSelection() {
        String name = ClientManager.getInstance().getStateContainer().getCurrentState().getUsername();
        System.out.println("Submitting selection for "+ name);
        ClientManager.getInstance().getNetworkHandler().sendMessage(
                new PickFromBoardMessage(
                        name,
                        clicked
                )
        );
    }

}
