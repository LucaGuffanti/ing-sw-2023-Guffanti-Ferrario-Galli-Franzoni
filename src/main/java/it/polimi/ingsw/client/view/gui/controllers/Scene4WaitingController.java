package it.polimi.ingsw.client.view.gui.controllers;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.client.controller.stateController.ClientState;
import it.polimi.ingsw.client.view.gui.Gui;
import it.polimi.ingsw.client.view.gui.MediaManager;
import it.polimi.ingsw.client.view.gui.Renderer;
import it.polimi.ingsw.network.messages.ChatMessage;
import it.polimi.ingsw.server.model.cards.ObjectTypeEnum;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Controller for waiting scene
 * @author Luca Guffanti, Marco Galli
 */
public class Scene4WaitingController implements GameSceneController, Initializable {
    /**
     * The button for player selection in chat
     */
    @FXML
    private MenuButton recipientMenu;

    /**
     * The list of messages in chat
     */
    @FXML
    private VBox messages;

    /**
     * The scroll pane of the chat
     */
    @FXML
    private ScrollPane scrollChat;

    /**
     * The text field in chat where a player writes a message
     */
    @FXML
    private TextField messageText;

    /**
     * The button which sends the message in chat
     */
    @FXML
    private Button sendButton;

    /**
     * List of players that can be selected in chat
     */
    private ArrayList<MenuItem> recipients = new ArrayList<>();

    /**
     * Generic player that can be selected in chat
     */
    private String messageRecipient;
    // Player's username displayed on top

    /**
     * The label where the message "Hi, username" is displayed
     */
    @FXML
    private Label usernameLabel;

    /**
     * A small description of this phase
     */
    @FXML
    private Label phaseDescription;

    /**
     * The label for error messages
     */
    @FXML
    private Label textError;

    /**
     * The volume slider
     */
    @FXML
    private Slider sliderVolume;

    /**
     * The container which contains players' shelves
     */
    @FXML
    private VBox shelvesBox;

    /**
     * The board of the game
     */
    @FXML
    private GridPane gameBoard;

    /**
     * The points of the first common goal
     */
    @FXML
    private GridPane cg1Points;

    /**
     * The points of the second common goal
     */
    @FXML
    private GridPane cg2Points;

    /**
     * The image of the personal goal card
     */
    @FXML
    private ImageView personalGoal;

    /**
     * The image of the first common goal card
     */
    @FXML
    private ImageView commonGoal1;

    /**
     * The image of the second common goal card
     */
    @FXML
    private ImageView commonGoal2;

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
     * This method draws the board, the personal goal card, the shelves,
     * the common goal cards with their points, the username label,
     * the phase description and previous messages
     * @param stage the stage of gui
     */
    @Override
    public void drawScene(Stage stage) {
        ClientState state = ClientManager.getInstance().getStateContainer().getCurrentState();

        // Displaying personal goals and available points


        // Displaying the personal goal card
        Image pgImage = MediaManager.personalGoalToImage.get(state.getPersonalGoalCardId());
        personalGoal.setImage(pgImage);

        phaseDescription.setText("It's "+ state.getActivePlayer()+"'s turn");
        usernameLabel.setText("Hi, "+state.getUsername());

        // Displaying the board
        renderBoard();

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

        Renderer.renderMessages(messages, state.getUsername(), scrollChat);
    }

    /**
     * This method renders shelves and common goal cards with their points
     */
    public void renderShelves() {
        Renderer.renderShelvesAndCards(shelvesBox,
                cg1Points,
                cg2Points,
                commonGoal1,
                commonGoal2);
    }

    /**
     * This method renders the game board
     */
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
                    imgView.setLayoutX(1);
                    imgView.setLayoutY(1);

                    imgView.setFitWidth(40);
                    imgView.setFitHeight(40);
                    paneContainingImage.setMaxWidth(42);
                    paneContainingImage.setMinWidth(42);
                    paneContainingImage.setPrefWidth(42);

                    paneContainingImage.setMaxHeight(42);
                    paneContainingImage.setMinHeight(42);
                    paneContainingImage.setPrefHeight(42);

                    gameBoard.add(paneContainingImage, j, i);
                }
            }
        }
    }

    /**
     * This method sets the error message in the label for error messages
     * @param message the error message
     */
    @Override
    public void setLabelErrorMessage(String message) {
        textError.setText(message);
    }

    /**
     * This method renders the current player's name
     */
    public void renderName() {
        phaseDescription.setText("It's " + ClientManager.getInstance().getStateContainer().getCurrentState().getActivePlayer() + "'s turn");
    }

    /**
     * This method receives chat messages
     * @param chatMessage the chat message
     */
    @Override
    public void updateChat(ChatMessage chatMessage) {
        String username = ClientManager.getInstance().getStateContainer().getCurrentState().getUsername();
        if (!chatMessage.getSenderUsername().equals(username)) {
            Renderer.renderNewMessage(chatMessage, username, messages, scrollChat);
            System.out.println("printing chat message from outside");
        }
    }

    /**
     * This method initializes the scene, in particular the send button and the ENTER key for sending chat messages
     */
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

    /**
     * This method is called when the "enter" button is pressed while inserting the message
     * or when the "send" button is pressed.
     */
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
            Renderer.renderNewMessage(chatMessage, name, messages, scrollChat);
            messageText.clear();
            System.out.println("printing chat message from inside");
        }
    }

    /**
     * This method gets the message that was being typed before the change of the scene
     * @return the message
     */
    @Override
    public String getTypedMessage() {
        return messageText.getText();
    }

    /**
     * This method returns the username of the player that a player was chatting with
     * @return the player's username
     */
    @Override
    public String getChatPlayer() {
        return recipientMenu.getText();
    }

    /**
     * This method sets the message that was being typed and the player
     * who a player was chatting with before the change of the scene
     */
    @Override
    public void setTypedMessagePlayer(String message, String player) {
        if (message.equals("")) {
            messageText.clear();
        } else {
            messageText.setText(message);
        }
        recipientMenu.setText(player);
        int i = 0;
        boolean found = false;
        for (MenuItem p : recipientMenu.getItems()) {
            if (p.getText().equals(player)) {
                found = true;
                break;
            }
            i++;
        }
        if (found) {
            recipientMenu.getItems().get(i).fire();
        }
    }
}
