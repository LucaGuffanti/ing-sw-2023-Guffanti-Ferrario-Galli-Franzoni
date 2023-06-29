package it.polimi.ingsw.model;

import it.polimi.ingsw.server.model.GoalCardsDeckSingleton;
import it.polimi.ingsw.server.model.cards.goalCards.CommonGoalCard;
import it.polimi.ingsw.server.model.cards.goalCards.PersonalGoalCard;
import it.polimi.ingsw.server.model.utils.JsonFixedPatternGoalCardsParser;
import it.polimi.ingsw.server.model.utils.exceptions.WrongNumberOfPlayersException;
import it.polimi.ingsw.server.model.utils.exceptions.WrongPointCardsValueGivenException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
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

    /**
     * Checks the pick of the requested cards.
     * @throws WrongPointCardsValueGivenException
     * @throws WrongNumberOfPlayersException
     * @throws IOException
     */
    @Test
    void getPersonalCardByIds() {
        for (int i = 1; i <= 11; i++) {
            String idStr = Integer.toString(i);
            assertEquals(GoalCardsDeckSingleton.getInstance().getPersonalGoalCardById(idStr).getId(), idStr);
        }

    }

    /**
     * Check the correct exception throw on incorrect inputs.
     */
    @Test
    void pickPersonalGoalsWrongPlayers_expectedInvalid()  {
        Throwable exception = assertThrows(WrongNumberOfPlayersException.class, () -> GoalCardsDeckSingleton.getInstance().pickPersonalGoals(5));
        assertEquals("Got " + 5 + " players. It should be between 2 and 4", exception.getMessage());
    }
}