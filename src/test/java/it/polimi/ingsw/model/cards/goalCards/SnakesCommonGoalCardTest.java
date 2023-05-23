package it.polimi.ingsw.model.cards.goalCards;

import it.polimi.ingsw.server.model.cards.goalCards.CommonGoalCard;
import it.polimi.ingsw.server.model.cards.goalCards.SnakesCommonGoalCard;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SnakesCommonGoalCardTest {

    @Test
    void returnEqualCard() {
        CommonGoalCard snake = new SnakesCommonGoalCard("4");
        CommonGoalCard snake2 = (CommonGoalCard) snake.returnEqualCard();
        assertNotNull(snake2);
    }
}