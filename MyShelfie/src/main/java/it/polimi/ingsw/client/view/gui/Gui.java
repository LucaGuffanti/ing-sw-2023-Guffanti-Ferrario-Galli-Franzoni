package it.polimi.ingsw.client.view.gui;

import it.polimi.ingsw.client.view.UserInterface;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * This class contains main info of GUI
 * @author Marco Galli
 */
public class Gui extends Application implements UserInterface {
    Stage window;
    Scene sceneMainMenu, sceneGame;
    Button buttonQuit, buttonStart, buttonBack;

    @Override
    public void run() {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // STAGES
        window = primaryStage;
        window.setTitle("My Shelfie");

        // LABELS
        Label labelMainMenu = new Label("Welcome, press one of these two buttons!");

        Label labelGame = new Label("For now you can only go back :(");

        // BUTTONS
        buttonStart = new Button("Start");
        buttonStart.setOnAction(actionEvent -> {
            window.setScene(sceneGame);
        });

        buttonQuit = new Button("Quit");
        buttonQuit.setOnAction(actionEvent -> {
            window.close();
        });

        buttonBack = new Button("PICK");
        buttonBack.setOnAction(actionEvent -> {
            window.setScene(sceneMainMenu);
        });

        // LAYOUT AND SCENES
        HBox layoutMainMenu = new HBox();
        layoutMainMenu.getChildren().addAll(labelMainMenu, buttonStart, buttonQuit);
        sceneMainMenu = new Scene(layoutMainMenu, 500, 100);

        VBox layoutGame = new VBox();
        layoutGame.getChildren().addAll(labelGame, buttonBack);
        sceneGame = new Scene(layoutGame, 100, 500);

        // DISPLAY
        window.setScene(sceneMainMenu);
        window.show();
    }
}
