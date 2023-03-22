package it.polimi.ingsw.model.cards;

/**
 * The end of game card is the card that's assigned when the game enters its
 * final round. It can be assigned to a player, who holds the reference to this object.
 * @author Luca Guffanti
 */
public class EndOfGameCard {
    private boolean isAssigned;
    private final int pointsAssigned = 1;
    public EndOfGameCard() {
        isAssigned = false;
    }
    public boolean isAssigned() {
        return isAssigned;
    }

    public int getPointsAssigned() {
        return pointsAssigned;
    }

    public void setAssigned(boolean assigned) {
        isAssigned = assigned;
    }
}
