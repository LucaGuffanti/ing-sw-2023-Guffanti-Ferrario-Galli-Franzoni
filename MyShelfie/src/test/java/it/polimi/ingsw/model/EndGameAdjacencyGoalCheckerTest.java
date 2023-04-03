package it.polimi.ingsw.model;

import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Shelf;
import it.polimi.ingsw.model.utils.CsvToShelfParser;
import it.polimi.ingsw.model.utils.MatrixUtils;
import org.junit.Test;


import static junit.framework.TestCase.assertEquals;

/**
 * This class implements tests used to check the correct attribution of points
 * related to the presence of groups of adjacent object cards of the same type
 * in the shelf of a player at the end of the game
 * @author Luca Guffanti
 */
public class EndGameAdjacencyGoalCheckerTest {

    /**
     * This method tests the calculation for a full shelf
     */
    @Test
    public void calculateAdjacencyPointsTest_fullShelf() {
        String pathToFile = "src/test/resources/shelfTEST/endGameShelfCheck_full.csv";
        int expectedPoints = 31;
        try {
            Shelf shelf = CsvToShelfParser.convert(pathToFile);
            Player player = new Player(shelf, "erizio");
            //MatrixUtils.printMatrix(shelf.getCells());
            assertEquals(expectedPoints, EndGameAdjacencyGoalChecker.calculateAdjacencyPoints(player));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method tests the calculation for a full shelf, with cells of a single type
     */
    @Test
    public void calculateAdjacencyPointsTest_fullShelf_oneType() {
        String pathToFile = "src/test/resources/shelfTEST/endGameShelfCheck_full_oneType.csv";
        int expectedPoints = 8;
        try {
            Shelf shelf = CsvToShelfParser.convert(pathToFile);
            Player player = new Player(shelf, "erizio");

            assertEquals(expectedPoints, EndGameAdjacencyGoalChecker.calculateAdjacencyPoints(player));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method tests the calculation an empty shelf, expecting a result of 0
     */
    @Test
    public void calculateAdjacentPointsTest_emptyShelf() {
        String pathToFile = "src/test/resources/shelfTEST/endGameShelfCheck_empty.csv";
        int expectedPoints = 0;
        try {
            Shelf shelf = CsvToShelfParser.convert(pathToFile);
            Player player = new Player(shelf, "erizio");

            assertEquals(expectedPoints, EndGameAdjacencyGoalChecker.calculateAdjacencyPoints(player));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method tests the calculation a generic non-full shelf: the test
     * regards the behavior of the algorithm when some cells of the shelf are not full
     */
    @Test
    public void calculateAdjacentPointsTest_notFullShelf() {
        String pathToFile = "src/test/resources/shelfTEST/endGameShelfCheck_notFull.csv";
        int expectedPoints = 18;
        try {
            Shelf shelf = CsvToShelfParser.convert(pathToFile);
            Player player = new Player(shelf, "erizio");

            assertEquals(expectedPoints, EndGameAdjacencyGoalChecker.calculateAdjacencyPoints(player));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
