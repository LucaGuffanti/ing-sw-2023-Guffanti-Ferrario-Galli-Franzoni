package it.polimi.ingsw.client.view.gui;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.client.controller.ClientPhasesEnum;
import it.polimi.ingsw.client.controller.stateController.StateContainer;
import it.polimi.ingsw.client.view.UserInterface;
import it.polimi.ingsw.network.ClientNetworkHandler;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.HashMap;

/**
 * This class contains main info of GUI
 * @author Marco Galli
 */
public class Gui extends Application implements UserInterface, PropertyChangeListener {
    private ClientNetworkHandler clientNetworkHandler;
    private StateContainer stateContainer;
    private HashMap<ClientPhasesEnum, Scene> phaseToSceneMap;
    private static Stage stage;
    private Scene scene;

    /*
    public Gui(StateContainer stateContainer, ClientNetworkHandler clientNetworkHandler) {
        super();
        this.stateContainer = stateContainer;
        this.clientNetworkHandler = clientNetworkHandler;
        phaseToControllerMap = new HashMap<>();
        initPhaseToController();
    }
    */

    public static Stage getStage() {
        return stage;
    }

    private void initPhaseToScene(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/scene1Login.fxml"));
            Parent pLogin = loader.load();
            Scene sLogin = new Scene(pLogin, scene.getWidth(), scene.getHeight());
            phaseToSceneMap.put(ClientPhasesEnum.LOGIN, sLogin);

            loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/scene2LobbyCreation.fxml"));
            Parent pLobbyCreation = loader.load();
            Scene sLobbyCreation = new Scene(pLobbyCreation, scene.getWidth(), scene.getHeight());
            phaseToSceneMap.put(ClientPhasesEnum.PICK_PLAYERS, sLobbyCreation);

            loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/scene2WaitingForLobby.fxml"));
            Parent pWaitingForLobby = loader.load();
            Scene sWaitingForLobby = new Scene(pWaitingForLobby, scene.getWidth(), scene.getHeight());
            phaseToSceneMap.put(ClientPhasesEnum.WAITING_FOR_LOBBY, sWaitingForLobby);

            loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/scene3Lobby.fxml"));
            Parent pLobby = loader.load();
            Scene sLobby = new Scene(pLobby, scene.getWidth(), scene.getHeight());
            phaseToSceneMap.put(ClientPhasesEnum.LOBBY, sLobby);

            loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/scene3LoadGame.fxml"));
            Parent pLoadGame = loader.load();
            Scene sLoadGame = new Scene(pLoadGame, scene.getWidth(), scene.getHeight());
            phaseToSceneMap.put(ClientPhasesEnum.LOBBY, sLoadGame);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void renderGui(Scene sceneToRender) throws IOException {
        getStage().setScene(sceneToRender);
    }

    public void renderCurrentScene() {
        getStage().setScene(phaseToSceneMap.get(stateContainer.getCurrentState().getCurrentPhase()));
    }

    @Override
    public void run() {
        Application.launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        ClientManager manager = ClientManager.getInstance();
        this.stateContainer = manager.getStateContainer();
        this.clientNetworkHandler = manager.getNetworkHandler();
        Gui.stage = stage;
        stateContainer.addPropertyChangeListener(this::propertyChange);
        try {
            phaseToSceneMap = new HashMap<>();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/scene0Boot.fxml"));
            Parent root = null;
            root = loader.load();
            Scene scene = new Scene(root);
            stage.setTitle("My Shelfie");
            stage.getIcons().add(new Image("file:src/main/resources/images/Publisher material/Icon 50x50px.png"));
            stage.setScene(scene);
            stage.setMaximized(true);
            stage.setFullScreen(false);
            this.scene = scene;
            stage.show();
            initPhaseToScene();
        } catch (Exception e) {
            e.printStackTrace();
        }
        renderCurrentScene();
    }

    @Override
    public void onGameAborted() {
        // esce un messaggio "partita finita"
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            // When currentPhase is changed, always render the default view for the new currentPhase
            case "currentPhase" -> {
                Scene newScene = phaseToSceneMap.get((ClientPhasesEnum) evt.getNewValue());
                // this is done to prevent the double printing of the list of players that would otherwise
                // be experienced by the player who creates the game
                if (!stateContainer.getCurrentState().getCurrentPhase().equals(ClientPhasesEnum.LOBBY)) {
                    try {
                        Platform.runLater(()-> {
                            try {
                                this.renderGui(newScene);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw new RuntimeException(e);
                    }
                }
            }
            //case "serverErrorMessage" -> labelErrorLogin.setText((String) evt.getNewValue());
            //case "serverLastMessage" -> Printer.boldsSubtitle((String) evt.getNewValue());
            case "orderedPlayersNames" -> {
                if (stateContainer.getCurrentState().getCurrentPhase().equals(ClientPhasesEnum.LOBBY)) {
                    Platform.runLater(()->
                            renderCurrentScene()
                    );
                }
            }/*
            case "lastChatMessage" -> {
                if (cliView instanceof ChatView) {
                    ChatView cli = (ChatView) cliView;
                    cli.updateRender(stateContainer.getCurrentState());
                }
            }*/
        }
    }
}
