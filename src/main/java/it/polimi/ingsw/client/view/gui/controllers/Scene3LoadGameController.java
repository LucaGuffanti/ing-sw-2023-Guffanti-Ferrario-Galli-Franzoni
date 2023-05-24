package it.polimi.ingsw.client.view.gui.controllers;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.client.view.gui.Gui;
import it.polimi.ingsw.client.view.gui.Renderer;
import it.polimi.ingsw.network.messages.ChatMessage;
import it.polimi.ingsw.network.messages.FoundSavedGameResponseMessage;
import it.polimi.ingsw.network.messages.enums.ReloadGameChoice;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Scene3LoadGameController implements GameSceneController, Initializable {
    @FXML
    private MenuButton recipientMenu;
    @FXML
    private ListView messages;
    @FXML
    private TextField messageText;
    @FXML
    private Button sendButton;
    @FXML
    private Label labelErrorLoadGame;
    @FXML
    private Slider sliderVolume;
    @FXML
    private Button buttonYesLoadGame;
    @FXML
    private Button buttonNoLoadGame;
    @FXML
    private Label labelLoadGame;
    private ArrayList<MenuItem> recipients = new ArrayList<>();
    private String messageRecipient;

    @Override
    public void setSliderVolume(double volume) {
        sliderVolume.setValue(volume * 100);
        sliderVolume.valueProperty().addListener(observable -> Gui.instance.getMediaPlayer().setVolume(sliderVolume.getValue() / 100));
    }


    @Override
    public void setLabelErrorMessage(String message) {
        labelErrorLoadGame.setText(message);
    }

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

    @Override
    public void drawScene(Stage stage) {
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
    }
}
