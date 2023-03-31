package it.polimi.ingsw.model;

import it.polimi.ingsw.model.cards.ObjectCard;
import it.polimi.ingsw.model.cards.ObjectTypeEnum;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Shelf;
import it.polimi.ingsw.model.utils.CsvToShelfParser;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


/**
 * This class tests the correct behavior of the player class
 * @author Luca Guffanti
 * @see Player
 */
class PlayerTest {

    /**
     *
     * @throws Exception if the read from the csv file fails
     */
    @Test
    void addCardsToShelfTest_GivenFullShelf() throws Exception {
        Shelf shelf = CsvToShelfParser.readLineByLine("src/test/resources/shelfTEST/fullShelf.csv");
        Player player = new Player(shelf, "Luca");

        List<ObjectCard> list;

        for (int col = 0; col < shelf.getLengthInCells(); col++) {
            list = new ArrayList<>();
            list.add(new ObjectCard(ObjectTypeEnum.TROPHY));
            assertFalse(player.addCardsToShelf(list, col));
            assertTrue(player.getAchievements().isCompletedShelf());
        }
    }

    /**
     * This method tests that a player can't add an objectCard
     * @throws Exception if the read from the csv file fails
     */
    @Test
    void addCardsToShelfTest_GivenEmptyShelf() throws Exception {
        Shelf shelf = CsvToShelfParser.readLineByLine("src/test/resources/shelfTEST/emptyShelf.csv");
        Player player = new Player(shelf, "Luca");

        List<ObjectCard> list;

        for (int col = 0; col < shelf.getLengthInCells(); col++) {
            list = new ArrayList<>();
            list.add(new ObjectCard(ObjectTypeEnum.TROPHY));
            list.add(new ObjectCard(ObjectTypeEnum.TROPHY));
            list.add(new ObjectCard(ObjectTypeEnum.TROPHY));
            list.add(new ObjectCard(ObjectTypeEnum.TROPHY));
            list.add(new ObjectCard(ObjectTypeEnum.TROPHY));
            list.add(new ObjectCard(ObjectTypeEnum.TROPHY));
            assertTrue(player.addCardsToShelf(list, col));
        }
        assertTrue(player.getAchievements().isCompletedShelf());
    }
}