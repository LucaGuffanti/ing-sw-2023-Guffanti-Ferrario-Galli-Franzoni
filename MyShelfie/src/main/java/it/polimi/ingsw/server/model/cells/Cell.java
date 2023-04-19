package it.polimi.ingsw.server.model.cells;

import it.polimi.ingsw.server.model.cards.ObjectCard;

import java.util.Optional;

/**
 * This class represents a single cell of the shell and the board.
 * @author Marco Galli
 */
public abstract class Cell {
    private Optional<ObjectCard> cellCard;

    public Cell(Optional cellCard) {
        this.cellCard = cellCard;
    }

    public Optional<ObjectCard> getCellCard() {
        return cellCard;
    }

    public void setCellCard(Optional<ObjectCard> cellCard) {
        this.cellCard = cellCard;
    }

    @Override
    public String toString() {
        return cellCard.map(objectCard -> objectCard.getType().toString()).orElse("X ");
    }

    public boolean isCovered(){
        return cellCard.isPresent();
    }
}
