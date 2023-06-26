package it.polimi.ingsw.server.model.cards.goalCards;

import it.polimi.ingsw.server.model.player.Player;

/**
 * A Goal Card contains a pattern which players aim to
 * reproduce in their shelf in order to gain points.
 *
 * @author Daniele ferrario
 */
public abstract class GoalCard {
    /**
     * The id of the goal card
     */
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
     * This method calculates the points that should be given to a player
     * when a goalCard is completed.
     * @param player the player who should be awarded the points
     * @return the points to be awarded to the player
     */
    public abstract int calculatePoints(Player player);

    /**
     * @return a copy of the goalCard which is "this"
     */
    public abstract GoalCard returnEqualCard();

    public String getId() {
        return id;
    }
}
