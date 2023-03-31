package it.polimi.ingsw.model.cards.goalCards;

import it.polimi.ingsw.model.player.Player;

/**
 * A Goal Card contains a pattern which players aim to
 * reproduce in their shelf in order to gain points.
 *
 * @author Daniele ferrario
 */
public abstract class GoalCard {
    protected String id;

    public GoalCard(String id) {
        this.id = id;
    }

    /**
     * This method will check for the number of occurrences of the pattern
     * in the player's shelf.
     *
     * @param player
     * @return number of occurrences of the pattern in the player's shelf.
     */
    protected abstract int checkPattern(Player player);

    /**
     *
     * @return a copy of the goalCard which is "this"
     */
    public abstract GoalCard returnEqualCard();
}
