package it.polimi.ingsw.model.cards;


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
     * The minimum number of occurrences of the subPattern
     */
    private int minNumberOfOccurrences;

    /**
     * The constraint which indicates if every subPattern
     * should share the same Type/Color
     */
    private boolean subPatternsSameColor;

    public CommonPatternRules(SubPattern subPattern, int minNumberOfOccurrences, boolean subPatternsSameColor) {
        super(subPattern);
        this.minNumberOfOccurrences = minNumberOfOccurrences;
        this.subPatternsSameColor = subPatternsSameColor;
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
}
