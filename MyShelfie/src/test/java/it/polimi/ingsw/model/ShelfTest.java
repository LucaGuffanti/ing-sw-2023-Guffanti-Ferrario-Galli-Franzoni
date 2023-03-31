package it.polimi.ingsw.model;

import it.polimi.ingsw.model.cards.ObjectCard;
import it.polimi.ingsw.model.cards.ObjectTypeEnum;
import it.polimi.ingsw.model.player.Shelf;
import it.polimi.ingsw.model.utils.CsvToShelfParser;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class tests the behavior of the Shelf class.
 * @see Shelf
 * @author Luca Guffanti
 */
class ShelfTest {

    /**
     * This method tests che correct calculation of the highestOccupiedCell array by the methods in the Shelf class
     * and whether the shelf is successfully identified as full or not
     */
    @Test
    void isFullOrNotTest() {
        try {
            Shelf shelf = CsvToShelfParser.readLineByLine("src/test/resources/shelfTEST/fullShelf.csv");
            boolean full = shelf.checkFullness();
            int[] expectedHighestCell = {0,0,0,0,0,0};

            for (int i = 0; i < shelf.getLengthInCells(); i++) {
                assertEquals(expectedHighestCell[i], shelf.getHighestOccupiedCell()[i]);
            }
            assertTrue(full);

            shelf = CsvToShelfParser.readLineByLine("src/test/resources/shelfTEST/emptyShelf.csv");

            for (int i = 0; i < shelf.getLengthInCells(); i++) {
                assertEquals(6, shelf.getHighestOccupiedCell()[i]);
            }
            full = shelf.checkFullness();
            assertFalse(full);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method tests if the calculation of the highestOccupiedCell array is correctly performed in a generic
     * situation of operation.
     */
    @Test
    void getHighestOccupiedCellTest() {
        try {
            Shelf shelf = CsvToShelfParser.readLineByLine("src/test/resources/shelfTEST/halfShelf.csv");
            int[] expectedHighestOccupied = {5,4,3,2,1};

            int[] actualHighest = shelf.getHighestOccupiedCell();
            assertNotNull(actualHighest);

            for(int i = 0; i < shelf.getLengthInCells(); i++) {
                assertEquals(expectedHighestOccupied[i], actualHighest[i]);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method tests whether trying to access cells from an empty shelf correctly yields
     * a negative result (the optional is empty).
     * @throws Exception if the read of the shelf from the csv file fails
     */
    @Test
    void getCell_ShouldAlwaysGetEmpty() throws Exception{
        Shelf shelf = CsvToShelfParser.readLineByLine("src/test/resources/shelfTEST/emptyShelf.csv");
        for (int y = 0; y < shelf.getHeightInCells(); y++) {
            for (int x = 0; x < shelf.getLengthInCells(); x++) {
                int finalX = x;
                int finalY = y;
                assertThrows(NoSuchElementException.class, () ->{
                    final int _x = finalX;
                    final int _y = finalY;
                    final ObjectCard objectCard = shelf.getCell(_x, _y).getCellCard().get();
                });
            }
        }
    }

    /**
     * This method tests that trying to access a cell from a full shelf correctly yields the shelf
     * @throws Exception if the read of the shelf from the csv file fails
     */
    @Test
    void getCell_ShouldAlwaysGetSomething() throws Exception {
        Shelf shelf = CsvToShelfParser.readLineByLine("src/test/resources/shelfTEST/fullShelf.csv");
        for (int y = 0; y < shelf.getHeightInCells(); y++) {
            for (int x = 0; x < shelf.getLengthInCells(); x++) {
                int finalX = x;
                int finalY = y;
                assertFalse(shelf.getCell(x,y).getCellCard().isEmpty());
                assertDoesNotThrow(() -> {
                    ObjectCard objectCard = shelf.getCell(finalX, finalY).getCellCard().get();
                });
            }
        }
    }

    /**
     * This method verifies that adding cards to an empty shelf works correctly
     */
    @Test
    void addCardsToColumn_ShouldAddWithNoProblems(){
        Shelf shelf = new Shelf();

        ArrayList<ObjectCard> list = new ArrayList<>();

        for (int col = 0; col < shelf.getLengthInCells(); col++) {
            list = new ArrayList<>();

            list.add(new ObjectCard(ObjectTypeEnum.CAT));
            list.add(new ObjectCard(ObjectTypeEnum.CAT));
            list.add(new ObjectCard(ObjectTypeEnum.CAT));
            list.add(new ObjectCard(ObjectTypeEnum.CAT));
            list.add(new ObjectCard(ObjectTypeEnum.CAT));
            list.add(new ObjectCard(ObjectTypeEnum.CAT));

            assertTrue(shelf.addCardsToColumn(list, col));
            assertEquals(0, shelf.getHighestOccupiedCell()[col]);

        }


    }

    /**
     * This method tests that trying to add an object card to an already full shelf is not permitted
     * @throws Exception if the read of the shelf from the csv file fails
     */
    @Test
    void addCardsToColumn_ShouldBeImpossible() throws Exception {
        Shelf shelf = CsvToShelfParser.readLineByLine("src/test/resources/shelfTEST/fullShelf.csv");

        assertTrue(shelf.isFull());
        ArrayList<ObjectCard> list = new ArrayList<>();
        for (int col = 0; col < shelf.getLengthInCells(); col++) {
            list.add(new ObjectCard(ObjectTypeEnum.TOY));
            boolean res = shelf.addCardsToColumn(list, col);
            assertFalse(res);
        }

    }
}