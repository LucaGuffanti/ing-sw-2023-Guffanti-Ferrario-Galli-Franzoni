package it.polimi.ingsw.model.cards;

import java.util.Set;

/**
 * A pattern is a "subMatrix"-like object of the shelf which is used as
 * a mask during the pattern matching. For optimization purposes
 * only coveredCells are saved, while the empty cells are not considered.
 *
 * @author Daniele Ferrario
 */
public class Pattern {

    /**
     *
     *  @public invariant
     *  \forall PatternCell cell; cell.getX() < height && cell.getY < length;
     *
     */

    /**
     * The minimum height of the frame for containing the covered cells.
     */
    private int height;
    /**
     * The minimum length of the frame for containing the covered cells.
     */
    private int length;

    /**
     * A set of SubPatternCells which identifies the pattern
     * in the mask frame is used instead of a matrix.
     */
    private Set<PatternCell> coveredCells;

    /**
     * The max number of different types that a pattern
     * should contain.
     */
    private int maxDifferentTypes;

    /**
     * The min number of different types that a pattern
     * should contain.
     */
    private int minDifferentTypes;

    public Pattern(int height, int length, Set<PatternCell> coveredCells, int minDifferentTypes, int maxDifferentTypes) {
        this.height = height;
        this.length = length;
        this.coveredCells = coveredCells;
        this.maxDifferentTypes = maxDifferentTypes;
        this.minDifferentTypes = minDifferentTypes;

    }

    public int getMinDifferentTypes() {
        return minDifferentTypes;
    }

    public void setMinDifferentTypes(int minDifferentTypes) {
        this.minDifferentTypes = minDifferentTypes;
    }


    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public Set<PatternCell> getCoveredCells() {
        return coveredCells;
    }

    public void setCoveredCells(Set<PatternCell> coveredCells) {
        this.coveredCells = coveredCells;
    }

    public int getMaxDifferentTypes() {
        return maxDifferentTypes;
    }

    public void setMaxDifferentTypes(int maxDifferentTypes) {
        this.maxDifferentTypes = maxDifferentTypes;
    }
}
