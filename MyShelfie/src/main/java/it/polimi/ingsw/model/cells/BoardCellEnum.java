package it.polimi.ingsw.model.cells;

/**
 * This enumeration contains status of cells. Given the number of players, cells can be available or inactive.
 * @author Marco Galli
 * @see BoardCell
 * @see it.polimi.ingsw.model.Board
 */
public enum BoardCellEnum {
    INACTIVE,
    THREE_PLAYERS,
    FOUR_PLAYERS,
    ALWAYS_ACTIVE
}
