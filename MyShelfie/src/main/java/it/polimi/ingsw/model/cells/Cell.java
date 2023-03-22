package it.polimi.ingsw.model.cells;

import it.polimi.ingsw.model.cards.ObjectCard;

/**
 * This class represents a single cell.
 * @author Marco Galli
 */
public abstract class Cell {
    private ObjectCard cellCard;
    private boolean isFull;

    public Cell() {
        cellCard = null;
        isFull = false;
    }

    public ObjectCard getCellCard() {
        return cellCard;
    }

    public void setCellCard(ObjectCard cellCard) {
        this.cellCard = cellCard;
    }

    public boolean isFull() {
        return isFull;
    }

    public void setFull(boolean full) {
        isFull = full;
    }

    public void updateCell(ObjectCard objectCard) {
        cellCard = objectCard;
        isFull = true;
    }

    public void removeCard() {
        isFull = false;
    }
}
