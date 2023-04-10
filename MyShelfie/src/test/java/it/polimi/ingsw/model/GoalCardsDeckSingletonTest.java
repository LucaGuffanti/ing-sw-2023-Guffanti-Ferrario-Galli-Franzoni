package it.polimi.ingsw.model;

import it.polimi.ingsw.model.cards.goalCards.CommonGoalCard;
import it.polimi.ingsw.model.cards.goalCards.PersonalGoalCard;
import it.polimi.ingsw.model.utils.exceptions.WrongNumberOfPlayersException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class contains methods that test the behaviour of the deck singleton.
 * @author Luca Guffanti
 */
class GoalCardsDeckSingletonTest {

    /**
     * This method verifies that there are no problems in getting the instance of the singleton,
     * and that at the beginning the singleton isn't instantiated.
     */
    @Test
    void getInstance() {
        GoalCardsDeckSingleton g1 = null;
        assertNull(g1);
        g1 = GoalCardsDeckSingleton.getInstance();
        assertNotNull(g1);
        GoalCardsDeckSingleton g2 = GoalCardsDeckSingleton.getInstance();
        assertNotNull(g2);
    }

    @Test
    void pickPersonalGoals() throws WrongNumberOfPlayersException {
        GoalCardsDeckSingleton g = GoalCardsDeckSingleton.getInstance();
        ArrayList<PersonalGoalCard> cards;

        cards = g.pickPersonalGoals(2);
        assertEquals(2, cards.size());

        cards = g.pickPersonalGoals(3);
        assertEquals(3, cards.size());

        cards = g.pickPersonalGoals(4);
        assertEquals(4, cards.size());

    }

    /**
     * This method verifies that two commonGoals can be correctly and randomly accessed
     * when "pickTwoCommonGoals" is called
     */
    @Test
    void pickTwoCommonGoals() {
        GoalCardsDeckSingleton g = GoalCardsDeckSingleton.getInstance();
        int num_of_tests = 1500;
        for (int i = 0; i < num_of_tests; i++) {
            ArrayList<CommonGoalCard> cards = g.pickTwoCommonGoals();
            assertEquals(2, cards.size());

            for (CommonGoalCard card : cards) {
                assertNotNull(card);
                System.out.println(card.getId());
                assertNull(card.getPointsCards());
            }
        }

    }
}