package it.polimi.ingsw.client.view.gui;

import it.polimi.ingsw.client.view.cli.Printer;

import java.util.HashMap;

import it.polimi.ingsw.server.model.cards.ObjectTypeEnum;
import it.polimi.ingsw.server.model.cards.PointEnumeration;
import javafx.scene.image.Image;

/**
 * This object contains all the resources that are useful for the gui.
 * @author Luca Guffanti
 */
public class MediaManager {
    /**
     *  The map which contains personal goal ID mapped to their relative image.
     */
    public static HashMap<String, Image> personalGoalToImage = new HashMap<>();

    /**
     *  The map which contains common goal ID mapped to their relative image.
     */
    public static HashMap<String, Image> commonGoalToImage = new HashMap<>();

    /**
     *  The map which contains our local common goal ID mapped to their correct ID as found in resources.
     */
    public static HashMap<String, String> jsonCommonGoalIdToResourceId = new HashMap<>();

    /**
     *  The map which contains tiles ID mapped to their relative image.
     */
    public static HashMap<ObjectTypeEnum, Image> tileToImage = new HashMap<>();

    /**
     *  The map which contains point cards ID mapped to their relative image.
     */
    public static HashMap<PointEnumeration, Image> pointToImage = new HashMap<>();

    /**
     * The card point of end game.
     */
    public static Image endOfGamePoint;

    /**
     * Compiling map jsonCommonGoalIdToResourceId.
     */
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

            for(Image i : personalGoalToImage.values()) {
                System.out.println(i);
                i.getPixelReader();
                if (i.errorProperty().getValue()) {
                    i.exceptionProperty().getValue().printStackTrace();
                }
            }

            commonGoalToImage.put("1", new Image("file:src/main/resources/images/common goal cards/1.jpg"));
            commonGoalToImage.put("2", new Image("file:src/main/resources/images/common goal cards/2.jpg"));
            commonGoalToImage.put("3", new Image("file:src/main/resources/images/common goal cards/3.jpg"));
            commonGoalToImage.put("4", new Image("file:src/main/resources/images/common goal cards/4.jpg"));
            commonGoalToImage.put("5", new Image("file:src/main/resources/images/common goal cards/5.jpg"));
            commonGoalToImage.put("6", new Image("file:src/main/resources/images/common goal cards/6.jpg"));
            commonGoalToImage.put("7", new Image("file:src/main/resources/images/common goal cards/7.jpg"));
            commonGoalToImage.put("8", new Image("file:src/main/resources/images/common goal cards/8.jpg"));
            commonGoalToImage.put("9", new Image("file:src/main/resources/images/common goal cards/9.jpg"));
            commonGoalToImage.put("10", new Image("file:src/main/resources/images/common goal cards/10.jpg"));
            commonGoalToImage.put("11", new Image("file:src/main/resources/images/common goal cards/11.jpg"));
            commonGoalToImage.put("12", new Image("file:src/main/resources/images/common goal cards/12.jpg"));


            for(Image i : commonGoalToImage.values()) {
                System.out.println(i);
                i.getPixelReader();
                if (i.errorProperty().getValue()) {
                    i.exceptionProperty().getValue().printStackTrace();
                }
            }

            tileToImage.put(ObjectTypeEnum.CAT,  new Image("file:src/main/resources/images/item tiles/Gatti1.1.png", 60, 60 ,false, false));
            tileToImage.put(ObjectTypeEnum.BOOK, new Image("file:src/main/resources/images/item tiles/Libri1.1.png", 60, 60 ,false, false));
            tileToImage.put(ObjectTypeEnum.TOY, new Image("file:src/main/resources/images/item tiles/Giochi1.1.png", 60, 60 ,false, false));
            tileToImage.put(ObjectTypeEnum.FRAME, new Image("file:src/main/resources/images/item tiles/Cornici1.1.png", 60, 60 ,false, false));
            tileToImage.put(ObjectTypeEnum.TROPHY, new Image("file:src/main/resources/images/item tiles/Trofei1.1.png", 60, 60 ,false, false));
            tileToImage.put(ObjectTypeEnum.PLANT, new Image("file:src/main/resources/images/item tiles/Piante1.1.png", 60, 60 ,false, false));

            for(Image i : tileToImage.values()) {
                System.out.println(i);
                i.getPixelReader();
                if (i.errorProperty().getValue()) {
                    i.exceptionProperty().getValue().printStackTrace();
                }
            }

            pointToImage.put(PointEnumeration.TWO_POINTS, new Image("file:src/main/resources/images/scoring tokens/scoring_2.jpg"));
            pointToImage.put(PointEnumeration.FOUR_POINTS, new Image("file:src/main/resources/images/scoring tokens/scoring_4.jpg"));
            pointToImage.put(PointEnumeration.SIX_POINTS, new Image("file:src/main/resources/images/scoring tokens/scoring_6.jpg"));
            pointToImage.put(PointEnumeration.EIGHT_POINTS, new Image("file:src/main/resources/images/scoring tokens/scoring_8.jpg"));

            for (Image i : pointToImage.values()) {
                System.out.println(i);
                i.getPixelReader();
                if (i.errorProperty().getValue()) {
                    i.exceptionProperty().getValue().printStackTrace();
                }
            }

            endOfGamePoint = new Image("file:src/main/resources/images/scoring tokens/end game.jpg");

        } catch (Exception e) {
            e.printStackTrace();
            Printer.error("COULD NOT LOAD GRAPHIC RESOURCES");
            System.exit(1);
        }
    }
}
