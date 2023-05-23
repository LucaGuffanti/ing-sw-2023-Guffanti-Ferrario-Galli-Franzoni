package it.polimi.ingsw.client.view.gui.controllers;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.client.controller.stateController.ClientState;
import it.polimi.ingsw.client.controller.utils.PickChecker;
import it.polimi.ingsw.client.view.cli.Printer;
import it.polimi.ingsw.client.view.gui.Gui;
import it.polimi.ingsw.client.view.gui.MediaManager;
import it.polimi.ingsw.client.view.gui.Renderer;
import it.polimi.ingsw.network.messages.PickFromBoardMessage;
import it.polimi.ingsw.server.model.cards.ObjectTypeEnum;
import it.polimi.ingsw.server.model.cards.goalCards.SimplifiedCommonGoalCard;
import it.polimi.ingsw.server.model.cells.Coordinates;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Scene4BoardSceneController implements GameSceneController {

    // Player's username displayed on top
    @FXML
    private Label usernameLabel;
    @FXML
    private Slider sliderVolume;
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
    public void setSliderVolume(double volume) {
        sliderVolume.setValue(volume * 100);
        sliderVolume.valueProperty().addListener(observable -> Gui.instance.getMediaPlayer().setVolume(sliderVolume.getValue() / 100));
    }

    @Override
    public void drawScene(Stage stage) {
        ClientState state = ClientManager.getInstance().getStateContainer().getCurrentState();

        // Displaying personal goals and available points
        renderCards();
        // Displaying the personal goal card
        Image pgImage = MediaManager.personalGoalToImage.get(state.getPersonalGoalCardId());
        personalGoal.setImage(pgImage);

        phaseDescription.setText("It's your turn");
        usernameLabel.setText("Hi, "+state.getUsername());


        // Displaying the board
        renderPickableBoard();

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

    public void renderPickableBoard() {
        boardCells.clear();
        gameBoard.getChildren().clear();
        imageToClickedProperty.clear();

        ClientState state = ClientManager.getInstance().getStateContainer().getCurrentState();
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
                                setLabelErrorMessage("You are trying to pick tiles in an incorrect way.");
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
    }



    private boolean checkCoordinateSelection(List<Coordinates> clicked, Coordinates coordsOfImage) {
        ObjectTypeEnum[][] board = ClientManager.getInstance().getStateContainer().getCurrentState().getBoard();
        System.out.println(clicked);
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

    public void renderName() {
        phaseDescription.setText("It's " + ClientManager.getInstance().getStateContainer().getCurrentState().getActivePlayer() + "'s turn");
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
        ArrayList<Coordinates> c = new ArrayList<>(clicked);
        clicked.clear();
        System.out.println(c);
        ClientManager.getInstance().getNetworkHandler().sendMessage(
                new PickFromBoardMessage(
                        name,
                        c
                )
        );

    }

}
