package it.polimi.ingsw.model.cards;

import java.awt.*;

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
}
