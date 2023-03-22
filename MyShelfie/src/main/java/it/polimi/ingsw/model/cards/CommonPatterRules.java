package it.polimi.ingsw.model.cards;

public class CommonPatterRules {
    private int minNumberOfOccurence;
    private boolean subPatternsSameColor;
    private int NumberOfOccurence;

    public CommonPatterRules(int minNumberOfOccurence, boolean subPatternsSameColor, int numberOfOccurence) {
        this.minNumberOfOccurence = minNumberOfOccurence;
        this.subPatternsSameColor = subPatternsSameColor;
        this.NumberOfOccurence = numberOfOccurence;
    }

    public int getMinNumberOfOccurence() {
        return minNumberOfOccurence;
    }

    public void setMinNumberOfOccurence(int minNumberOfOccurence) {
        this.minNumberOfOccurence = minNumberOfOccurence;
    }

    public boolean isSubPatternsSameColor() {
        return subPatternsSameColor;
    }

    public void setSubPatternsSameColor(boolean subPatternsSameColor) {
        this.subPatternsSameColor = subPatternsSameColor;
    }

    public int getNumberOfOccurence() {
        return NumberOfOccurence;
    }

    public void setNumberOfOccurence(int numberOfOccurence) {
        NumberOfOccurence = numberOfOccurence;
    }
}
