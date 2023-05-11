package it.polimi.ingsw.client.view.gui;

import it.polimi.ingsw.client.view.cli.Printer;

import java.util.HashMap;
import javafx.scene.image.Image;

/**
 * This object contains all the resources that are useful for the gui.
 * @author Luca Guffanti
 */
public class MediaManager {
    public static HashMap<String, Image> personalGoalToImage = new HashMap<>();
    public static HashMap<String, Image> commonGoalToImage = new HashMap<>();
    public static HashMap<String, String> jsonCommonGoalIdToResourceId = new HashMap<>();

    static {
        jsonCommonGoalIdToResourceId.put("0", "4");
        jsonCommonGoalIdToResourceId.put("1", "11");
        jsonCommonGoalIdToResourceId.put("2", "8");
        jsonCommonGoalIdToResourceId.put("3", "7");
        jsonCommonGoalIdToResourceId.put("4", "3");
        jsonCommonGoalIdToResourceId.put("5", "2");
        jsonCommonGoalIdToResourceId.put("6", "1");
        jsonCommonGoalIdToResourceId.put("7", "6");
        jsonCommonGoalIdToResourceId.put("8", "5");
        jsonCommonGoalIdToResourceId.put("9", "10");
        jsonCommonGoalIdToResourceId.put("10", "9");
        jsonCommonGoalIdToResourceId.put("11", "12");
    }

    /**
     * This method loads all the graphic resources
     * <ol>
     *     <li>loads the personalGoals</li>
     *     <li>loads the commonGoals</li>
     *     <li>loads the objectCards</li>
     * </ol>
     */
    public static void loadGraphicResources() {
        try {
            personalGoalToImage.put("1", new Image("file:src/main/resources/images/personal goal cards/Personal_Goals.png"));
            personalGoalToImage.put("2", new Image("file:src/main/resources/images/personal goal cards/Personal_Goals2.png"));
            personalGoalToImage.put("3", new Image("file:src/main/resources/images/personal goal cards/Personal_Goals3.png"));
            personalGoalToImage.put("4", new Image("file:src/main/resources/images/personal goal cards/Personal_Goals4.png"));
            personalGoalToImage.put("5", new Image("file:src/main/resources/images/personal goal cards/Personal_Goals5.png"));
            personalGoalToImage.put("6", new Image("file:src/main/resources/images/personal goal cards/Personal_Goals6.png"));
            personalGoalToImage.put("7", new Image("file:src/main/resources/images/personal goal cards/Personal_Goals7.png"));
            personalGoalToImage.put("8", new Image("file:src/main/resources/images/personal goal cards/Personal_Goals8.png"));
            personalGoalToImage.put("9", new Image("file:src/main/resources/images/personal goal cards/Personal_Goals9.png"));
            personalGoalToImage.put("10", new Image("file:src/main/resources/images/personal goal cards/Personal_Goals10.png"));
            personalGoalToImage.put("11", new Image("file:src/main/resources/images/personal goal cards/Personal_Goals11.png"));
            personalGoalToImage.put("12", new Image("file:src/main/resources/images/personal goal cards/Personal_Goals12.png"));

            commonGoalToImage.put("1", new Image("file:src/main/resources/images/common goal cards/1.png"));
            commonGoalToImage.put("2", new Image("file:src/main/resources/images/common goal cards/2.png"));
            commonGoalToImage.put("3", new Image("file:src/main/resources/images/common goal cards/3.png"));
            commonGoalToImage.put("4", new Image("file:src/main/resources/images/common goal cards/4.png"));
            commonGoalToImage.put("5", new Image("file:src/main/resources/images/common goal cards/5.png"));
            commonGoalToImage.put("6", new Image("file:src/main/resources/images/common goal cards/6.png"));
            commonGoalToImage.put("7", new Image("file:src/main/resources/images/common goal cards/7.png"));
            commonGoalToImage.put("8", new Image("file:src/main/resources/images/common goal cards/8.png"));
            commonGoalToImage.put("9", new Image("file:src/main/resources/images/common goal cards/9.png"));
            commonGoalToImage.put("10", new Image("file:src/main/resources/images/common goal cards/10.png"));
            commonGoalToImage.put("11", new Image("file:src/main/resources/images/common goal cards/11.png"));
            commonGoalToImage.put("12", new Image("file:src/main/resources/images/common goal cards/12.png"));

        } catch (Exception e) {
            e.printStackTrace();
            Printer.error("COULD NOT LOAD GRAPHIC RESOURCES");
            System.exit(1);
        }
    }

    public static void main(String[] args) {
        System.out.println(personalGoalToImage.get("1"));
    }
}
