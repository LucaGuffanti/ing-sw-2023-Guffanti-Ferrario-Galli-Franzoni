package it.polimi.ingsw.client.view.gui.controllers;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.client.controller.stateController.ClientState;
import it.polimi.ingsw.client.view.gui.Gui;
import it.polimi.ingsw.client.view.gui.Renderer;
import it.polimi.ingsw.network.messages.ChatMessage;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Controller for final results scene
 * @author Marco Galli
 */
public class Scene5FinalResultsController implements GameSceneController, Initializable {
    /**
     * The label of the winner name player
     */
    @FXML
    private Label winner;

    /**
     * The list of players
     */
    @FXML
    private GridPane playerList;

    /**
     * The close button that closes the window
     */
    @FXML
    private Button buttonClose;

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
     * The container of final results which contains players' names and their points
     */
    @FXML
    private BorderPane borderPaneFinalResults;

    /**
     * The volume slider
     */
    @FXML
    private Slider sliderVolume;

    /**
     * The label for error messages
     */
    @FXML
    private Label labelErrorFinalResults;

    /**
     * This method initializes the scene, in particular the send button and the ENTER key for sending chat messages.
     * Also, a fade-in effect is displayed
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        borderPaneFinalResults.setOpacity(0);
        makeFadeIn();

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
     * This method allows the volume slider to be set
     * @param volume the media player volume
     */
    @Override
    public void setSliderVolume(double volume) {
        sliderVolume.setValue(volume * 100);
        sliderVolume.valueProperty().addListener(observable -> Gui.instance.getMediaPlayer().setVolume(sliderVolume.getValue() / 100));
    }

    /**
     * Fade-in effect for initialization
     */
    private void makeFadeIn() {
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(2000));
        fadeTransition.setNode(borderPaneFinalResults);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();
    }

    /**
     * This method sets the error message in the label for error messages
     * @param message the error message
     */
    @Override
    public void setLabelErrorMessage(String message) {
        labelErrorFinalResults.setText(message);
    }

    /**
     * The method that is invoked when the close button is pressed
     */
    public void close() {
        Stage stage = (Stage) buttonClose.getScene().getWindow();
        stage.close();
        System.exit(0);
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
            Label messageLabel = new Label(Renderer.printChatMessage(chatMessage, name));
            messageLabel.setWrapText(true);
            messageLabel.setMaxWidth(300);
            messageText.setPrefWidth(300);

            messageText.setText("");
            messages.getItems().add(0, messageLabel);
            System.out.println("printing chat message from inside");
        }
    }

    /**
     * This method draws the scene, in particular the winner name, the list of players with their points
     * and previous chat messages
     * @param stage the stage of gui
     */
    @Override
    public void drawScene(Stage stage) {
        String winnerName = ClientManager.getInstance().getStateContainer().getCurrentState().getWinnerUserName();
        winner.setText("WINNER: " + winnerName);
        // sorting players by their final score
        HashMap<String, Integer> playersResults = ClientManager.getInstance().getStateContainer().getCurrentState().getNameToPointMap();
        List<Map.Entry<String, Integer>> list = new ArrayList<>(playersResults.entrySet());
        list.sort((o1, o2) -> o2.getValue().compareTo(o1.getValue()));
        LinkedHashMap<String, Integer> playersResultsOrdered = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> entry : list) {
            playersResultsOrdered.put(entry.getKey(), entry.getValue());
        }
        // displaying players with their score
        int x = 0;
        for (Map.Entry<String, Integer> entry : playersResultsOrdered.entrySet()) {
            String player = entry.getKey();
            Integer points = entry.getValue();
            Label label = new Label(player + " scored: " + points);
            Font font = Font.font("Times New Roman", 20);
            String style = "-fx-font-style: italic; -fx-font-weight: bold;";
            Color color = Color.web("#ffedaa");
            String colorStyle = String.format("-fx-text-fill: #%02x%02x%02x;", (int)(color.getRed() * 255), (int)(color.getGreen() * 255), (int)(color.getBlue() * 255));
            Insets padding = new Insets(10);
            label.setPadding(padding);
            label.setStyle(style + colorStyle);
            label.setFont(font);
            playerList.add(label, 0, x);
            x++;
        }

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

        Renderer.renderMessages(messages, state.getUsername());
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
