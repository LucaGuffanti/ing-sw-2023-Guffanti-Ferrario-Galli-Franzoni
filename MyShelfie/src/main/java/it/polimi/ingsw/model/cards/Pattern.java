package it.polimi.ingsw.model.cards;

import java.util.HashSet;
import java.util.Set;

/**
 * A pattern object describes a sub-matrix of the shelf which is used as
 * a mask during the pattern matching. The frame of the patterns are
 * dimensioned to host the covered cells.<br><br>
 *
 * Graphical representation of a diagonal pattern:
 *
 * <table>
 *     <tr><td>O</td><td>X</td><td>X</td><td>X</td></tr>
 *     <tr><td>X</td><td>O</td><td>X</td><td>X</td></tr>
 *     <tr><td>X</td><td>X</td><td>O</td><td>X</td></tr>
 *     <tr><td>X</td><td>X</td><td>X</td><td>O</td></tr>

 * </table>
 * height = 4<br>lenght= 4<br>coveredCells=[[0,0],[1,1],[2,2],[3,3]]
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
     * The minimum height of the frame to contain the covered cells.
     */
    private int height;
    /**
     * The minimum length of the frame to contain the covered cells.
     */
    private int length;

    /**
     * A set of PatternCells which identifies the covered cells
     * to detect in a shelf portion. Their coordinates are set relatively
     * to the frame dimensions. For example: <br>
     *

     */
    private Set<PatternCell> coveredCells;

    /**
     * The max number of different ObjectType that the pattern
     * should contain.
     */
    private int maxDifferentTypes;

    /**
     * The min number of different ObjectType that the pattern
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

    public Pattern (Pattern p) {
        this.height = p.getHeight();
        this.length = p.getLength();
        this.coveredCells = new HashSet<>(p.getCoveredCells());
        this.maxDifferentTypes = p.getMaxDifferentTypes();
        this.minDifferentTypes = p.getMinDifferentTypes();
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
