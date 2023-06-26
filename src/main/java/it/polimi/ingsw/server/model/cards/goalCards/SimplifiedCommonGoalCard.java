package it.polimi.ingsw.server.model.cards.goalCards;

import it.polimi.ingsw.server.model.cards.PointCard;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

/**
 * A simplified version of a common Goal card, consisting only in id and list of point cards
 * @author Luca Guffanti
 */
public class SimplifiedCommonGoalCard implements Serializable {
    /**
     * The id of the common goal card
     */
    private String id;
    /**
     * List of point cards that can be awarded
     */
    private ArrayList<PointCard> pointCards;
    /**
     * Map between the name of each player and the achieved points
     */
    private HashMap<String, PointCard> nickToEarnedPoints;

    public SimplifiedCommonGoalCard(String id, ArrayList<PointCard> pointCards, HashMap<String, PointCard> earnedPoints) {
        this.id = id;
        this.pointCards = pointCards;
        this.nickToEarnedPoints = earnedPoints;
    }

    public String getId() {
        return id;
    }

    public ArrayList<PointCard> getPointCards() {
        return pointCards;
    }

    public HashMap<String, PointCard> getNickToEarnedPoints() {
        return nickToEarnedPoints;
    }

    @Override
    public String toString() {
        return "SimplifiedCommonGoalCard{" +
                "id='" + id + '\'' +
                ", pointCards=" + pointCards +
                ", nickToEarnedPoints=" + nickToEarnedPoints.toString() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SimplifiedCommonGoalCard that = (SimplifiedCommonGoalCard) o;

        if (!Objects.equals(id, that.id)) return false;
        if (!Objects.equals(pointCards, that.pointCards)) return false;
        return Objects.equals(nickToEarnedPoints, that.nickToEarnedPoints);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (pointCards != null ? pointCards.hashCode() : 0);
        result = 31 * result + (nickToEarnedPoints != null ? nickToEarnedPoints.hashCode() : 0);
        return result;
    }
}
