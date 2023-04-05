package it.polimi.ingsw.model.cards.goalCards;

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