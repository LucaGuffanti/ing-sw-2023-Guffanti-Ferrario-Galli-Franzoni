package it.polimi.ingsw.server.model.cards.goalCards;

import it.polimi.ingsw.server.model.cards.PointCard;
import it.polimi.ingsw.server.model.player.Player;

import java.util.*;

/**
 * CommonGoalCards represents a goal for all players. When the goal is reached,
 * the user will get a PointCard relatively to how many player have previously completed the same goal.
 *
 * @author Daniele Ferrario
 *
 */
public abstract class CommonGoalCard extends GoalCard {

    /**
     * List of available point cards
     */
    protected ArrayList<PointCard> pointsCards;


    public CommonGoalCard(String id, ArrayList<PointCard> pointsCard) {
        super(id);
        this.pointsCards = pointsCard;
    }

    public CommonGoalCard(CommonGoalCard card) {
        super(card.getId());
        this.pointsCards = new ArrayList<>(card.getPointsCards());
    }

    public CommonGoalCard(String id) {
        super(id);
        this.pointsCards = null;
    }


    /**
     * This method calculates the points the user will receive
     * based on how many players have already reached the goal.
     * The most valuable PointCard ( the pointCard on the top of
     * pointsCard list) will be returned to the player and popped
     * out of pointsCards.
     *
     * @param player        The current player whom shelf has to be analyzed.
     * @return PointCard    A card representing game points.
     */
    @Override
    public int calculatePoints(Player player){

        // If there are no more pointsCards, don't compute the pattern matching algorithm
        // @TODO: Maybe rise an exception

        if(pointsCards.size() == 0){
            return 0;
        }

        // Start pattern matching algorithm
        int patternsFound = checkPattern(player);

        // If less patterns than requested are found, the goal has not been reached
        if(patternsFound == 0) {
            return 0;
        }

        // Get the highest pointCard and remove it from the pile
        PointCard highestPointCard = this.pointsCards.remove(pointsCards.size()-1);

        return highestPointCard.getPointsGiven();

    }

    public void setPointsCards(ArrayList<PointCard> pointsCards) {
        this.pointsCards = pointsCards;
    }

    public ArrayList<PointCard> getPointsCards() {
        return pointsCards;
    }
}
