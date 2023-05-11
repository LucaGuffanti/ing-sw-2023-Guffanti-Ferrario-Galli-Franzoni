package it.polimi.ingsw.client.view.gui;

import it.polimi.ingsw.client.view.cli.Printer;

import java.io.FileInputStream;
import java.util.HashMap;

import it.polimi.ingsw.server.model.cards.ObjectTypeEnum;
import javafx.scene.image.Image;
import javafx.scene.image.PixelBuffer;

/**
 * This object contains all the resources that are useful for the gui.
 * @author Luca Guffanti
 */
public class MediaManager {
    public static HashMap<String, Image> personalGoalToImage = new HashMap<>();
    public static HashMap<String, Image> commonGoalToImage = new HashMap<>();
    public static HashMap<String, String> jsonCommonGoalIdToResourceId = new HashMap<>();
    public static HashMap<ObjectTypeEnum, Image> tileToImage = new HashMap<>();

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
            personalGoalToImage.put("1", new Image(new FileInputStream("src/main/resources/images/personal goal cards/Personal_Goals.png")));
            personalGoalToImage.put("2", new Image(new FileInputStream("src/main/resources/images/personal goal cards/Personal_Goals2.png")));
            personalGoalToImage.put("3", new Image(new FileInputStream("src/main/resources/images/personal goal cards/Personal_Goals3.png")));
            personalGoalToImage.put("4", new Image(new FileInputStream("src/main/resources/images/personal goal cards/Personal_Goals4.png")));
            personalGoalToImage.put("5", new Image(new FileInputStream("src/main/resources/images/personal goal cards/Personal_Goals5.png")));
            personalGoalToImage.put("6", new Image(new FileInputStream("src/main/resources/images/personal goal cards/Personal_Goals6.png")));
            personalGoalToImage.put("7", new Image(new FileInputStream("src/main/resources/images/personal goal cards/Personal_Goals7.png")));
            personalGoalToImage.put("8", new Image(new FileInputStream("src/main/resources/images/personal goal cards/Personal_Goals8.png")));
            personalGoalToImage.put("9", new Image(new FileInputStream("src/main/resources/images/personal goal cards/Personal_Goals9.png")));
            personalGoalToImage.put("10", new Image(new FileInputStream("src/main/resources/images/personal goal cards/Personal_Goals10.png")));
            personalGoalToImage.put("11", new Image(new FileInputStream("src/main/resources/images/personal goal cards/Personal_Goals11.png")));
            personalGoalToImage.put("12", new Image(new FileInputStream("src/main/resources/images/personal goal cards/Personal_Goals12.png")));

            for(Image i : personalGoalToImage.values()) {
                System.out.println(i);
            }

            commonGoalToImage.put("1", new Image(new FileInputStream("src/main/resources/images/common goal cards/1.jpg")));
            commonGoalToImage.put("2", new Image(new FileInputStream("src/main/resources/images/common goal cards/2.jpg")));
            commonGoalToImage.put("3", new Image(new FileInputStream("src/main/resources/images/common goal cards/3.jpg")));
            commonGoalToImage.put("4", new Image(new FileInputStream("src/main/resources/images/common goal cards/4.jpg")));
            commonGoalToImage.put("5", new Image(new FileInputStream("src/main/resources/images/common goal cards/5.jpg")));
            commonGoalToImage.put("6", new Image(new FileInputStream("src/main/resources/images/common goal cards/6.jpg")));
            commonGoalToImage.put("7", new Image(new FileInputStream("src/main/resources/images/common goal cards/7.jpg")));
            commonGoalToImage.put("8", new Image(new FileInputStream("src/main/resources/images/common goal cards/8.jpg")));
            commonGoalToImage.put("9", new Image(new FileInputStream("src/main/resources/images/common goal cards/9.jpg")));
            commonGoalToImage.put("10", new Image(new FileInputStream("src/main/resources/images/common goal cards/10.jpg")));
            commonGoalToImage.put("11", new Image(new FileInputStream("src/main/resources/images/common goal cards/11.jpg")));
            commonGoalToImage.put("12", new Image(new FileInputStream("src/main/resources/images/common goal cards/12.jpg")));


            for(Image i : commonGoalToImage.values()) {
                System.out.println(i);
            }

            tileToImage.put(ObjectTypeEnum.CAT,  new Image(new FileInputStream("src/main/resources/images/item tiles/Gatti1.1.png")));
            tileToImage.put(ObjectTypeEnum.BOOK, new Image(new FileInputStream("src/main/resources/images/item tiles/Libri1.1.png")));
            tileToImage.put(ObjectTypeEnum.TOY, new Image(new FileInputStream("src/main/resources/images/item tiles/Giochi1.1.png")));
            tileToImage.put(ObjectTypeEnum.FRAME, new Image(new FileInputStream("src/main/resources/images/item tiles/Cornici1.1.png")));
            tileToImage.put(ObjectTypeEnum.TROPHY, new Image(new FileInputStream("src/main/resources/images/item tiles/Trofei1.1.png")));
            tileToImage.put(ObjectTypeEnum.PLANT, new Image(new FileInputStream("src/main/resources/images/item tiles/Piante1.1.png")));


            for(Image i : tileToImage.values()) {
                System.out.println(i);
            }

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
