package it.polimi.ingsw.model;

import it.polimi.ingsw.model.cards.goalCards.CommonGoalCard;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Shelf;
import it.polimi.ingsw.model.utils.CsvToShelfParser;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

/**
 * These methods test the behaviour of the CommonGoalCard pattern matching algorithm
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

        assertEquals(EXPECTED_VALUE, exampleCard.calculatePoints(player));
    }


}
