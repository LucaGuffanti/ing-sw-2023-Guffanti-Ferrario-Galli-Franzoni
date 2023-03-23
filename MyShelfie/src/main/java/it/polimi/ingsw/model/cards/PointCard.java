package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.player.Player;

public class PointCard {
    private PointEnumeration type;
    private int pointsGiven;

    public PointCard(PointEnumeration type, int pointsGiven){
        this.pointsGiven = pointsGiven;
        this.type = type;
    }

    public PointEnumeration getType() {
        return type;
    }

    public void setType(PointEnumeration type) {
        this.type = type;
    }

    public int getPointsGiven() {
        return pointsGiven;
    }

    public void setPointsGiven(int pointsGiven) {
        this.pointsGiven = pointsGiven;
    }



}
