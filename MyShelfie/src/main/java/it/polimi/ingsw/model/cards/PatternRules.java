package it.polimi.ingsw.model.cards;


import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * A PatternRules class indicates the rules that should
 * be followed when searching for a pattern in the
 * player shelf.
 *
 * @author Daniele Ferrario
 */
public class PatternRules {
    /**
     * The subPattern to detect
     */
    private SubPattern subPattern;

    /**
     * The original subPattern to detect, rotated 90° degrees counter-clockwise.
     * The rotation is useful as the pattern must be detected both horizontally and vertically.
     * As some characteristics may change (e.g. length and height of the subpattern) it's better
     * to instantiate a new object, strictly related to the first.
     */
    private SubPattern rotatedSubPattern;

    public PatternRules(SubPattern subPattern){
        this.subPattern = subPattern;
        this.rotatedSubPattern = rotateSubPattern(subPattern);
    }

    public SubPattern getSubPattern() {
        return subPattern;
    }

    public void setSubPattern(SubPattern subPattern) {
        this.subPattern = subPattern;
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

        boolean rotatedSymmetry = subPattern.isRadiallySymmetric();
        int rotatedMinDifferentTypes = subPattern.getMinDifferentTypes();
        int rotatedMaxDifferentTypes = subPattern.getMaxDifferentTypes();

        int rotatedHeight;
        int rotatedLength;

        Set<SubPatternCell> rotatedCells;

        if (rotatedSymmetry) {
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
                        originalLength - 1 - cell.getY(),
                        cell.getX(),
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
                rotatedMaxDifferentTypes,
                rotatedSymmetry
        );
    }

    public SubPattern getRotatedSubPattern() {
        return rotatedSubPattern;
    }
}
