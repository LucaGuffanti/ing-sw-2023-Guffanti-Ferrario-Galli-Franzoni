package it.polimi.ingsw.model;

import it.polimi.ingsw.model.cards.PointCard;
import it.polimi.ingsw.model.cards.PointEnumeration;
import it.polimi.ingsw.model.cards.goalCards.*;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Shelf;
import it.polimi.ingsw.model.utils.CsvToShelfParser;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

/**
 * These methods test the behaviour of CommonGoalCard pattern matching algorithms
 * @author Daniele Ferrario
 */
public class CommonGoalCardTest {

    final private static GoalCardsDeckSingleton goalCardsDeckSingleton = GoalCardsDeckSingleton.getInstance();
    final String ROOT_SHELF_CSV_PATH = "src/test/resources/shelfTEST/";
    @Test
    public void twoSquaresSameColorNonAdjacent_expectedValid() {


        final String SHELF_CSV_NAME = "twoSquaresSameColorNotAdjacent.csv";
        final String GOAL_CARD_ID = "6";
        final int EXPECTED_VALUE = 8;

        final String PLAYER_NICKNAME = "testUser";


        Shelf shelf = null;
        try {
            shelf = CsvToShelfParser.convert(ROOT_SHELF_CSV_PATH+SHELF_CSV_NAME);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Player player = new Player(shelf, PLAYER_NICKNAME);

        CommonGoalCard exampleCard = goalCardsDeckSingleton.getCommonGoalCardById(GOAL_CARD_ID);
        ArrayList<PointCard> points = new ArrayList<>();
        points.add(new PointCard(PointEnumeration.TWO_POINTS, 2));
        points.add(new PointCard(PointEnumeration.FOUR_POINTS,  4));
        points.add(new PointCard(PointEnumeration.SIX_POINTS, 6));
        points.add(new PointCard(PointEnumeration.EIGHT_POINTS, 8));
        exampleCard.setPointsCards(points);


        assertEquals(EXPECTED_VALUE, exampleCard.calculatePoints(player));

    }

    @Test
    public void twoSquaresSameColorAdjacent_expectedNotValid() {

        final String SHELF_CSV_NAME = "twoSquaresSameColorAdjacent.csv";
        final String GOAL_CARD_ID = "6";
        final int EXPECTED_VALUE = 0;

        final String PLAYER_NICKNAME = "testUser";


        Shelf shelf = null;
        try {
            shelf = CsvToShelfParser.convert(ROOT_SHELF_CSV_PATH+SHELF_CSV_NAME);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Player player = new Player(shelf, PLAYER_NICKNAME);

        CommonGoalCard exampleCard = goalCardsDeckSingleton.getCommonGoalCardById(GOAL_CARD_ID);
        ArrayList<PointCard> points = new ArrayList<>();
        points.add(new PointCard(PointEnumeration.TWO_POINTS, 2));
        points.add(new PointCard(PointEnumeration.FOUR_POINTS,  4));
        points.add(new PointCard(PointEnumeration.SIX_POINTS, 6));
        points.add(new PointCard(PointEnumeration.EIGHT_POINTS, 8));
        exampleCard.setPointsCards(points);

        assertEquals(EXPECTED_VALUE, exampleCard.calculatePoints(player));
    }

    @Test
    public void fourAdjacentRowsThreeTypes_expectedValid() {

        final String SHELF_CSV_NAME = "fourAdjacentRowsThreeTypes.csv";
        final String GOAL_CARD_ID = "3";
        final int EXPECTED_VALUE = 8;

        final String PLAYER_NICKNAME = "testUser";


        Shelf shelf = null;
        try {
            shelf = CsvToShelfParser.convert(ROOT_SHELF_CSV_PATH+SHELF_CSV_NAME);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Player player = new Player(shelf, PLAYER_NICKNAME);

        CommonGoalCard exampleCard = goalCardsDeckSingleton.getCommonGoalCardById(GOAL_CARD_ID);
        ArrayList<PointCard> points = new ArrayList<>();
        points.add(new PointCard(PointEnumeration.TWO_POINTS, 2));
        points.add(new PointCard(PointEnumeration.FOUR_POINTS,  4));
        points.add(new PointCard(PointEnumeration.SIX_POINTS, 6));
        points.add(new PointCard(PointEnumeration.EIGHT_POINTS, 8));
        exampleCard.setPointsCards(points);

        assertEquals(EXPECTED_VALUE, exampleCard.calculatePoints(player));
    }

    @Test
    public void fourAdjacentRowsThreeTypes_expectedNonValid() {

        final String SHELF_CSV_NAME = "fourAdjacentRowsFourTypes.csv";
        final String GOAL_CARD_ID = "3";
        final int EXPECTED_VALUE = 0;

        final String PLAYER_NICKNAME = "testUser";


        Shelf shelf = null;
        try {
            shelf = CsvToShelfParser.convert(ROOT_SHELF_CSV_PATH+SHELF_CSV_NAME);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Player player = new Player(shelf, PLAYER_NICKNAME);

        CommonGoalCard exampleCard = goalCardsDeckSingleton.getCommonGoalCardById(GOAL_CARD_ID);
        ArrayList<PointCard> points = new ArrayList<>();
        points.add(new PointCard(PointEnumeration.TWO_POINTS, 2));
        points.add(new PointCard(PointEnumeration.FOUR_POINTS,  4));
        points.add(new PointCard(PointEnumeration.SIX_POINTS, 6));
        points.add(new PointCard(PointEnumeration.EIGHT_POINTS, 8));
        exampleCard.setPointsCards(points);

        assertEquals(EXPECTED_VALUE, exampleCard.calculatePoints(player));
    }

    /**
     * This method verifies that both the configurations of the pyramid with the highest column being the first
     * work correctly.
     */
    @Test
    public void pyramidFixedShaped_expectedValid() {
        String pathToFilePrefix = "pyramid";
        String fileType = ".csv";
        Shelf shelf;
        CommonGoalCard pyramid = goalCardsDeckSingleton.getCommonGoalCardById("11");

        ArrayList<PointCard> points = new ArrayList<>();
        points.add(new PointCard(PointEnumeration.TWO_POINTS, 2));
        points.add(new PointCard(PointEnumeration.FOUR_POINTS,  4));
        points.add(new PointCard(PointEnumeration.SIX_POINTS, 6));
        points.add(new PointCard(PointEnumeration.EIGHT_POINTS, 8));
        pyramid.setPointsCards(points);

        Player player = new Player("test");

        for (int i = 1; i < 2; i++) {
            String path = ROOT_SHELF_CSV_PATH+pathToFilePrefix+i+fileType;
            try {
                shelf = CsvToShelfParser.convert(path);
                player.setShelf(shelf);
                assertEquals(8, pyramid.calculatePoints(player));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
    /**
     * This method verifies that both the configurations of the pyramid with the highest column being the last
     * work correctly.
     */
    @Test
    public void mirroredPyramidFixedShaped_expectedValid(){
        String pathToFilePrefix = "pyramid";
        String fileType = ".csv";
        Shelf shelf;
        CommonGoalCard pyramid = goalCardsDeckSingleton.getCommonGoalCardById("11");
        ArrayList<PointCard> points = new ArrayList<>();
        points.add(new PointCard(PointEnumeration.TWO_POINTS, 2));
        points.add(new PointCard(PointEnumeration.FOUR_POINTS,  4));
        points.add(new PointCard(PointEnumeration.SIX_POINTS, 6));
        points.add(new PointCard(PointEnumeration.EIGHT_POINTS, 8));
        pyramid.setPointsCards(points);

        Player player = new Player("test");

        for (int i = 1; i < 2; i++) {
            String path = ROOT_SHELF_CSV_PATH+pathToFilePrefix+i+fileType;
            try {
                shelf = CsvToShelfParser.convert(path);
                player.setShelf(shelf);
                assertEquals(8, pyramid.calculatePoints(player));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * This method verifies that the pyramid implementation of check pattern correctly refuses
     * patterns that do not match the pyramid.
     *
     * This test utilises the completed shelf as an example.
     * @todo: check if it should be valid or invalid
     */
    @Test
    public void pyramidFixedShaped_expectedInvalid() {
        final String exampleFile = "fullShelf.csv";
        try {
            Shelf shelf = CsvToShelfParser.convert(ROOT_SHELF_CSV_PATH+exampleFile);
            CommonGoalCard pyramid = goalCardsDeckSingleton.getCommonGoalCardById("11");
            ArrayList<PointCard> points = new ArrayList<>();
            points.add(new PointCard(PointEnumeration.TWO_POINTS, 2));
            points.add(new PointCard(PointEnumeration.FOUR_POINTS,  4));
            points.add(new PointCard(PointEnumeration.SIX_POINTS, 6));
            points.add(new PointCard(PointEnumeration.EIGHT_POINTS, 8));
            pyramid.setPointsCards(points);

            Player player = new Player(shelf, "luca");

            assertEquals(0, pyramid.calculatePoints(player));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * This method verifies that both the configurations of the pyramid with the highest column being the first
     * work correctly.
     */
    @Test
    public void pyramid_expectedValid() {
        String pathToFilePrefix = "pyramid";
        String fileType = ".csv";
        Shelf shelf;
        CommonGoalCard pyramid = new PyramidCommonGoalCard("0");
        ArrayList<PointCard> points = new ArrayList<>();
        points.add(new PointCard(PointEnumeration.TWO_POINTS, 2));
        points.add(new PointCard(PointEnumeration.FOUR_POINTS,  4));
        points.add(new PointCard(PointEnumeration.SIX_POINTS, 6));
        points.add(new PointCard(PointEnumeration.EIGHT_POINTS, 8));
        pyramid.setPointsCards(points);

        Player player = new Player("test");

        for (int i = 1; i < 2; i++) {
            String path = ROOT_SHELF_CSV_PATH+pathToFilePrefix+i+fileType;
            try {
                shelf = CsvToShelfParser.convert(path);
                player.setShelf(shelf);
                assertEquals(8, pyramid.calculatePoints(player));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
    /**
     * This method verifies that both the configurations of the pyramid with the highest column being the last
     * work correctly.
     */
    @Test
    public void mirroredPyramid_expectedValid(){
        String pathToFilePrefix = "pyramid";
        String fileType = ".csv";
        Shelf shelf;
        CommonGoalCard pyramid = new PyramidCommonGoalCard("0");
        ArrayList<PointCard> points = new ArrayList<>();
        points.add(new PointCard(PointEnumeration.TWO_POINTS, 2));
        points.add(new PointCard(PointEnumeration.FOUR_POINTS,  4));
        points.add(new PointCard(PointEnumeration.SIX_POINTS, 6));
        points.add(new PointCard(PointEnumeration.EIGHT_POINTS, 8));
        pyramid.setPointsCards(points);

        Player player = new Player("test");

        for (int i = 1; i < 2; i++) {
            String path = ROOT_SHELF_CSV_PATH+pathToFilePrefix+i+fileType;
            try {
                shelf = CsvToShelfParser.convert(path);
                player.setShelf(shelf);
                assertEquals(8, pyramid.calculatePoints(player));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * This method verifies that the pyramid implementation of check pattern correctly refuses
     * patterns that do not match the pyramid.
     *
     * This test utilises the completed shelf as an example.
     * @todo: check if it should be valid or invalid
     */
    @Test
    public void pyramid_expectedInvalid() {
        final String exampleFile = "fullShelf.csv";
        try {
            Shelf shelf = CsvToShelfParser.convert(ROOT_SHELF_CSV_PATH+exampleFile);
            CommonGoalCard pyramid = new PyramidCommonGoalCard("0");
            ArrayList<PointCard> points = new ArrayList<>();
            points.add(new PointCard(PointEnumeration.TWO_POINTS, 2));
            points.add(new PointCard(PointEnumeration.FOUR_POINTS,  4));
            points.add(new PointCard(PointEnumeration.SIX_POINTS, 6));
            points.add(new PointCard(PointEnumeration.EIGHT_POINTS, 8));
            pyramid.setPointsCards(points);

            Player player = new Player(shelf, "luca");

            assertEquals(0, pyramid.calculatePoints(player));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method verifies that different configurations of the 4 adjacent
     * object cards are correctly identified and that the implementation of
     * check pattern correctly refuses wrong configurations.
     */
    @Test
    public void fourGroupsFourAdjacent_expectedValid() {
        final String SHELF_CSV_NAME = "fourGroupsFourAdjacent.csv";
        final int EXPECTED_VALUE = 8;

        try {
            Shelf shelf = CsvToShelfParser.convert(ROOT_SHELF_CSV_PATH + SHELF_CSV_NAME);
            CommonGoalCard snakes = new SnakesCommonGoalCard("0");
            ArrayList<PointCard> points = new ArrayList<>();
            points.add(new PointCard(PointEnumeration.TWO_POINTS, 2));
            points.add(new PointCard(PointEnumeration.FOUR_POINTS, 4));
            points.add(new PointCard(PointEnumeration.SIX_POINTS, 6));
            points.add(new PointCard(PointEnumeration.EIGHT_POINTS, 8));
            snakes.setPointsCards(points);

            Player player = new Player(shelf, "test");

            assertEquals(8, snakes.calculatePoints(player));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method verifies that
     */
    @Test
    public void fourGroupsFourAdjacent_expectedInvalid() {
        final String SHELF_CSV_NAME = "fourGroupsFourAdjacent_Invalid";
        final int NUM_OF_TESTS = 2;
        final int EXPECTED_VALUE = 0;

        try {
            for (int i = 1; i <= NUM_OF_TESTS; i++) {
                Shelf shelf = CsvToShelfParser.convert(ROOT_SHELF_CSV_PATH + SHELF_CSV_NAME + i + ".csv");
                CommonGoalCard snakes = new SnakesCommonGoalCard("0");
                ArrayList<PointCard> points = new ArrayList<>();
                points.add(new PointCard(PointEnumeration.TWO_POINTS, 2));
                points.add(new PointCard(PointEnumeration.FOUR_POINTS, 4));
                points.add(new PointCard(PointEnumeration.SIX_POINTS, 6));
                points.add(new PointCard(PointEnumeration.EIGHT_POINTS, 8));
                snakes.setPointsCards(points);

                Player player = new Player(shelf, "test");

                assertEquals(EXPECTED_VALUE, snakes.calculatePoints(player));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
