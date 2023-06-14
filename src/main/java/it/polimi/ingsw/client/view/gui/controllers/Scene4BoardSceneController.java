package it.polimi.ingsw.client.view.gui.controllers;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.client.controller.stateController.ClientState;
import it.polimi.ingsw.client.controller.utils.PickChecker;
import it.polimi.ingsw.client.view.cli.Printer;
import it.polimi.ingsw.client.view.gui.Gui;
import it.polimi.ingsw.client.view.gui.MediaManager;
import it.polimi.ingsw.client.view.gui.Renderer;
import it.polimi.ingsw.network.messages.ChatMessage;
import it.polimi.ingsw.network.messages.PickFromBoardMessage;
import it.polimi.ingsw.server.model.cards.ObjectTypeEnum;
import it.polimi.ingsw.server.model.cells.Coordinates;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

public class Scene4BoardSceneController implements GameSceneController, Initializable {
    @FXML
    private MenuButton recipientMenu;
    @FXML
    private ListView messages;
    @FXML
    private TextField messageText;
    @FXML
    private Button sendButton;
    private ArrayList<MenuItem> recipients = new ArrayList<>();
    private String messageRecipient;
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
    private GridPane cg1Points;
    @FXML
    private GridPane cg2Points;
    @FXML
    private ImageView personalGoal;
    @FXML
    private ImageView commonGoal1;
    @FXML
    private ImageView commonGoal2;
    private List<ImageView> boardCells = new ArrayList<>();
    private List<Coordinates> clicked = new ArrayList<>();
    private HashMap<ImageView, BooleanProperty> imageToClickedProperty = new HashMap<>();
    private List<ObjectTypeEnum> lastPickedTiles = new ArrayList<>();

    public List<ObjectTypeEnum> getLastPickedTiles() {
        return lastPickedTiles;
    }

    @Override
    public void setSliderVolume(double volume) {
        sliderVolume.setValue(volume * 100);
        sliderVolume.valueProperty().addListener(observable -> Gui.instance.getMediaPlayer().setVolume(sliderVolume.getValue() / 100));
    }

    @Override
    public void drawScene(Stage stage) {
        setLabelErrorMessage("");
        ClientState state = ClientManager.getInstance().getStateContainer().getCurrentState();

        // Displaying personal goals and available points
        // Displaying the personal goal card
        Image pgImage = MediaManager.personalGoalToImage.get(state.getPersonalGoalCardId());
        personalGoal.setImage(pgImage);

        phaseDescription.setText("It's your turn");
        usernameLabel.setText("Hi, "+state.getUsername());


        // Displaying the board
        renderPickableBoard();

        // Displaying the shelves
        renderShelves();

        List<String> players = ClientManager.getInstance().getStateContainer().getCurrentState().getOrderedPlayersNames();

        recipients.clear();

        recipientMenu.getItems().clear();

        for (String player : players) {
            if (!player.equals(ClientManager.getInstance().getStateContainer().getCurrentState().getUsername())) {
                MenuItem playerLabelItem = new MenuItem(player);
                playerLabelItem.setOnAction((e)-> {
                    messageRecipient = player;
                    System.out.println("selected " + player);
                    recipientMenu.setText(player);
                });
                recipientMenu.getItems().add(playerLabelItem);
            }
        }

        MenuItem allPlayers = new MenuItem("all");
        recipients.add(allPlayers);
        recipientMenu.getItems().add(allPlayers);

        allPlayers.setOnAction(e->{
            messageRecipient = "all";
            System.out.println("selected all");
            recipientMenu.setText("all");
        });

        Renderer.renderMessages(messages, state.getUsername());
    }

    public void renderShelves() {
        Renderer.renderShelvesAndCards(shelvesBox,
                cg1Points,
                cg2Points,
                commonGoal1,
                commonGoal2);
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
                    imgView.setLayoutX(1);
                    imgView.setLayoutY(1);
                    imgView.setFitHeight(40);
                    imgView.setFitWidth(40);

                    paneContainingImage.setMaxWidth(42);
                    paneContainingImage.setMinWidth(42);
                    paneContainingImage.setPrefWidth(42);

                    paneContainingImage.setMaxHeight(42);
                    paneContainingImage.setMinHeight(42);
                    paneContainingImage.setPrefHeight(42);

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
                            if (clicked.size()<3) {
                                System.out.println("Valid selection");
                                clicked.add(coordsOfImage);
                                setLabelErrorMessage("");
                                imageViewBorderActive.set(true);
                            } else {
                                System.out.println("Invalid selection");
                                setLabelErrorMessage("You can't pick more than 3 tiles at a time");
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
        //resetSelection();
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
        int currentPlayerIndex = ClientManager.getInstance().getStateContainer().getCurrentState().getOrderedPlayersNames().indexOf(name);
        ObjectTypeEnum[][] activePlayerShelf = ClientManager.getInstance().getStateContainer().getCurrentState().getShelves().get(currentPlayerIndex);
        System.out.println("Submitting selection for "+ name);
        if (activePlayerShelf != null && PickChecker.shelfIsFull(activePlayerShelf, clicked.size())) {
            resetSelection();
            setLabelErrorMessage("The selection is invalid, you don't have enough space in your shelf");
            System.out.println("Invalid selection");
        } else if (PickChecker.checkAdjacencies(clicked)) {
            ArrayList<Coordinates> coordinates = new ArrayList<>(clicked);
            // Setting last picked tiles from coordinates
            ObjectTypeEnum[][] board = ClientManager.getInstance().getStateContainer().getCurrentState().getBoard();
            for (Coordinates c : coordinates) {
                lastPickedTiles.add(board[c.getY()][c.getX()]);
            }
            resetSelection();
            //clicked.clear();
            System.out.println(coordinates);
            ClientManager.getInstance().getNetworkHandler().sendMessage(
                    new PickFromBoardMessage(
                            name,
                            coordinates
                    )
            );
            System.out.println("correctly submitted");
        } else {
            resetSelection();
            setLabelErrorMessage("The selection is invalid");
            System.out.println("Invalid selection");
        }

    }

    @Override
    public void updateChat(ChatMessage chatMessage) {
        String username = ClientManager.getInstance().getStateContainer().getCurrentState().getUsername();
        if (!chatMessage.getSenderUsername().equals(username)) {
            Label messageText = new Label(Renderer.printChatMessage(chatMessage, username));
            messageText.setWrapText(true);
            messageText.setMaxWidth(300);
            messageText.setPrefWidth(300);
            messages.getItems().add(0, messageText);
            System.out.println("printing chat message from outside");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        sendButton.setOnMouseClicked(e->{
            sendMessage();
        });

        messageText.setOnKeyPressed(e->{
            if (e.getCode().equals(KeyCode.ENTER)) {
                sendMessage();
            }
        });
    }

    public void sendMessage() {
        List<String> players = ClientManager.getInstance().getStateContainer().getCurrentState().getOrderedPlayersNames();
        String messageBody = messageText.getText().trim();
        if(players.size() > 1 && messageRecipient != null && messageText.getText().trim().length() > 0) {
            String name = ClientManager.getInstance().getStateContainer().getCurrentState().getUsername();
            List<String> recipients;
            if (messageRecipient.equals("all")) {
                recipients = new ArrayList<>(players);
                recipients.remove(name);

            } else {
                recipients = new ArrayList<>();
                recipients.add(messageRecipient);
            }

            ChatMessage chatMessage = new ChatMessage(messageBody,
                    name,
                    LocalDateTime.now(),
                    recipients);

            ClientManager.getInstance().getNetworkHandler().sendMessage(
                    chatMessage
            );
            Label messageLabel = new Label(Renderer.printChatMessage(chatMessage, name));
            messageLabel.setWrapText(true);
            messageLabel.setMaxWidth(300);
            messageText.setPrefWidth(300);

            messageText.setText("");
            messages.getItems().add(0, messageLabel);
            System.out.println("printing chat message from inside");
        }
    }
}
