package it.polimi.ingsw.model;

import it.polimi.ingsw.model.cards.CommonPatternRules;
import it.polimi.ingsw.model.cards.PatternRules;
import it.polimi.ingsw.model.cards.SubPattern;
import it.polimi.ingsw.model.cards.SubPatternCell;
import org.junit.Test;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.*;


/**
 * This class tests the patternRules class
 * @author Luca Guffanti
 * @see PatternRules
 */
public class PatternRulesTest {

    /**
     * This method tests the rotation of a subPattern when the subPattern is symmetric.
     * In that case one should expect the first subPattern to be simply copied.
     */
    @Test
    public void rotateSubPatternTest_whenRadiallySymmetric() {

        // building the cells of the subpattern
        Set<SubPatternCell> coveredCells = new HashSet<>();
        coveredCells.add(new SubPatternCell(0,0, Optional.empty()));
        coveredCells.add(new SubPatternCell(0,1, Optional.empty()));
        coveredCells.add(new SubPatternCell(1,0, Optional.empty()));
        coveredCells.add(new SubPatternCell(1,1, Optional.empty()));

        SubPattern subPattern = new SubPattern(2,2, coveredCells, 1, 1, true);

        assertNotNull(subPattern);

        PatternRules pr = new PatternRules(subPattern);

        assertNotNull(pr);

        // the two pattern should be equal. the following code will check that
        assertEquals(pr.getSubPattern().getLength(), pr.getRotatedSubPattern().getLength());
        assertEquals(pr.getSubPattern().getHeight(), pr.getRotatedSubPattern().getHeight());
        assertEquals(pr.getSubPattern().getMaxDifferentTypes(), pr.getRotatedSubPattern().getMaxDifferentTypes());
        assertEquals(pr.getSubPattern().getMinDifferentTypes(), pr.getRotatedSubPattern().getMinDifferentTypes());
        assertEquals(pr.getSubPattern().isRadiallySymmetric(), pr.getRotatedSubPattern().isRadiallySymmetric());
        assertEquals(pr.getSubPattern().getCoveredCells(), pr.getRotatedSubPattern().getCoveredCells());
    }

    /**
     * This method tests the rotation of a subPattern when the subPattern is asymmetric.
     * In that case one should expect the resultant subPattern to be the 90 degrees counter-clockwise rotation of
     * the first. length and height should change subsequently.
     */
    @Test
    public void rotateSubPatternTest_whenNotRadiallySymmetric_equalLengthAndHeight() {
        Set<SubPatternCell> coveredCells = new HashSet<>();
        coveredCells.add(new SubPatternCell(0,0, Optional.empty()));
        coveredCells.add(new SubPatternCell(1,1, Optional.empty()));
        coveredCells.add(new SubPatternCell(2,2, Optional.empty()));
        coveredCells.add(new SubPatternCell(3,3, Optional.empty()));


        SubPattern subPattern = new SubPattern(4,4, coveredCells, 1, 1, false);

        assertNotNull(subPattern);

        PatternRules pr = new PatternRules(subPattern);

        // the two patterns must not be equal. the following code will check that
        // the height of one is the length of the other and vice-versa
        // the number if max and min different types must be equal
        // the covered cells of the rotated sub pattern should be as follows
        //  cell1: (0,0) -> (3,0)
        //  cell2: (1,1) -> (2,1)
        //  cell3: (2,2) -> (1,2)
        //  cell4: (3,3) -> (0,3)

        Set<SubPatternCell> rotatedCoveredCells = new HashSet<>();
        rotatedCoveredCells.add(new SubPatternCell(3,0, Optional.empty()));
        rotatedCoveredCells.add(new SubPatternCell(2,1, Optional.empty()));
        rotatedCoveredCells.add(new SubPatternCell(1,2, Optional.empty()));
        rotatedCoveredCells.add(new SubPatternCell(0,3, Optional.empty()));

        assertEquals(pr.getSubPattern().getLength(), pr.getRotatedSubPattern().getHeight());
        assertEquals(pr.getSubPattern().getHeight(), pr.getRotatedSubPattern().getLength());
        assertEquals(pr.getSubPattern().getMaxDifferentTypes(), pr.getRotatedSubPattern().getMaxDifferentTypes());
        assertEquals(pr.getSubPattern().getMinDifferentTypes(), pr.getRotatedSubPattern().getMinDifferentTypes());
        assertEquals(pr.getSubPattern().isRadiallySymmetric(), pr.getRotatedSubPattern().isRadiallySymmetric());

        assertEquals(rotatedCoveredCells, pr.getRotatedSubPattern().getCoveredCells());
    }

    /**
     * This method tests the rotation of a subPattern when the subPattern is asymmetric.
     * In that case one should expect the resultant subPattern to be the 90 degrees counter-clockwise rotation of
     * the first. length and height should change subsequently.
     */
    @Test
    public void rotateSubPatternTest_whenNotRadiallySymmetric_differentLengthAndHeight() {
        Set<SubPatternCell> coveredCells = new HashSet<>();
        coveredCells.add(new SubPatternCell(0,5, Optional.empty()));
        coveredCells.add(new SubPatternCell(4,3, Optional.empty()));
        coveredCells.add(new SubPatternCell(2,4, Optional.empty()));
        coveredCells.add(new SubPatternCell(1,2, Optional.empty()));
        coveredCells.add(new SubPatternCell(3,3, Optional.empty()));

        SubPattern subPattern = new SubPattern(5,6, coveredCells, 1, 1, false);

        assertNotNull(subPattern);

        PatternRules pr = new PatternRules(subPattern);

        // the two patterns must not be equal. the following code will check that
        // the height of one is the length of the other and vice-versa
        // the number if max and min different types must be equal
        // the covered cells of the rotated sub pattern should be as follows
        //  cell1: (0,5) -> (0,0)
        //  cell2: (4,3) -> (2,4)
        //  cell3: (2,4) -> (1,2)
        //  cell4: (1,2) -> (3,1)
        //  cell5: (3,3) -> (2,3)

        Set<SubPatternCell> rotatedCoveredCells = new HashSet<>();
        rotatedCoveredCells.add(new SubPatternCell(0,0, Optional.empty()));
        rotatedCoveredCells.add(new SubPatternCell(2,4, Optional.empty()));
        rotatedCoveredCells.add(new SubPatternCell(1,2, Optional.empty()));
        rotatedCoveredCells.add(new SubPatternCell(3,1, Optional.empty()));
        rotatedCoveredCells.add(new SubPatternCell(2,3, Optional.empty()));


        assertEquals(pr.getSubPattern().getLength(), pr.getRotatedSubPattern().getHeight());
        assertEquals(pr.getSubPattern().getHeight(), pr.getRotatedSubPattern().getLength());
        assertEquals(pr.getSubPattern().getMaxDifferentTypes(), pr.getRotatedSubPattern().getMaxDifferentTypes());
        assertEquals(pr.getSubPattern().getMinDifferentTypes(), pr.getRotatedSubPattern().getMinDifferentTypes());
        assertEquals(pr.getSubPattern().isRadiallySymmetric(), pr.getRotatedSubPattern().isRadiallySymmetric());

        assertEquals(rotatedCoveredCells, pr.getRotatedSubPattern().getCoveredCells());
    }
}
