package it.polimi.ingsw.model.cards;

public class CommonPatternRules extends PatternRules{
    private int minNumberOfOccurrences;
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
