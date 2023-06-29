package it.polimi.ingsw.model;

import it.polimi.ingsw.server.model.GoalCardsDeckSingleton;
import it.polimi.ingsw.server.model.cards.CardBuilder;
import it.polimi.ingsw.server.model.cards.goalCards.FixedPatternCommonGoalCard;
import it.polimi.ingsw.server.model.cards.Pattern;
import it.polimi.ingsw.server.model.cards.PatternCell;
import it.polimi.ingsw.server.model.utils.exceptions.WrongNumberOfPlayersException;
import it.polimi.ingsw.server.model.utils.exceptions.WrongPointCardsValueGivenException;
import org.junit.Test;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.*;


/**
 * This class tests the process of rotation of Pattern in FixedPatternCommonGoalCard
 * @author Luca Guffanti
 * @see FixedPatternCommonGoalCard
 */
public class PatternRotationTests {

    final private static GoalCardsDeckSingleton goalCardsDeckSingleton = GoalCardsDeckSingleton.getInstance();

    /**
     * This method tests the rotation of a pattern when the pattern is symmetric.
     * In that case one should expect the first pattern to be simply copied.
     */
    @Test
    public void rotateSubPatternTest_whenRadiallySymmetric() {

        final String GOAL_CARD_ID = "6";

        FixedPatternCommonGoalCard card = (FixedPatternCommonGoalCard) goalCardsDeckSingleton.getCommonGoalCardById(GOAL_CARD_ID);

        assertNotNull(card);

        // the two pattern should be equal. the following code will check that
        assertEquals(card.getPattern().getLength(), card.getRotatedPattern().getLength());
        assertEquals(card.getPattern().getHeight(), card.getRotatedPattern().getHeight());
        assertEquals(card.getPattern().getMaxDifferentTypes(), card.getRotatedPattern().getMaxDifferentTypes());
        assertEquals(card.getPattern().getMinDifferentTypes(), card.getRotatedPattern().getMinDifferentTypes());
        assertEquals(card.getPattern().getCoveredCells(), card.getRotatedPattern().getCoveredCells());
    }

    /**
     * This method tests the rotation of a pattern when the pattern is asymmetric.
     * In that case one should expect the resultant pattern to be the 90 degrees counter-clockwise rotation of
     * the first. length and height should change subsequently.
     */
    @Test
    public void rotateSubPatternTest_whenNotRadiallySymmetric_equalLengthAndHeight() {

        final String GOAL_CARD_ID = "1";

        FixedPatternCommonGoalCard card = (FixedPatternCommonGoalCard) goalCardsDeckSingleton.getCommonGoalCardById(GOAL_CARD_ID);

        assertNotNull(card);

        // the two patterns must not be equal. the following code will check that
        // the height of one is the length of the other and vice-versa
        // the number if max and min different types must be equal
        // the covered cells of the rotated sub pattern should be as follows
        //  cell1: (0,0) -> (0,4)
        //  cell2: (1,1) -> (1,3)
        //  cell3: (2,2) -> (2,2)
        //  cell4: (3,3) -> (3,1)
        //  cell4: (4,4) -> (4,0)
        Set<PatternCell> rotatedCoveredCells = new HashSet<>();
        rotatedCoveredCells.add(new PatternCell(0,4, Optional.empty()));
        rotatedCoveredCells.add(new PatternCell(1,3, Optional.empty()));
        rotatedCoveredCells.add(new PatternCell(2,2, Optional.empty()));
        rotatedCoveredCells.add(new PatternCell(3,1, Optional.empty()));
        rotatedCoveredCells.add(new PatternCell(4,0, Optional.empty()));

        // the two pattern should be equal. the following code will check that
        assertEquals(card.getPattern().getLength(), card.getRotatedPattern().getLength());
        assertEquals(card.getPattern().getHeight(), card.getRotatedPattern().getHeight());
        assertEquals(card.getPattern().getMaxDifferentTypes(), card.getRotatedPattern().getMaxDifferentTypes());
        assertEquals(card.getPattern().getMinDifferentTypes(), card.getRotatedPattern().getMinDifferentTypes());

        assertEquals(rotatedCoveredCells, card.getRotatedPattern().getCoveredCells());
    }

    /**
     * This method tests the rotation of a pattern when the pattern is asymmetric.
     * In that case one should expect the resultant pattern to be the 90 degrees counter-clockwise rotation of
     * the first. length and height should change subsequently.
     */
    @Test
    public void rotateSubPatternTest_whenNotRadiallySymmetric_differentLengthAndHeight() throws WrongPointCardsValueGivenException, WrongNumberOfPlayersException {
        Set<PatternCell> coveredCells = new HashSet<>();
        coveredCells.add(new PatternCell(5,0, Optional.empty()));
        coveredCells.add(new PatternCell(4,3, Optional.empty()));
        coveredCells.add(new PatternCell(2,4, Optional.empty()));
        coveredCells.add(new PatternCell(1,2, Optional.empty()));
        coveredCells.add(new PatternCell(3,3, Optional.empty()));

        Pattern pattern = new Pattern(5,6, coveredCells, 1, 1);

        assertNotNull(pattern);
        FixedPatternCommonGoalCard card = null;
        card = new FixedPatternCommonGoalCard("test", CardBuilder.generatePointsCards(4), pattern, 1,true,false,false);


        // the two patterns must not be equal. the following code will check that
        // the height of one is the length of the other and vice-versa
        // the number if max and min different types must be equal
        // the covered cells of the rotated sub pattern should be as follows
        //  cell1: (5,0) -> (0,0)
        //  cell2: (4,3) -> (3,1)
        //  cell3: (2,4) -> (4,3)
        //  cell4: (1,2) -> (2,4)
        //  cell5: (3,3) -> (3,2)

        Set<PatternCell> rotatedCoveredCells = new HashSet<>();
        rotatedCoveredCells.add(new PatternCell(0,0, Optional.empty()));
        rotatedCoveredCells.add(new PatternCell(3,1, Optional.empty()));
        rotatedCoveredCells.add(new PatternCell(4,3, Optional.empty()));
        rotatedCoveredCells.add(new PatternCell(2,4, Optional.empty()));
        rotatedCoveredCells.add(new PatternCell(3,2, Optional.empty()));


        // the two pattern should be equal. the following code will check that
        assertEquals(card.getPattern().getLength(), card.getRotatedPattern().getHeight());
        assertEquals(card.getPattern().getHeight(), card.getRotatedPattern().getLength());
        assertEquals(card.getPattern().getMaxDifferentTypes(), card.getRotatedPattern().getMaxDifferentTypes());
        assertEquals(card.getPattern().getMinDifferentTypes(), card.getRotatedPattern().getMinDifferentTypes());

        assertEquals(rotatedCoveredCells, card.getRotatedPattern().getCoveredCells());
    }
}
