package it.polimi.ingsw.model.cells;

import it.polimi.ingsw.model.cards.ObjectCard;
import it.polimi.ingsw.model.cards.ObjectTypeEnum;

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
        if(cellCard.isPresent())
            return cellCard.get().getType().toString();
        else
            return "X ";
    }
}
