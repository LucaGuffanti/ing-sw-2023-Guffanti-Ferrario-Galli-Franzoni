package it.polimi.ingsw.model.cards;

import java.util.Set;

/**
 * A subPattern is a "subMatrix"-like object of the shelf which is used as
 * a mask during the pattern matching. For optimization purposes
 * only coveredCells are saved, while the empty cells are not considered.
 *
 * @author Daniele Ferrario
 */
public class SubPattern {

    /**
     *
     *  @public invariant
     *  \forall SubPatternCell cell; cell.getX() < height && cell.getY < length;
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
     * A set of SubPatternCells which identifies the subPattern
     * in the mask frame is used instead of a matrix.
     */
    private Set<SubPatternCell> coveredCells;

    /**
     * The max number of different types that a subPattern
     * should contain.
     */
    private int maxDifferentTypes;

    /**
     * The min number of different types that a subPattern
     * should contain.
     */
    private int minDifferentTypes;

    public SubPattern(int height, int length, Set<SubPatternCell> coveredCells, int minDifferentTypes, int maxDifferentTypes) {
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

    public Set<SubPatternCell> getCoveredCells() {
        return coveredCells;
    }

    public void setCoveredCells(Set<SubPatternCell> coveredCells) {
        this.coveredCells = coveredCells;
    }

    public int getMaxDifferentTypes() {
        return maxDifferentTypes;
    }

    public void setMaxDifferentTypes(int maxDifferentTypes) {
        this.maxDifferentTypes = maxDifferentTypes;
    }
}
