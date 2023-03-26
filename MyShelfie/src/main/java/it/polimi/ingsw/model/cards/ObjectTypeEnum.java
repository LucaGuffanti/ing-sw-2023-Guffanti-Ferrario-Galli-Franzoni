package it.polimi.ingsw.model.cards;

import java.awt.*;

/**
 * Possible types of Object Cards.
 * @author Luca Guffanti
 * @see ObjectCard
 */
public enum ObjectTypeEnum {

    CAT(Color.GREEN),
    BOOK(Color.WHITE),
    TOY(Color.YELLOW),
    FRAME(Color.BLUE),
    TROPHY(Color.CYAN),
    PLANT(Color.MAGENTA);

    private Color color;
    ObjectTypeEnum(Color color) {
        this.color = color;
    }
    public Color getColor() {
        return color;
    }

    @Override
    public String toString() {
        return this.name().substring(0,1);
    }
}
