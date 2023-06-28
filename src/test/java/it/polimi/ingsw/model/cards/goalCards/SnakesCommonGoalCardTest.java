package it.polimi.ingsw.model.cards.goalCards;

import it.polimi.ingsw.server.model.cards.PointCard;
import it.polimi.ingsw.server.model.cards.PointEnumeration;
import it.polimi.ingsw.server.model.cards.goalCards.CommonGoalCard;
import it.polimi.ingsw.server.model.cards.goalCards.SnakesCommonGoalCard;
import it.polimi.ingsw.server.model.player.Player;
import it.polimi.ingsw.server.model.player.Shelf;
import it.polimi.ingsw.server.model.utils.CsvToShelfParser;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class SnakesCommonGoalCardTest {

    @Test
    void returnEqualCard() {
        CommonGoalCard snake = new SnakesCommonGoalCard("4");
        CommonGoalCard snake2 = (CommonGoalCard) snake.returnEqualCard();
        assertNotNull(snake2);
    }

    @Test
    public void calculatePointsTest() {
        CommonGoalCard snake = new SnakesCommonGoalCard("4");
        ArrayList<PointCard> points = new ArrayList<>();
        points.add(new PointCard(PointEnumeration.EIGHT_POINTS, 8));
        snake.setPointsCards(points);

        Player p = new Player("test");
        try {
            p.setShelf(CsvToShelfParser.convert("src/test/resources/shelfTEST/fourGroupsFourAdjacent_Invalid1.csv"));
            assertEquals(0, snake.calculatePoints(p));
            p.setShelf(CsvToShelfParser.convert("src/test/resources/shelfTEST/fourGroupsFourAdjacent_Invalid2.csv"));
            assertEquals(0, snake.calculatePoints(p));
            p.setShelf(CsvToShelfParser.convert("src/test/resources/shelfTEST/fourGroupsFourAdjacent.csv"));
            assertEquals(8, snake.calculatePoints(p));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}