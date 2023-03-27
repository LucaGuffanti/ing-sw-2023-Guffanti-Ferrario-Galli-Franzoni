package it.polimi.ingsw.model.cards;


import java.util.HashSet;
import java.util.Set;

/**
 * For CommonGoalCards, a rule used to validate a Pattern is composed by:
 *      - a SubPattern
 *      - the minimum number of occurrences of the subPattern
 *      - the constraint which indicates if every subPattern
 *        should share the same Type/Color
 *
 * @author Daniele Ferrario
 */
public class CommonPatternRules extends PatternRules{

    /**
     * The original subPattern to detect, rotated 90° degrees counter-clockwise.
     * The rotation is useful as the pattern must be detected both horizontally and vertically.
     * As some characteristics may change (e.g. length and height of the subpattern) it's better
     * to instantiate a new object, strictly related to the first.
     */
    private SubPattern rotatedSubPattern;

    /**
     * The minimum number of occurrences of the subPattern
     */
    private int minNumberOfOccurrences;

    /**
     * The constraint which indicates if every subPattern
     * should share the same Type/Color
     */
    private boolean subPatternsSameColor;

    /**
     * The flag which indicated that the shelf should also be analyzed
     * for rotatedSubPatterns
     */
    private boolean shouldRotate;

    /**
     * The flag which indicates if the subPatterns can be adjacent
     */
    private boolean admitsAdjacency;


    public CommonPatternRules(SubPattern subPattern, int minNumberOfOccurrences, boolean shouldRotate, boolean admitsAdjacency, boolean subPatternsSameColor) {
        super(subPattern);
        this.minNumberOfOccurrences = minNumberOfOccurrences;
        this.shouldRotate = shouldRotate;
        this.admitsAdjacency = admitsAdjacency;
        this.subPatternsSameColor = subPatternsSameColor;
        this.rotatedSubPattern = rotateSubPattern(subPattern);
    }

    /**
     * Performs a 90° counter-clockwise rotation of the cells of the subpattern, adjusting some parameters (length, height).
     * If the original pattern is radiallySymmetric then the resultant pattern is equal to the first one,
     * and it won't be checked for matches.
     *
     * @param subPattern the pattern to be rotated
     * @return the rotated subPattern, of a copy of the original if it's radiallySymmetric
     * @author Luca Guffanti
     */
    private SubPattern rotateSubPattern(SubPattern subPattern) {

        int rotatedMinDifferentTypes = subPattern.getMinDifferentTypes();
        int rotatedMaxDifferentTypes = subPattern.getMaxDifferentTypes();

        int rotatedHeight;
        int rotatedLength;

        Set<SubPatternCell> rotatedCells;

        if (!this.shouldRotate) {
            rotatedHeight = subPattern.getHeight();
            rotatedLength = subPattern.getLength();
            rotatedCells = new HashSet<>(subPattern.getCoveredCells());
        } else {
            rotatedHeight = subPattern.getLength();
            rotatedLength = subPattern.getHeight();

            rotatedCells = new HashSet<>();

            // performs the rotation. NEEDS STRICT TESTING
            int originalLength = subPattern.getLength();
            for (SubPatternCell cell : subPattern.getCoveredCells()) {
                rotatedCells.add(
                        new SubPatternCell(
                                cell.getY(),
                                originalLength - 1 - cell.getX(),
                                cell.getAdmittedType()
                        )
                );
            }
        }

        return new SubPattern(
                rotatedHeight,
                rotatedLength,
                rotatedCells,
                rotatedMinDifferentTypes,
                rotatedMaxDifferentTypes
        );
    }

    public boolean getShouldRotate() {
        return shouldRotate;
    }

    public boolean getAdmitsAdjacency() {
        return admitsAdjacency;
    }

    public int getMinNumberOfOccurrences() {
        return minNumberOfOccurrences;
    }

    public void setMinNumberOfOccurrences(int minNumberOfOccurrences) {
        this.minNumberOfOccurrences = minNumberOfOccurrences;
    }

    public boolean isSubPatternsSameColor() {
        return subPatternsSameColor;
    }

    public void setSubPatternsSameColor(boolean subPatternsSameColor) {
        this.subPatternsSameColor = subPatternsSameColor;
    }

    public SubPattern getRotatedSubPattern() {
        return rotatedSubPattern;
    }
}
