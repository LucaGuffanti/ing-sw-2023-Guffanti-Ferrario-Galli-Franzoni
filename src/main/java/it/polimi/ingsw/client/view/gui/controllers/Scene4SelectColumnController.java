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
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.*;

public class Scene4SelectColumnController implements GameSceneController, Initializable {
    @FXML
    private Label usernameLabel;
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
    private Slider sliderVolume;
    @FXML
    private Button b0, b1, b2, b3, b4;
    @FXML
    private Label phaseDescription;
    @FXML
    private Label textError;
    @FXML
    private VBox shelvesBox;
    @FXML
    private GridPane gameShelf;
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
    @FXML
    private GridPane selectedTiles;
    private List<ImageView> shelfCells = new ArrayList<>();

    @Override
    public void setSliderVolume(double volume) {
        sliderVolume.setValue(volume * 100);
        sliderVolume.valueProperty().addListener(observable -> Gui.instance.getMediaPlayer().setVolume(sliderVolume.getValue() / 100));
    }

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

        phaseDescription.setText("Select a column");
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
    }
    public void renderShelves() {
        Renderer.renderShelvesAndCards(shelvesBox,
                cg1Points,
                cg2Points,
                commonGoal1,
                commonGoal2);
    }

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

    @Override
    public void setLabelErrorMessage(String message) {
        textError.setText(message);
    }

    public void renderName(String name) {
        phaseDescription.setText("It's " + name + "'s turn");
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
