package it.polimi.ingsw.client.view.gui;

import it.polimi.ingsw.client.view.UserInterface;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * This class contains main info of GUI
 * @author Marco Galli
 */
public class Gui extends Application implements UserInterface {

    @Override
    public void run() {
        Application.launch();
    }

    @Override
    public void onGameAborted() {
        // esce un messaggio "partita finita"
    }

    @Override
    public void start(Stage stage) throws Exception {

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/scene1Login.fxml"));
            Parent root = null;
            root = loader.load();
            Scene scene = new Scene(root);
            stage.setTitle("My Shelfie");
            stage.getIcons().add(new Image("file:src/main/resources/images/Publisher material/Icon 50x50px.png"));
            stage.setScene(scene);
            stage.setMaximized(true);
            stage.setFullScreen(false);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
