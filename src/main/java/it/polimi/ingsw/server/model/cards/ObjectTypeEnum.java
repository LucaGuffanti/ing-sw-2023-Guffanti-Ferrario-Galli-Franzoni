package it.polimi.ingsw.server.model.cards;

import java.awt.*;
import java.io.Serializable;

/**
 * Possible types of Object Cards.
 * @author Luca Guffanti
 * @see ObjectCard
 */
public enum ObjectTypeEnum implements Serializable {
    /**
     * The cat object card
     */
    CAT(Color.GREEN),
    /**
     * The book object card
     */
    BOOK(Color.WHITE),
    /**
     * The toy object card
     */
    TOY(Color.YELLOW),
    /**
     * The frame object card
     */
    FRAME(Color.BLUE),
    /**
     * The trophy object card
     */
    TROPHY(Color.CYAN),
    /**
     * The plant object card
     */
    PLANT(Color.MAGENTA);

    /**
     * The original color of the tile
     */
    private Color color;
    ObjectTypeEnum(Color color) {
        this.color = color;
    }
    public Color getColor() {
        return color;
    }

    @Override
    public String toString() {
        return this.name().substring(0,2);
    }
}
