package it.polimi.ingsw.model.cells;

/**
 * This enumeration contains status of cells. Given the number of players, cells can be available or inactive.
 * @author Marco Galli
 * @see BoardCell
 * @see it.polimi.ingsw.model.Board
 */
public enum BoardCellEnum {
    INACTIVE,
    ACTIVE;

    @Override
    public String toString() {
        return this.name().substring(0,1);
    }
}
