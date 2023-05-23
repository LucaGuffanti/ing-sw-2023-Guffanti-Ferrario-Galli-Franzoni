package it.polimi.ingsw.server.model.cells;

import it.polimi.ingsw.server.model.Board;

/**
 * This enumeration contains status of cells. Given the number of players, cells can be available or inactive.
 * @author Marco Galli
 * @see BoardCell
 * @see Board
 */
public enum BoardCellEnum {
    INACTIVE,
    ACTIVE;

    @Override
    public String toString() {
        return this.name().substring(0,1);
    }
}
