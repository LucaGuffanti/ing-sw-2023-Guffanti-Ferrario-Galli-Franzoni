package it.polimi.ingsw.client.view.gui.controllers;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.client.controller.stateController.ClientState;
import it.polimi.ingsw.client.view.gui.Gui;
import it.polimi.ingsw.client.view.gui.Renderer;
import it.polimi.ingsw.network.messages.ChatMessage;
import it.polimi.ingsw.network.messages.FoundSavedGameResponseMessage;
import it.polimi.ingsw.network.messages.enums.ReloadGameChoice;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Controller for load game scene
 * @author Marco Galli
 */
public class Scene3LoadGameController implements GameSceneController, Initializable {
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
     * The label for error messages
     */
    @FXML
    private Label labelErrorLoadGame;

    /**
     * The volume slider
     */
    @FXML
    private Slider sliderVolume;

    /**
     * The button for loading the saved game
     */
    @FXML
    private Button buttonYesLoadGame;

    /**
     * The button for not loading the saved game
     */
    @FXML
    private Button buttonNoLoadGame;

    /**
     * The label containing info of this scene
     */
    @FXML
    private Label labelLoadGame;

    /**
     * List of players that can be selected in chat
     */
    private ArrayList<MenuItem> recipients = new ArrayList<>();

    /**
     * Generic player that can be selected in chat
     */
    private String messageRecipient;

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
     * This method sets the error message in the label for error messages
     * @param message the error message
     */
    @Override
    public void setLabelErrorMessage(String message) {
        labelErrorLoadGame.setText(message);
    }

    /**
     * This method sends a found saved game response message to the network handler with the player's choice,
     * if the reload is accepted
     */
    public void yesLoad() {
        ClientManager.getInstance().getNetworkHandler().sendMessage(
                new FoundSavedGameResponseMessage(
                        ClientManager.getInstance().getStateContainer().getCurrentState().getUsername(),
                        ReloadGameChoice.ACCEPT_RELOAD
                )
        );
        buttonYesLoadGame.setDisable(true);
        buttonNoLoadGame.setDisable(true);
        labelLoadGame.setText("Loading...");
    }

    /**
     * This method sends a found saved game response message to the network handler with the player's choice,
     * if the reload is refused
     */
    public void noLoad() {
        ClientManager.getInstance().getNetworkHandler().sendMessage(
                new FoundSavedGameResponseMessage(
                        ClientManager.getInstance().getStateContainer().getCurrentState().getUsername(),
                        ReloadGameChoice.DECLINE_RELOAD
                )
        );
        buttonYesLoadGame.setDisable(true);
        buttonNoLoadGame.setDisable(true);
        labelLoadGame.setText("Loading...");
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
     * This method sends a message in the chat when the "enter" button is pressed while inserting the message
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
     * This method draws the scene, in particular previous chat messages
     * @param stage the stage of gui
     */
    @Override
    public void drawScene(Stage stage) {
        List<String> players = ClientManager.getInstance().getStateContainer().getCurrentState().getOrderedPlayersNames();
        ClientState state = ClientManager.getInstance().getStateContainer().getCurrentState();

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
