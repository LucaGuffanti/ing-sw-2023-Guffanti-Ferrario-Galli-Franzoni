package it.polimi.ingsw.client.view.gui.controllers;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.client.controller.ClientPhasesEnum;
import it.polimi.ingsw.client.controller.stateController.ClientState;
import it.polimi.ingsw.client.view.cli.Printer;
import it.polimi.ingsw.client.view.gui.Gui;
import it.polimi.ingsw.client.view.gui.MediaManager;
import it.polimi.ingsw.client.view.gui.Renderer;
import it.polimi.ingsw.network.messages.ChatMessage;
import it.polimi.ingsw.network.messages.SelectColumnMessage;
import it.polimi.ingsw.server.model.cards.ObjectTypeEnum;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Controller for select column scene
 * @author Marco Galli
 */
public class Scene4SelectColumnController implements GameSceneController, Initializable {
    /**
     * The label where the message "Hi, username" is displayed
     */
    @FXML
    private Label usernameLabel;

    /**
     * The button for player selection in chat
     */
    @FXML
    private MenuButton recipientMenu;

    /**
     * The list of messages in chat
     */
    @FXML
    private ListView messages;

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

    /**
     * The volume slider
     */
    @FXML
    private Slider sliderVolume;

    /**
     * The label for error messages
     */
    @FXML
    private Label textError;

    /**
     * The container which contains players' shelves
     */
    @FXML
    private VBox shelvesBox;

    /**
     * The container of the current player's shelf
     */
    @FXML
    private GridPane gameShelf;

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
     * The container of tiles that have been picked
     */
    @FXML
    private GridPane selectedTiles;

    /**
     * List of shelf cells which contains images tiles
     */
    private List<ImageView> shelfCells = new ArrayList<>();

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
     * This method draws the shelf, the last picked tiles, the personal goal card, the shelves,
     * the common goal cards with their points, the username label and previous messages
     * @param stage the stage of gui
     */
    @Override
    public void drawScene(Stage stage) {
        setLabelErrorMessage("");
        // Removing previous picked tiles if they are present
        selectedTiles.getChildren().removeIf(node ->
                GridPane.getColumnIndex(node) == 0 && GridPane.getRowIndex(node) == 0
                        && node instanceof ImageView);
        selectedTiles.getChildren().removeIf(node ->
                GridPane.getColumnIndex(node) == 0 && GridPane.getRowIndex(node) == 1
                        && node instanceof ImageView);
        selectedTiles.getChildren().removeIf(node ->
                GridPane.getColumnIndex(node) == 0 && GridPane.getRowIndex(node) == 2
                        && node instanceof ImageView);
        // Drawing picked tiles
        List<ObjectTypeEnum> pickedTiles = ((Scene4BoardSceneController)Gui.instance.getPhaseToControllerMap().get(ClientPhasesEnum.PICK_FORM_BOARD)).getLastPickedTiles();
        Collections.reverse(pickedTiles);
        int x = 0;
        for (ObjectTypeEnum tile : pickedTiles) {
            ImageView imgView = new ImageView(MediaManager.tileToImage.get(tile));
            selectedTiles.add(imgView, 0, x);
            x++;
        }

        ClientState state = ClientManager.getInstance().getStateContainer().getCurrentState();
        // Displaying personal goals and available points
        // Displaying the personal goal card
        Image pgImage = MediaManager.personalGoalToImage.get(state.getPersonalGoalCardId());
        personalGoal.setImage(pgImage);

        usernameLabel.setText("Hi, "+state.getUsername());

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
                    imgView.setFitHeight(49);
                    imgView.setFitWidth(49);
                    if (i == 0) {
                        imgView.setFitHeight(51);
                    } else if (i == 1) {
                        imgView.setFitHeight(51);
                    } else if (i == 2) {
                        imgView.setFitHeight(50);
                    }
                    if (j == 0) {
                        imgView.setFitWidth(50);
                    } else if (j == 1) {
                        imgView.setFitWidth(50);
                    } else if (j == 3) {
                        imgView.setFitWidth(50);
                    } else if (j == 4) {
                        imgView.setFitWidth(52);
                    }
                    shelfCells.add(imgView);

                    gameShelf.add(imgView ,j, i);
                } else {
                    shelfCells.add(new ImageView());
                }

            }
        }

        shelvesBox.getChildren().clear();
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
        messageText.clear();
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
     * This method sends a select column message to the network handler with player username and the selected column
     * @param actionEvent the action event that is triggered by clicking on a button which refers to a column
     */
    public void submitSelection(ActionEvent actionEvent) {
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
        ((Scene4BoardSceneController) Gui.instance.getPhaseToControllerMap().get(ClientPhasesEnum.PICK_FORM_BOARD)).getLastPickedTiles().clear();
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
     * This method receives chat messages
     * @param chatMessage the chat message
     */
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
