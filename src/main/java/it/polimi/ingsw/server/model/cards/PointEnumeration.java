package it.polimi.ingsw.server.model.cards;

import java.io.Serializable;

/**
 * The points awarded by a point card
 * @see PointCard
 * @author Davide Franzoni
 */
public enum PointEnumeration implements Serializable {
    /**
     * Zero points
     */
    ZERO_POINTS,
    /**
     * Two points
     */
    TWO_POINTS,
    /**
     * Four points
     */
    FOUR_POINTS,
    /**
     * Six points
     */
    SIX_POINTS,
    /**
     * Eight points
     */
    EIGHT_POINTS;
}
