package it.polimi.ingsw.model;

import it.polimi.ingsw.client.view.cli.Printer;
import it.polimi.ingsw.server.controller.utils.GameObjectConverter;
import it.polimi.ingsw.server.model.GoalCardsDeckSingleton;
import it.polimi.ingsw.server.model.cards.PointCard;
import it.polimi.ingsw.server.model.cards.PointEnumeration;
import it.polimi.ingsw.server.model.cards.goalCards.CommonGoalCard;
import it.polimi.ingsw.server.model.player.Player;
import it.polimi.ingsw.server.model.player.Shelf;
import it.polimi.ingsw.server.model.utils.CsvToShelfParser;
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
            shelf = CsvToShelfParser.convert(ROOT_SHELF_CSV_PATH + SHELF_CSV_NAME);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Player player = new Player(shelf, PLAYER_NICKNAME);

        CommonGoalCard exampleCard = goalCardsDeckSingleton.getCommonGoalCardById(GOAL_CARD_ID);
        ArrayList<PointCard> points = new ArrayList<>();
        points.add(new PointCard(PointEnumeration.TWO_POINTS, 2));
        points.add(new PointCard(PointEnumeration.FOUR_POINTS, 4));
        points.add(new PointCard(PointEnumeration.SIX_POINTS, 6));
        points.add(new PointCard(PointEnumeration.EIGHT_POINTS, 8));
        exampleCard.setPointsCards(points);

        Printer.printSimplifiedCommonGoal(GameObjectConverter.fromCommonGoalToSimplifiedPartialCommonGoal(exampleCard));
        assertEquals(EXPECTED_VALUE, exampleCard.calculatePoints(player));
        assertEquals(3, exampleCard.getPointsCards().size());
        Printer.printSimplifiedCommonGoal(GameObjectConverter.fromCommonGoalToSimplifiedPartialCommonGoal(exampleCard));
    }

    @Test
    public void twoSquaresSameColorAdjacent_expectedNotValid() {

        final String SHELF_CSV_NAME = "twoSquaresSameColorAdjacent.csv";
        final String GOAL_CARD_ID = "6";
        final int EXPECTED_VALUE = 0;

        final String PLAYER_NICKNAME = "testUser";


        Shelf shelf = null;
        try {
            shelf = CsvToShelfParser.convert(ROOT_SHELF_CSV_PATH + SHELF_CSV_NAME);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Player player = new Player(shelf, PLAYER_NICKNAME);

        CommonGoalCard exampleCard = goalCardsDeckSingleton.getCommonGoalCardById(GOAL_CARD_ID);
        ArrayList<PointCard> points = new ArrayList<>();
        points.add(new PointCard(PointEnumeration.TWO_POINTS, 2));
        points.add(new PointCard(PointEnumeration.FOUR_POINTS, 4));
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
            shelf = CsvToShelfParser.convert(ROOT_SHELF_CSV_PATH + SHELF_CSV_NAME);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Player player = new Player(shelf, PLAYER_NICKNAME);

        CommonGoalCard exampleCard = goalCardsDeckSingleton.getCommonGoalCardById(GOAL_CARD_ID);
        ArrayList<PointCard> points = new ArrayList<>();
        points.add(new PointCard(PointEnumeration.TWO_POINTS, 2));
        points.add(new PointCard(PointEnumeration.FOUR_POINTS, 4));
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
            shelf = CsvToShelfParser.convert(ROOT_SHELF_CSV_PATH + SHELF_CSV_NAME);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Player player = new Player(shelf, PLAYER_NICKNAME);

        CommonGoalCard exampleCard = goalCardsDeckSingleton.getCommonGoalCardById(GOAL_CARD_ID);
        ArrayList<PointCard> points = new ArrayList<>();
        points.add(new PointCard(PointEnumeration.TWO_POINTS, 2));
        points.add(new PointCard(PointEnumeration.FOUR_POINTS, 4));
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
    public void pyramid_expectedValid() {
        String pathToFilePrefix = "pyramid";
        String fileType = ".csv";
        Shelf shelf;
        CommonGoalCard pyramid = goalCardsDeckSingleton.getCommonGoalCardById("11");

        ArrayList<PointCard> points = new ArrayList<>();
        points.add(new PointCard(PointEnumeration.TWO_POINTS, 2));
        points.add(new PointCard(PointEnumeration.FOUR_POINTS, 4));
        points.add(new PointCard(PointEnumeration.SIX_POINTS, 6));
        points.add(new PointCard(PointEnumeration.EIGHT_POINTS, 8));
        pyramid.setPointsCards(points);

        Player player = new Player("test");

        for (int i = 1; i < 2; i++) {
            String path = ROOT_SHELF_CSV_PATH + pathToFilePrefix + i + fileType;
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
    public void mirroredPyramid_expectedValid() {
        String pathToFilePrefix = "pyramid";
        String fileType = ".csv";
        Shelf shelf;
        CommonGoalCard pyramid = goalCardsDeckSingleton.getCommonGoalCardById("11");
        ArrayList<PointCard> points = new ArrayList<>();
        points.add(new PointCard(PointEnumeration.TWO_POINTS, 2));
        points.add(new PointCard(PointEnumeration.FOUR_POINTS, 4));
        points.add(new PointCard(PointEnumeration.SIX_POINTS, 6));
        points.add(new PointCard(PointEnumeration.EIGHT_POINTS, 8));
        pyramid.setPointsCards(points);

        Player player = new Player("test");

        for (int i = 1; i < 2; i++) {
            String path = ROOT_SHELF_CSV_PATH + pathToFilePrefix + i + fileType;
            try {
                shelf = CsvToShelfParser.convert(path);
                player.setShelf(shelf);
                assertEquals(8, pyramid.calculatePoints(player));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Test
    public void cellsDiagonal_expectedInvalid() {

        final String SHELF_CSV_NAME = "diagonalInvalid.csv";
        final String GOAL_CARD_ID = "1";
        final int EXPECTED_VALUE = 0;

        final String PLAYER_NICKNAME = "testUser";


        Shelf shelf = null;
        try {
            shelf = CsvToShelfParser.convert(ROOT_SHELF_CSV_PATH + SHELF_CSV_NAME);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Player player = new Player(shelf, PLAYER_NICKNAME);

        CommonGoalCard diagonalCommonGoalNotOK = goalCardsDeckSingleton.getCommonGoalCardById(GOAL_CARD_ID);
        ArrayList<PointCard> points = new ArrayList<>();
        points.add(new PointCard(PointEnumeration.TWO_POINTS, 2));
        points.add(new PointCard(PointEnumeration.FOUR_POINTS, 4));
        points.add(new PointCard(PointEnumeration.SIX_POINTS, 6));
        points.add(new PointCard(PointEnumeration.EIGHT_POINTS, 8));
        diagonalCommonGoalNotOK.setPointsCards(points);

        assertEquals(EXPECTED_VALUE, diagonalCommonGoalNotOK.calculatePoints(player));
    }

    @Test
    public void diagonalCells_expectedValid() {

        final String SHELF_CSV_NAME = "diagonalValid.csv";
        final String GOAL_CARD_ID = "1";
        final int EXPECTED_VALUE = 8;

        final String PLAYER_NICKNAME = "testUser";


        Shelf shelf = null;
        try {
            shelf = CsvToShelfParser.convert(ROOT_SHELF_CSV_PATH + SHELF_CSV_NAME);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Player player = new Player(shelf, PLAYER_NICKNAME);

        CommonGoalCard diagonalCommonGoalOk = goalCardsDeckSingleton.getCommonGoalCardById(GOAL_CARD_ID);
        ArrayList<PointCard> points = new ArrayList<>();
        points.add(new PointCard(PointEnumeration.TWO_POINTS, 2));
        points.add(new PointCard(PointEnumeration.FOUR_POINTS, 4));
        points.add(new PointCard(PointEnumeration.SIX_POINTS, 6));
        points.add(new PointCard(PointEnumeration.EIGHT_POINTS, 8));
        diagonalCommonGoalOk.setPointsCards(points);

        assertEquals(EXPECTED_VALUE, diagonalCommonGoalOk.calculatePoints(player));
    }

    @Test
    public void fourAnglesOfTheShelf_expectedValid() {
        final String SHELF_NAME = "fourAnglesOfTheShelfValid.csv";
        final String PATTERN_ID = "2";
        final int EXPECTED_VALUE = 8;
        final String NAME_PLAYER = "Test User";

        Shelf shelf = null;
        try {
            shelf = CsvToShelfParser.convert(ROOT_SHELF_CSV_PATH + SHELF_NAME);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Player player = new Player(shelf, NAME_PLAYER);
        CommonGoalCard fourAnglesOk = goalCardsDeckSingleton.getCommonGoalCardById(PATTERN_ID);
        ArrayList<PointCard> points = new ArrayList<>();
        points.add(new PointCard(PointEnumeration.TWO_POINTS, 2));
        points.add(new PointCard(PointEnumeration.FOUR_POINTS, 4));
        points.add(new PointCard(PointEnumeration.SIX_POINTS, 6));
        points.add(new PointCard(PointEnumeration.EIGHT_POINTS, 8));
        fourAnglesOk.setPointsCards(points);

        assertEquals(EXPECTED_VALUE, fourAnglesOk.calculatePoints(player));
    }

    @Test
    public void fourAnglesOfTheShelf_expectedInvalid() {
        final String SHELF_NAME = "fourAnglesOfTheShelfInvalid.csv";
        final String PATTERN_ID = "2";
        final int EXPECTED_VALUE = 0;
        final String NAME_PLAYER = "Test User";

        Shelf shelf = null;
        try {
            shelf = CsvToShelfParser.convert(ROOT_SHELF_CSV_PATH + SHELF_NAME);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Player player = new Player(shelf, NAME_PLAYER);
        CommonGoalCard fourAnglesNotOk = goalCardsDeckSingleton.getCommonGoalCardById(PATTERN_ID);
        ArrayList<PointCard> points = new ArrayList<>();
        points.add(new PointCard(PointEnumeration.TWO_POINTS, 2));
        points.add(new PointCard(PointEnumeration.FOUR_POINTS, 4));
        points.add(new PointCard(PointEnumeration.SIX_POINTS, 6));
        points.add(new PointCard(PointEnumeration.EIGHT_POINTS, 8));
        fourAnglesNotOk.setPointsCards(points);

        assertEquals(EXPECTED_VALUE, fourAnglesNotOk.calculatePoints(player));
    }

    @Test
    public void twoDifferentRowsOfSixCells_expectedInvalid() {
        final String SHELF_NAME = "twoRowsDifferentTypesInvalid.csv";
        final String PATTERN_ID = "5";
        final int EXPECTED_VALUE = 0;
        final String NAME_PLAYER = "Test User";

        Shelf shelf = null;
        try {
            shelf = CsvToShelfParser.convert(ROOT_SHELF_CSV_PATH + SHELF_NAME);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Player player = new Player(shelf, NAME_PLAYER);
        CommonGoalCard twoRowsNotOk = goalCardsDeckSingleton.getCommonGoalCardById(PATTERN_ID);
        ArrayList<PointCard> points = new ArrayList<>();
        points.add(new PointCard(PointEnumeration.TWO_POINTS, 2));
        points.add(new PointCard(PointEnumeration.FOUR_POINTS, 4));
        points.add(new PointCard(PointEnumeration.SIX_POINTS, 6));
        points.add(new PointCard(PointEnumeration.EIGHT_POINTS, 8));
        twoRowsNotOk.setPointsCards(points);

        assertEquals(EXPECTED_VALUE, twoRowsNotOk.calculatePoints(player));
    }

    @Test
    public void twoDifferentRowsOfSixCells_expectedValid() {
        final String SHELF_NAME = "twoRowsDifferentTypesValid.csv";
        final String PATTERN_ID = "5";
        final int EXPECTED_VALUE = 8;
        final String NAME_PLAYER = "Test User";

        Shelf shelf = null;
        try {
            shelf = CsvToShelfParser.convert(ROOT_SHELF_CSV_PATH + SHELF_NAME);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Player player = new Player(shelf, NAME_PLAYER);
        CommonGoalCard twoRowsOk = goalCardsDeckSingleton.getCommonGoalCardById(PATTERN_ID);
        ArrayList<PointCard> points = new ArrayList<>();
        points.add(new PointCard(PointEnumeration.TWO_POINTS, 2));
        points.add(new PointCard(PointEnumeration.FOUR_POINTS, 4));
        points.add(new PointCard(PointEnumeration.SIX_POINTS, 6));
        points.add(new PointCard(PointEnumeration.EIGHT_POINTS, 8));
        twoRowsOk.setPointsCards(points);

        assertEquals(EXPECTED_VALUE, twoRowsOk.calculatePoints(player));
    }
    @Test
    public void eightEqualCells_expectedValid() {
        final String SHELF_NAME = "eigthCellsSameTypeValid.csv";
        final String PATTERN_ID = "10";
        final int EXPECTED_VALUE = 8;
        final String NAME_PLAYER = "Test User";

        Shelf shelf = null;
        try {
            shelf = CsvToShelfParser.convert(ROOT_SHELF_CSV_PATH + SHELF_NAME);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Player player = new Player(shelf, NAME_PLAYER);
        CommonGoalCard eightCellsOk = goalCardsDeckSingleton.getCommonGoalCardById(PATTERN_ID);
        ArrayList<PointCard> points = new ArrayList<>();
        points.add(new PointCard(PointEnumeration.TWO_POINTS, 2));
        points.add(new PointCard(PointEnumeration.FOUR_POINTS, 4));
        points.add(new PointCard(PointEnumeration.SIX_POINTS, 6));
        points.add(new PointCard(PointEnumeration.EIGHT_POINTS, 8));
        eightCellsOk.setPointsCards(points);

        assertEquals(EXPECTED_VALUE, eightCellsOk.calculatePoints(player));
    }
    @Test
    public void eightEqualCells_expectedInvalid() {
        final String SHELF_NAME = "eightCellsSameTypeInvalid.csv";
        final String PATTERN_ID = "10";
        final int EXPECTED_VALUE = 0;
        final String NAME_PLAYER = "Test User";

        Shelf shelf = null;
        try {
            shelf = CsvToShelfParser.convert(ROOT_SHELF_CSV_PATH + SHELF_NAME);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Player player = new Player(shelf, NAME_PLAYER);
        CommonGoalCard eightCellsNotOk = goalCardsDeckSingleton.getCommonGoalCardById(PATTERN_ID);
        ArrayList<PointCard> points = new ArrayList<>();
        points.add(new PointCard(PointEnumeration.TWO_POINTS, 2));
        points.add(new PointCard(PointEnumeration.FOUR_POINTS, 4));
        points.add(new PointCard(PointEnumeration.SIX_POINTS, 6));
        points.add(new PointCard(PointEnumeration.EIGHT_POINTS, 8));
        eightCellsNotOk.setPointsCards(points);

        assertEquals(EXPECTED_VALUE, eightCellsNotOk.calculatePoints(player));
    }

    /*
    problema : regole per scrivere i casi limite nel file json
     @Test
    public void sixGroupTwoCellsSameTypeNotAdjacent_expectedInvalid() {
        final String SHELF_NAME = "";
        final String PATTERN_ID = "0";
        final int EXPECTED_VALUE = 0;
        final String NAME_PLAYER = "Test User";

        Shelf shelf = null;
        try {
            shelf = CsvToShelfParser.convert(ROOT_SHELF_CSV_PATH + SHELF_NAME);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Player player = new Player(shelf, NAME_PLAYER);
        CommonGoalCard sixGroupOfTwoCellsNotOk = goalCardsDeckSingleton.getCommonGoalCardById(PATTERN_ID);
        ArrayList<PointCard> points = new ArrayList<>();
        points.add(new PointCard(PointEnumeration.TWO_POINTS, 2));
        points.add(new PointCard(PointEnumeration.FOUR_POINTS, 4));
        points.add(new PointCard(PointEnumeration.SIX_POINTS, 6));
        points.add(new PointCard(PointEnumeration.EIGHT_POINTS, 8));
        sixGroupOfTwoCellsNotOk.setPointsCards(points);

        assertEquals(EXPECTED_VALUE, sixGroupOfTwoCellsNotOk.calculatePoints(player));
    }

   @Test
    public void sixGroupTwoCellsSameTypeNotAdjacent_expectedValid() {
        final String SHELF_NAME = "";
        final String PATTERN_ID = "0";
        final int EXPECTED_VALUE = 8;
        final String NAME_PLAYER = "Test User";

        Shelf shelf = null;
        try {
            shelf = CsvToShelfParser.convert(ROOT_SHELF_CSV_PATH + SHELF_NAME);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Player player = new Player(shelf, NAME_PLAYER);
        CommonGoalCard sixGroupOfTwoCellsOk = goalCardsDeckSingleton.getCommonGoalCardById(PATTERN_ID);
        ArrayList<PointCard> points = new ArrayList<>();
        points.add(new PointCard(PointEnumeration.TWO_POINTS, 2));
        points.add(new PointCard(PointEnumeration.FOUR_POINTS, 4));
        points.add(new PointCard(PointEnumeration.SIX_POINTS, 6));
        points.add(new PointCard(PointEnumeration.EIGHT_POINTS, 8));
        sixGroupOfTwoCellsOk.setPointsCards(points);

        assertEquals(EXPECTED_VALUE, sixGroupOfTwoCellsOk.calculatePoints(player));
    }
    */

    @Test
    public void crossWithFiveCells_expectedValid() {
        final String SHELF_NAME = "fiveCellsCrossValid.csv";
        final String PATTERN_ID = "9";
        final int EXPECTED_VALUE = 8;
        final String NAME_PLAYER = "Test User";

        Shelf shelf = null;
        try {
            shelf = CsvToShelfParser.convert(ROOT_SHELF_CSV_PATH + SHELF_NAME);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Player player = new Player(shelf, NAME_PLAYER);
        CommonGoalCard crossWithFive = goalCardsDeckSingleton.getCommonGoalCardById(PATTERN_ID);
        ArrayList<PointCard> points = new ArrayList<>();
        points.add(new PointCard(PointEnumeration.TWO_POINTS, 2));
        points.add(new PointCard(PointEnumeration.FOUR_POINTS, 4));
        points.add(new PointCard(PointEnumeration.SIX_POINTS, 6));
        points.add(new PointCard(PointEnumeration.EIGHT_POINTS, 8));
        crossWithFive.setPointsCards(points);

        assertEquals(EXPECTED_VALUE, crossWithFive.calculatePoints(player));
    }

    @Test
    public void crossWithFiveCells_expectedInvalid() {
        final String SHELF_NAME = "fiveCellsCrossInvalid.csv";
        final String PATTERN_ID = "8";
        final int EXPECTED_VALUE = 0;
        final String NAME_PLAYER = "Test User";

        Shelf shelf = null;
        try {
            shelf = CsvToShelfParser.convert(ROOT_SHELF_CSV_PATH + SHELF_NAME);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Player player = new Player(shelf, NAME_PLAYER);
        CommonGoalCard twoRowsOfSixNotOk = goalCardsDeckSingleton.getCommonGoalCardById(PATTERN_ID);
        ArrayList<PointCard> points = new ArrayList<>();
        points.add(new PointCard(PointEnumeration.TWO_POINTS, 2));
        points.add(new PointCard(PointEnumeration.FOUR_POINTS, 4));
        points.add(new PointCard(PointEnumeration.SIX_POINTS, 6));
        points.add(new PointCard(PointEnumeration.EIGHT_POINTS, 8));
        twoRowsOfSixNotOk.setPointsCards(points);

        assertEquals(EXPECTED_VALUE, twoRowsOfSixNotOk.calculatePoints(player));
    }
    @Test
    public void twoRowsSixCellsThreeDifferentTypes_expectedInvalid() {
        final String SHELF_NAME = "twoRowsOfSixCellsMaxThreeTypesInvalid.csv";
        final String PATTERN_ID = "8";
        final int EXPECTED_VALUE = 0;
        final String NAME_PLAYER = "Test User";

        Shelf shelf = null;
        try {
            shelf = CsvToShelfParser.convert(ROOT_SHELF_CSV_PATH + SHELF_NAME);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Player player = new Player(shelf, NAME_PLAYER);
        CommonGoalCard twoRowsOfSixNotOk = goalCardsDeckSingleton.getCommonGoalCardById(PATTERN_ID);
        ArrayList<PointCard> points = new ArrayList<>();
        points.add(new PointCard(PointEnumeration.TWO_POINTS, 2));
        points.add(new PointCard(PointEnumeration.FOUR_POINTS, 4));
        points.add(new PointCard(PointEnumeration.SIX_POINTS, 6));
        points.add(new PointCard(PointEnumeration.EIGHT_POINTS, 8));
        twoRowsOfSixNotOk.setPointsCards(points);

        assertEquals(EXPECTED_VALUE, twoRowsOfSixNotOk.calculatePoints(player));
    }

    @Test
    public void twoDifferentLines_expectedValid() {
        final String SHELF_NAME = "TwoLinesOfFiveDifferentCellsValid.csv";
        final String PATTERN_ID = "7";
        final int EXPECTED_VALUE = 8;
        final String NAME_PLAYER = "Test User";

        Shelf shelf = null;
        try {
            shelf = CsvToShelfParser.convert(ROOT_SHELF_CSV_PATH + SHELF_NAME);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Player player = new Player(shelf, NAME_PLAYER);
        CommonGoalCard twoLinesOk = goalCardsDeckSingleton.getCommonGoalCardById(PATTERN_ID);
        ArrayList<PointCard> points = new ArrayList<>();
        points.add(new PointCard(PointEnumeration.TWO_POINTS, 2));
        points.add(new PointCard(PointEnumeration.FOUR_POINTS, 4));
        points.add(new PointCard(PointEnumeration.SIX_POINTS, 6));
        points.add(new PointCard(PointEnumeration.EIGHT_POINTS, 8));
        twoLinesOk.setPointsCards(points);

        assertEquals(EXPECTED_VALUE, twoLinesOk.calculatePoints(player));
    }

    @Test
    public void twoDifferentLines_expectedInvalid() {
        final String SHELF_NAME = "twoLinesOfFiveDifferentCellsInvalid.csv";
        final String PATTERN_ID = "7";
        final int EXPECTED_VALUE = 0;
        final String NAME_PLAYER = "Test User";

        Shelf shelf = null;
        try {
            shelf = CsvToShelfParser.convert(ROOT_SHELF_CSV_PATH + SHELF_NAME);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Player player = new Player(shelf, NAME_PLAYER);
        CommonGoalCard twoLinesNotOk = goalCardsDeckSingleton.getCommonGoalCardById(PATTERN_ID);
        ArrayList<PointCard> points = new ArrayList<>();
        points.add(new PointCard(PointEnumeration.TWO_POINTS, 2));
        points.add(new PointCard(PointEnumeration.FOUR_POINTS, 4));
        points.add(new PointCard(PointEnumeration.SIX_POINTS, 6));
        points.add(new PointCard(PointEnumeration.EIGHT_POINTS, 8));
        twoLinesNotOk.setPointsCards(points);

        assertEquals(EXPECTED_VALUE, twoLinesNotOk.calculatePoints(player));
    }
}