package it.polimi.ingsw.model;

import it.polimi.ingsw.model.cells.BoardCell;
import it.polimi.ingsw.model.utils.Constants;

/**
 * This class contains elements and methods which can manipulate the board.
 * @author Marco Galli
 * @see BoardCell
 */
public class Board {
    private int heightInCells;
    private int lengthInCells;
    private BoardCell[][] cells;
    private boolean toBeRefilled;

    public Board(int nPlayer) {
        heightInCells = Constants.BOARD_DIMENSION;
        lengthInCells = Constants.BOARD_DIMENSION;
        initBoard(nPlayer);
        toBeRefilled = false;
    }

    /**
     * This method refills board cells that have no more cards, checking if isFull == 0
     * @param sack the game sack
     */
    public void refillBoard(Sack sack) {

    }

    public void initBoard(int nPlayer) {}

    public boolean shouldBeRefilled() {
        return toBeRefilled;
    }
}
