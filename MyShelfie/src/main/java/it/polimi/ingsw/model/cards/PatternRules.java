package it.polimi.ingsw.model.cards;

public class PatternRules {
    private SubPattern positionalPattern;

    public PatternRules( SubPattern positionalPattern){
        this.positionalPattern = positionalPattern;
    }

    public SubPattern getPositionalPattern() {
        return positionalPattern;
    }

    public void setPositionalPattern(SubPattern positionalPattern) {
        this.positionalPattern = positionalPattern;
    }
}
