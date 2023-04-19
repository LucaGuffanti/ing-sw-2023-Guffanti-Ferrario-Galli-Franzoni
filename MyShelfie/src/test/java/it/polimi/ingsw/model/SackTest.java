package it.polimi.ingsw.model;

import it.polimi.ingsw.server.model.Sack;
import it.polimi.ingsw.server.model.cards.ObjectCard;
import it.polimi.ingsw.server.model.cards.ObjectTypeEnum;
import it.polimi.ingsw.server.model.utils.Constants;
import it.polimi.ingsw.server.model.utils.exceptions.EmptySackException;
import org.junit.Test;


import java.util.HashMap;
import java.util.Map;

import static junit.framework.TestCase.*;
import static org.junit.Assert.assertThrows;


/**
 * This class tests the behavior of the Sack class.
 * @see Sack
 * @author Luca Guffanti
 */
public class SackTest{

    /**
     * This method tests that initSackTest can correctly instantiate the list of cards
     */
    @Test
    public void initSackTest() {
        Sack s = new Sack();

        // checking the general number of cards is correct
        assertEquals(Constants.TOTAL_OBJECT_CARDS, s.getCards().size());

        // checking the actual number of cards of each type is correct

        Map<ObjectTypeEnum, Integer> typeToIndex = new HashMap<>();

        typeToIndex.put(ObjectTypeEnum.BOOK, 0);
        typeToIndex.put(ObjectTypeEnum.CAT, 1);
        typeToIndex.put(ObjectTypeEnum.TOY, 2);
        typeToIndex.put(ObjectTypeEnum.FRAME, 3);
        typeToIndex.put(ObjectTypeEnum.PLANT, 4);
        typeToIndex.put(ObjectTypeEnum.TROPHY, 5);

        int[] actualNumberOfCards = new int[6];

        for(int i = 0; i < 6; i++) {
            actualNumberOfCards[i] = 0;
        }

        for(int i = 0; i < Constants.TOTAL_OBJECT_CARDS; i++) {
            actualNumberOfCards[typeToIndex.get(s.getCards().get(i).getType())]++;
        }

        for(int i = 0; i < 6; i++) {
            assertEquals(Constants.OBJECT_CARDS_PER_TYPE, actualNumberOfCards[i]);
        }
    }

    /**
     * This method tests that, in a normal situation (Sack is not empty), there are no problems in retrieving
     * cards from the sack.
     */
    @Test
    public void pickFromSackTest() {
        Sack s = new Sack();

        ObjectCard objectCard;
        for(int i = 0; i < Constants.TOTAL_OBJECT_CARDS; i++) {
            try{
                objectCard = s.pickFromSack();
                assertNotNull(objectCard);
            } catch (EmptySackException e) {
                e.printStackTrace();
            }
        }
        assertEquals(0, s.getCards().size());
    }

    /**
     * This method verifies that when the sack is empty, the correct exception in called.
     */
    @Test
    public void pickFromSackTest_expectEmptySackException() {
        Sack s = new Sack();

        ObjectCard objectCard;
        for(int i = 0; i < Constants.TOTAL_OBJECT_CARDS; i++) {
            try{
                objectCard = s.pickFromSack();
                assertNotNull(objectCard);
            } catch (EmptySackException e) {
                e.printStackTrace();
            }
        }

        EmptySackException emptySackException = assertThrows(EmptySackException.class, s::pickFromSack);
        String expectedMessageFromException = "The sack is empty.";
        assertEquals(expectedMessageFromException, emptySackException.getMessage());
    }
}
