package it.polimi.ingsw.model.cards;


import java.util.HashSet;
import java.util.Set;

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
    protected SubPattern subPattern;
    public PatternRules(SubPattern subPattern) {
        this.subPattern = subPattern;
    }

    public SubPattern getSubPattern() {
        return subPattern;
    }

    public void setSubPattern(SubPattern subPattern) {
        this.subPattern = subPattern;
    }


}
