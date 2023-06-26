package it.polimi.ingsw.server.model.cards;

import java.io.Serializable;

/**
 * The specific point card awarded by a common goal card when completed
 */
public class PointCard implements Serializable {
    /**
     * The specific type of point card
     */
    private PointEnumeration type;
    /**
     * The number of points awarded by the point card
     */
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PointCard pointCard = (PointCard) o;

        if (pointsGiven != pointCard.pointsGiven) return false;
        return type == pointCard.type;
    }

    @Override
    public int hashCode() {
        int result = type != null ? type.hashCode() : 0;
        result = 31 * result + pointsGiven;
        return result;
    }
}
