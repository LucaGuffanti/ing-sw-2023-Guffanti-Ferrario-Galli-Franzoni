package it.polimi.ingsw.model.cards;

public class CommonPatternRules {
    private int minNumberOfOccurrences;
    private boolean subPatternsSameColor;

    public CommonPatternRules(int minNumberOfOccurrences, boolean subPatternsSameColor) {
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
