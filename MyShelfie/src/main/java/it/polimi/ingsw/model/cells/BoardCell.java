package it.polimi.ingsw.model.cells;

import java.util.Optional;

/**
 * This class represent a single cell of the board.
 * @author Marco Galli
 * @see Cell
 */
public class BoardCell extends Cell {
    BoardCellEnum type;

    public BoardCell(Optional cellCard, BoardCellEnum type) {
        super(cellCard);
        this.type = type;
    }

    public BoardCellEnum getType() {
        return type;
    }
}
