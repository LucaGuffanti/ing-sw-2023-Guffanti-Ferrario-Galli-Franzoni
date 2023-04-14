package it.polimi.ingsw.model.cards.goalCards;

import it.polimi.ingsw.model.cards.PointCard;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * A simplified version of a common Goal card, consisting only in id and list of point cards
 * @author Luca Guffanti
 */
public class SimplifiedCommonGoalCard implements Serializable {
    private String id;
    private ArrayList<PointCard> pointCards;

    public SimplifiedCommonGoalCard(String id, ArrayList<PointCard> pointCards) {
        this.id = id;
        this.pointCards = pointCards;
    }
}
