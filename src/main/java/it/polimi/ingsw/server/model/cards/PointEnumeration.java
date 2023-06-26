package it.polimi.ingsw.server.model.cards;

import java.io.Serializable;

/**
 * The points awarded by a point card
 * @see PointCard
 * @author Davide Franzoni
 */
public enum PointEnumeration implements Serializable {
    ZERO_POINTS,
    TWO_POINTS,
    FOUR_POINTS,
    SIX_POINTS,
    EIGHT_POINTS;
}
