package it.polimi.ingsw.server.model.cards.goalCards;

import it.polimi.ingsw.server.model.cards.PointCard;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simplified version of a common Goal card, consisting only in id and list of point cards
 * @author Luca Guffanti
 */
public class SimplifiedCommonGoalCard implements Serializable {
    private String id;
    private ArrayList<PointCard> pointCards;
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
}
