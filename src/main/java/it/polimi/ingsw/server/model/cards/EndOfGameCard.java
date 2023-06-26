package it.polimi.ingsw.server.model.cards;

/**
 * The end of game card is the card that's assigned when the game enters its
 * final round. It can be assigned to a player, who holds the reference to this object.
 * @author Luca Guffanti
 */
public class EndOfGameCard {
    /**
     * Whether the card has already been assigned
     */
    private boolean isAssigned;
    /**
     * The number of points that
     */
    private final int pointsAssigned = 1;
    public EndOfGameCard() {
        isAssigned = false;
    }
    public void setAssigned(boolean assigned) {
        isAssigned = assigned;
    }
}
