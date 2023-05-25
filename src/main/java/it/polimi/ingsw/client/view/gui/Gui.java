package it.polimi.ingsw.client.view.gui;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.client.controller.ClientPhasesEnum;
import it.polimi.ingsw.client.controller.stateController.ClientState;
import it.polimi.ingsw.client.controller.stateController.StateContainer;
import it.polimi.ingsw.client.view.UserInterface;
import it.polimi.ingsw.client.view.gui.controllers.*;
import it.polimi.ingsw.network.ClientNetworkHandler;

import it.polimi.ingsw.server.controller.GameController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

/**
 * This class contains main info of GUI
 * @author Marco Galli
 */
public class Gui extends Application implements UserInterface, PropertyChangeListener {
    public static Gui instance;
    private ClientNetworkHandler clientNetworkHandler;
    private StateContainer stateContainer;
    private HashMap<ClientPhasesEnum, Scene> phaseToSceneMap;
    private HashMap<ClientPhasesEnum, SceneController> phaseToControllerMap;
    private HashMap<ClientPhasesEnum, MediaPlayer> phaseToMusicMap;
    private static Stage stage;
    private Scene scene;
    private MediaPlayer mediaPlayer;
    private Scene sGameAborted;

    public HashMap<ClientPhasesEnum, SceneController> getPhaseToControllerMap() {
        return phaseToControllerMap;
    }

    public static Stage getStage() {
        return stage;
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public void setMediaPlayer(MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
    }

    private void initGui(){
        try {
            // LOAD SCENES AND CONTROLLERS
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/scene1Login.fxml"));
            Parent pLogin = loader.load();
            Scene sLogin = new Scene(pLogin, scene.getWidth(), scene.getHeight());
            phaseToSceneMap.put(ClientPhasesEnum.LOGIN, sLogin);
            phaseToControllerMap.put(ClientPhasesEnum.LOGIN, loader.getController());

            loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/scene2LobbyCreation.fxml"));
            Parent pLobbyCreation = loader.load();
            Scene sLobbyCreation = new Scene(pLobbyCreation, scene.getWidth(), scene.getHeight());
            phaseToSceneMap.put(ClientPhasesEnum.PICK_PLAYERS, sLobbyCreation);
            phaseToControllerMap.put(ClientPhasesEnum.PICK_PLAYERS, loader.getController());

            loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/scene2WaitingForLobby.fxml"));
            Parent pWaitingForLobby = loader.load();
            Scene sWaitingForLobby = new Scene(pWaitingForLobby, scene.getWidth(), scene.getHeight());
            phaseToSceneMap.put(ClientPhasesEnum.WAITING_FOR_LOBBY, sWaitingForLobby);
            phaseToControllerMap.put(ClientPhasesEnum.WAITING_FOR_LOBBY, loader.getController());

            loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/scene3LoadGame.fxml"));
            Parent pLoadGame = loader.load();
            Scene sLoadGame = new Scene(pLoadGame, scene.getWidth(), scene.getHeight());
            phaseToSceneMap.put(ClientPhasesEnum.DECIDING_FOR_RELOAD, sLoadGame);
            phaseToControllerMap.put(ClientPhasesEnum.DECIDING_FOR_RELOAD, loader.getController());

            loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/scene3Lobby.fxml"));
            Parent pLobby = loader.load();
            Scene sLobby = new Scene(pLobby, scene.getWidth(), scene.getHeight());
            phaseToSceneMap.put(ClientPhasesEnum.LOBBY, sLobby);
            phaseToControllerMap.put(ClientPhasesEnum.LOBBY, loader.getController());

            // GAME CYCLE SCENES:
            //  PICK FROM BOARD
            //  SELECT COLUMN
            //  WAIT FOR TURN

            loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/scene4BoardScene.fxml"));
            Parent pPickFromBoard = loader.load();
            Scene sPickFromBoard = new Scene(pPickFromBoard, scene.getWidth(), scene.getHeight());
            phaseToSceneMap.put(ClientPhasesEnum.PICK_FORM_BOARD, sPickFromBoard);
            phaseToControllerMap.put(ClientPhasesEnum.PICK_FORM_BOARD, loader.getController());

            loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/scene4SelectColumn.fxml"));
            Parent pSelectColumn = loader.load();
            Scene sSelectColumn = new Scene(pSelectColumn, scene.getWidth(), scene.getHeight());
            phaseToSceneMap.put(ClientPhasesEnum.SELECT_COLUMN, sSelectColumn);
            phaseToControllerMap.put(ClientPhasesEnum.SELECT_COLUMN, loader.getController());

            loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/scene4Waiting.fxml"));
            Parent pWaitForTurn = loader.load();
            Scene sWaitForTurn = new Scene(pWaitForTurn, scene.getWidth(), scene.getHeight());
            phaseToSceneMap.put(ClientPhasesEnum.WAITING_FOR_TURN, sWaitForTurn);
            phaseToControllerMap.put(ClientPhasesEnum.WAITING_FOR_TURN, loader.getController());

            loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/scene5FinalResults.fxml"));
            Parent pFinalResults = loader.load();
            Scene sFinalResults = new Scene(pFinalResults, scene.getWidth(), scene.getHeight());
            phaseToSceneMap.put(ClientPhasesEnum.FINAL_RESULTS_SHOW, sFinalResults);
            phaseToControllerMap.put(ClientPhasesEnum.FINAL_RESULTS_SHOW, loader.getController());

            // GAME ABORTED SCENE
            loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/sceneGameAborted.fxml"));
            Parent pGameAborted = loader.load();
            sGameAborted = new Scene(pGameAborted, scene.getWidth(), scene.getHeight());
            phaseToControllerMap.put(ClientPhasesEnum.ABORTED_GAME, loader.getController());

            // LOAD SOUNDS
            Media sound = new Media(new File("src/main/resources/audio/LocalForecast-Elevator.mp3").toURI().toString());
            MediaPlayer mPreGame = new MediaPlayer(sound);
            mPreGame.setOnEndOfMedia(() -> mPreGame.seek(Duration.ZERO));
            phaseToMusicMap.put(ClientPhasesEnum.LOGIN, mPreGame);

            sound = new Media(new File("src/main/resources/audio/KevinMacLeod-CasaBossaNova.mp3").toURI().toString());
            MediaPlayer mGame = new MediaPlayer(sound);
            mGame.setOnEndOfMedia(() -> mGame.seek(Duration.ZERO));
            phaseToMusicMap.put(ClientPhasesEnum.PICK_FORM_BOARD, mGame);
            phaseToMusicMap.put(ClientPhasesEnum.WAITING_FOR_TURN, mGame);

            sound = new Media(new File("src/main/resources/audio/BrazilianJazz.mp3").toURI().toString());
            MediaPlayer mFinalResults = new MediaPlayer(sound);
            mFinalResults.setOnEndOfMedia(() -> mFinalResults.seek(Duration.ZERO));
            phaseToMusicMap.put(ClientPhasesEnum.FINAL_RESULTS_SHOW, mFinalResults);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void renderGui(Scene sceneToRender, MediaPlayer m) throws IOException {
        ClientPhasesEnum phase = ClientManager.getInstance().getStateContainer().getCurrentState().getCurrentPhase();
        if (phase.equals(ClientPhasesEnum.PICK_FORM_BOARD) ||
                phase.equals(ClientPhasesEnum.WAITING_FOR_TURN) ||
                phase.equals(ClientPhasesEnum.SELECT_COLUMN) ||
                phase.equals(ClientPhasesEnum.DECIDING_FOR_RELOAD)) {
            System.out.println("Drawing scene");
            GameSceneController controller = (GameSceneController) phaseToControllerMap.get(phase);
            System.out.println(controller);
            controller.drawScene(stage);

        }

        getStage().setScene(sceneToRender);
        if (m != null && (getMediaPlayer() == null || !Objects.equals(m.getMedia().getSource(), getMediaPlayer().getMedia().getSource()))) {
            if (getMediaPlayer() != null) {
                getMediaPlayer().stop();
                m.setVolume(getMediaPlayer().getVolume());
            }
            phaseToControllerMap.get(stateContainer.getCurrentState().getCurrentPhase()).setSliderVolume(m.getVolume());
            setMediaPlayer(m);
            m.play();
        } else if (m == null && getMediaPlayer() != null && phaseToControllerMap.get(stateContainer.getCurrentState().getCurrentPhase()) != null) {
            phaseToControllerMap.get(stateContainer.getCurrentState().getCurrentPhase()).setSliderVolume(getMediaPlayer().getVolume());
        } else if (m != null && getMediaPlayer() != null && Objects.equals(m.getMedia().getSource(), getMediaPlayer().getMedia().getSource())) {
            phaseToControllerMap.get(stateContainer.getCurrentState().getCurrentPhase()).setSliderVolume(getMediaPlayer().getVolume());
        }
    }

    public void renderCurrentScene() throws IOException {
        ClientPhasesEnum phase = ClientManager.getInstance().getStateContainer().getCurrentState().getCurrentPhase();
        if (phase.equals(ClientPhasesEnum.PICK_FORM_BOARD) ||
                phase.equals(ClientPhasesEnum.WAITING_FOR_TURN) ||
                phase.equals(ClientPhasesEnum.SELECT_COLUMN) ||
                phase.equals(ClientPhasesEnum.DECIDING_FOR_RELOAD)) {
            System.out.println("Drawing scene");
            GameSceneController controller = (GameSceneController) phaseToControllerMap.get(phase);
            System.out.println(controller);
            controller.drawScene(stage);

        }

        getStage().setScene(phaseToSceneMap.get(stateContainer.getCurrentState().getCurrentPhase()));
        MediaPlayer m = phaseToMusicMap.get((stateContainer.getCurrentState().getCurrentPhase()));
        if (m != null && (getMediaPlayer() == null || !Objects.equals(m.getMedia().getSource(), getMediaPlayer().getMedia().getSource()))) {
            if (getMediaPlayer() != null) {
                getMediaPlayer().stop();
                m.setVolume(getMediaPlayer().getVolume());
            }
            phaseToControllerMap.get(stateContainer.getCurrentState().getCurrentPhase()).setSliderVolume(m.getVolume());
            setMediaPlayer(m);
            m.play();
        } else if (m == null && getMediaPlayer() != null && phaseToControllerMap.get(stateContainer.getCurrentState().getCurrentPhase()) != null) {
            phaseToControllerMap.get(stateContainer.getCurrentState().getCurrentPhase()).setSliderVolume(getMediaPlayer().getVolume());
        }
    }

    @Override
    public void run() {
        Application.launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        instance = this;
        ClientManager manager = ClientManager.getInstance();
        this.stateContainer = manager.getStateContainer();
        this.clientNetworkHandler = manager.getNetworkHandler();
        Gui.stage = stage;
        stateContainer.addPropertyChangeListener(this);
        try {
            phaseToSceneMap = new HashMap<>();
            phaseToControllerMap = new HashMap<>();
            phaseToMusicMap = new HashMap<>();
            setMediaPlayer(null);
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/scene0Boot.fxml"));
            Parent root = null;
            root = loader.load();
            Scene scene = new Scene(root);
            stage.setTitle("My Shelfie");
            stage.getIcons().add(new Image("file:src/main/resources/images/Publisher material/Icon 50x50px.png"));
            stage.setScene(scene);
            stage.setOnCloseRequest(e -> {
                System.exit(0);
            });
            stage.show();
            stage.setFullScreen(true);
            stage.setFullScreen(false);
            stage.setMaximized(true);
            this.scene = scene;
            MediaManager.loadGraphicResources();
            initGui();
        } catch (Exception e) {
            e.printStackTrace();
        }
        renderCurrentScene();
    }

    @Override
    public void onGameAborted() {
        try {
            Platform.runLater(()-> {
                getStage().setScene(sGameAborted);
                if (getMediaPlayer() != null) {
                    phaseToControllerMap.get(ClientPhasesEnum.ABORTED_GAME).setSliderVolume(getMediaPlayer().getVolume());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        ClientState currentState = ClientManager.getInstance().getStateContainer().getCurrentState();
        ClientPhasesEnum phase = currentState.getCurrentPhase();
        switch (evt.getPropertyName()) {
            // When currentPhase is changed, always render the default view for the new currentPhase
            case "currentPhase" -> {
                Scene newScene = phaseToSceneMap.get((ClientPhasesEnum) evt.getNewValue());
                MediaPlayer m = phaseToMusicMap.get((ClientPhasesEnum) evt.getNewValue());

                try {
                    Platform.runLater(()-> {
                        try {
                            this.renderGui(newScene, m);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }


            }
            case "serverErrorMessage" ->
                Platform.runLater(()->
                    phaseToControllerMap.get(
                    currentState.getCurrentPhase()
                    ).setLabelErrorMessage((String) evt.getNewValue()));
            //case "serverLastMessage" -> Printer.boldsSubtitle((String) evt.getNewValue());
            case "orderedPlayersNames" -> {
                if (phase.equals(ClientPhasesEnum.LOBBY)) {
                    Platform.runLater(()->
                            {
                                Scene3LobbyController c= (Scene3LobbyController) phaseToControllerMap.get(ClientPhasesEnum.LOBBY);
                                c.renderLoggedPlayers();
                            }
                    );
                }
            }
            case "activePlayer" -> {
                if (phase.equals(ClientPhasesEnum.WAITING_FOR_TURN)) {
                    Scene4WaitingController g = (Scene4WaitingController) phaseToControllerMap.get(ClientPhasesEnum.WAITING_FOR_TURN);
                    Platform.runLater(g::renderName);
                }
                if (phase.equals(ClientPhasesEnum.PICK_FORM_BOARD)) {
                    Scene4BoardSceneController g = (Scene4BoardSceneController) phaseToControllerMap.get(ClientPhasesEnum.PICK_FORM_BOARD);
                    Platform.runLater(g::renderName);
                }
            }
            case "board" -> {
                if (phase.equals(ClientPhasesEnum.WAITING_FOR_TURN)) {
                    Scene4WaitingController g = (Scene4WaitingController) phaseToControllerMap.get(ClientPhasesEnum.WAITING_FOR_TURN);
                    Platform.runLater(g::renderBoard);
                }
                if (phase.equals(ClientPhasesEnum.PICK_FORM_BOARD)) {
                    Scene4BoardSceneController g = (Scene4BoardSceneController) phaseToControllerMap.get(ClientPhasesEnum.PICK_FORM_BOARD);
                    Platform.runLater(g::renderPickableBoard);
                }
            }
            case "activePlayerShelf" -> {
                if (phase.equals(ClientPhasesEnum.WAITING_FOR_TURN)) {
                    Scene4WaitingController g = (Scene4WaitingController) phaseToControllerMap.get(ClientPhasesEnum.WAITING_FOR_TURN);
                    Platform.runLater(g::renderShelves);
                }
                if (phase.equals(ClientPhasesEnum.PICK_FORM_BOARD)) {
                    Scene4BoardSceneController g = (Scene4BoardSceneController) phaseToControllerMap.get(ClientPhasesEnum.PICK_FORM_BOARD);
                    Platform.runLater(g::renderShelves);
                }
                if (phase.equals(ClientPhasesEnum.SELECT_COLUMN)) {
                    Scene4SelectColumnController g = (Scene4SelectColumnController) phaseToControllerMap.get(ClientPhasesEnum.SELECT_COLUMN);
                    Platform.runLater(g::renderShelves);
                }
            }
            case "lastChatMessage" -> {
                if (phase.equals(ClientPhasesEnum.LOBBY) ||
                phase.equals(ClientPhasesEnum.DECIDING_FOR_RELOAD) ||
                phase.equals(ClientPhasesEnum.WAITING_FOR_TURN) ||
                phase.equals(ClientPhasesEnum.PICK_FORM_BOARD) ||
                phase.equals(ClientPhasesEnum.SELECT_COLUMN) ||
                phase.equals(ClientPhasesEnum.FINAL_RESULTS_SHOW)) {
                    SceneWithChatController sceneController  = (SceneWithChatController) phaseToControllerMap.get(phase);
                    Platform.runLater(()->{
                        sceneController.updateChat(currentState.getLastChatMessage());
                    });
                }
            }
        }
    }
}
