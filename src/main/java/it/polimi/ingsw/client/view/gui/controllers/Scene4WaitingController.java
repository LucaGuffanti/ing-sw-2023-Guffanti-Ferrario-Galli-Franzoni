package it.polimi.ingsw.client.view.gui.controllers;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.client.controller.stateController.ClientState;
import it.polimi.ingsw.client.view.gui.Gui;
import it.polimi.ingsw.client.view.gui.MediaManager;
import it.polimi.ingsw.client.view.gui.Renderer;
import it.polimi.ingsw.network.messages.ChatMessage;
import it.polimi.ingsw.server.model.cards.ObjectTypeEnum;
import it.polimi.ingsw.server.model.cards.goalCards.SimplifiedCommonGoalCard;
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

public class Scene4WaitingController implements GameSceneController, Initializable {
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

    @Override
    public void setLabelErrorMessage(String message) {
        textError.setText(message);
    }

    public void renderName() {
        phaseDescription.setText("It's " + ClientManager.getInstance().getStateContainer().getCurrentState().getActivePlayer() + "'s turn");
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
