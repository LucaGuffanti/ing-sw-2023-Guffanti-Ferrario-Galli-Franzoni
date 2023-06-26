package it.polimi.ingsw.server.model.cells;



import it.polimi.ingsw.server.model.cards.ObjectCard;

import java.util.Optional;

/**
 * A specialization of {@link Cell}, regarding the cells of a shelf
 */
public class ShelfCell extends Cell{
    public ShelfCell(Optional<ObjectCard> cellCard) {
        super(cellCard);
    }

}
