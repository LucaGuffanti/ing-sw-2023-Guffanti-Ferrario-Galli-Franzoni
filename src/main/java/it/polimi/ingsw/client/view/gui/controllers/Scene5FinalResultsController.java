package it.polimi.ingsw.client.view.gui.controllers;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.client.controller.stateController.ClientState;
import it.polimi.ingsw.client.view.gui.Gui;
import it.polimi.ingsw.client.view.gui.Renderer;
import it.polimi.ingsw.network.messages.ChatMessage;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
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

public class Scene5FinalResultsController implements GameSceneController, Initializable {
    @FXML
    private Label winner;
    @FXML
    private GridPane playerList;
    @FXML
    private Button buttonClose;
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
    @FXML
    private BorderPane borderPaneFinalResults;
    @FXML
    private Slider sliderVolume;
    @FXML
    private Label labelErrorFinalResults;

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

    @Override
    public void setSliderVolume(double volume) {
        sliderVolume.setValue(volume * 100);
        sliderVolume.valueProperty().addListener(observable -> Gui.instance.getMediaPlayer().setVolume(sliderVolume.getValue() / 100));
    }

    private void makeFadeIn() {
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(2000));
        fadeTransition.setNode(borderPaneFinalResults);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();
    }
    @Override
    public void setLabelErrorMessage(String message) {
        labelErrorFinalResults.setText(message);
    }

    public void close(ActionEvent actionEvent) {
        Stage stage = (Stage) buttonClose.getScene().getWindow();
        stage.close();
        System.exit(0);
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
}
