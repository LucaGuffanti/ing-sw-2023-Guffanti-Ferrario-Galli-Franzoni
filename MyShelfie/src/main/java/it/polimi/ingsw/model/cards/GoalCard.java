package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.player.Player;

public abstract class GoalCard {
    protected PatternRules patternRules;
    protected String id;

    public GoalCard(String id, PatternRules patternRules) {
        this.patternRules = patternRules;
        this.id = id;
    }

    protected abstract int checkPattern(Player player);

}
