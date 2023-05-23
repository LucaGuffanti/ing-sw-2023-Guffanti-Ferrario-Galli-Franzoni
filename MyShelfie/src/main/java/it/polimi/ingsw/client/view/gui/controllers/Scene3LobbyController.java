package it.polimi.ingsw.client.view.gui.controllers;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.client.view.cli.Printer;
import it.polimi.ingsw.client.view.gui.Gui;
import it.polimi.ingsw.client.view.gui.Renderer;
import it.polimi.ingsw.network.messages.ChatMessage;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;

import javax.swing.text.LabelView;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Scene3LobbyController implements SceneController, Initializable {
    @FXML
    private Button sendButton;
    @FXML
    private MenuButton recipientMenu;
    @FXML
    private TextField messageText;
    @FXML
    private VBox messages;
    @FXML
    private ListView<Label> playerList;
    @FXML
    private Label labelErrorStartGame;
    @FXML
    private Slider sliderVolume;

    private ArrayList<MenuItem> recipients = new ArrayList<>();
    private String messageRecipient;

    @Override
    public void setLabelErrorMessage(String message) {
        labelErrorStartGame.setText(message);
    }

    @Override
    public void setSliderVolume(double volume) {
        sliderVolume.setValue(volume * 100);
        sliderVolume.valueProperty().addListener(observable -> Gui.instance.getMediaPlayer().setVolume(sliderVolume.getValue() / 100));
    }

    public void renderLoggedPlayers() {
        List<String> players = ClientManager.getInstance().getStateContainer().getCurrentState().getOrderedPlayersNames();

        playerList.getItems().clear();
        recipients.clear();
        for (String player : players) {
            Label playerLabel = new Label();

            playerLabel.setText(player);
            playerLabel.setStyle("-fx-font-family: 'Comic Sans MS'");
            playerLabel.setStyle("-fx-font-size: medium");
            playerLabel.setStyle("-fx-font-weight: bolder");

            playerList.getItems().add(playerLabel);
        }

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

    /**
     * This method is called when the "enter" button is pressed while inserting the message
     * or when the "send" button is pressed.
     */
    public void sendMessage() {
        List<String> players = ClientManager.getInstance().getStateContainer().getCurrentState().getOrderedPlayersNames();
        if(players.size() > 1) {
            String messageBody = messageText.getText();
            String name = ClientManager.getInstance().getStateContainer().getCurrentState().getUsername();
            List<String> recipients;
            if (messageRecipient.equals("all")) {
                recipients = new ArrayList<>(players);
                recipients.remove(name);

            } else {
                recipients = new ArrayList<>();
                recipients.add(messageRecipient);
            }

            ChatMessage c = new ChatMessage(messageBody,
                    name,
                    LocalDateTime.now(),
                    recipients);

            ClientManager.getInstance().getNetworkHandler().sendMessage(
                    c
            );
            Label messageLabel = new Label(Renderer.printChatMessage(c, name));
            messageLabel.setWrapText(true);
            messageText.setText("");
            messages.getChildren().add(0, messageLabel);
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
}
