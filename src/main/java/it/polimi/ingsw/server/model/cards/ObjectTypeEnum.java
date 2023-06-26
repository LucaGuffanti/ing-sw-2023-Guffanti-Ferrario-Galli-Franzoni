package it.polimi.ingsw.server.model.cards;

import java.awt.*;
import java.io.Serializable;

/**
 * Possible types of Object Cards.
 * @author Luca Guffanti
 * @see ObjectCard
 */
public enum ObjectTypeEnum implements Serializable {

    CAT(Color.GREEN),
    BOOK(Color.WHITE),
    TOY(Color.YELLOW),
    FRAME(Color.BLUE),
    TROPHY(Color.CYAN),
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
