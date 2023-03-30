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
    }

    /**
     * This method refills board cells that have no more cards, checking if isFull == 0
     * @param sack the game sack
     */
    public void refillBoard(Sack sack) {
        //algorithm
        toBeRefilled = false;
    }

    public void initBoard(int nPlayer) {
        // algorithm
        toBeRefilled = false;
    }

    public boolean shouldBeRefilled() {
        boolean found = false;
        for (int i = 0; i < Constants.SHELF_HEIGHT && !found; i++) {
            for (int j = 0; j < Constants.SHELF_LENGTH && !found; j++) {
                if (i == 0 && j == 0) {
                    if (cells[i][j].getCellCard().isPresent() && (cells[i][j+1].getCellCard().isPresent()
                            || cells[i+1][j].getCellCard().isPresent())) {
                        found = true;
                    }
                } else if (i == 0 && j != 0) {
                    if (cells[i][j].getCellCard().isPresent() && (cells[i][j+1].getCellCard().isPresent() ||
                            cells[i][j-1].getCellCard().isPresent() || cells[i+1][j].getCellCard().isPresent())) {
                        found = true;
                    }
                } else if (i != 0 && j == 0) {
                    if (cells[i][j].getCellCard().isPresent() && (cells[i][j+1].getCellCard().isPresent() ||
                            cells[i+1][j].getCellCard().isPresent() || cells[i-1][j].getCellCard().isPresent())) {
                        found = true;
                    }
                } else if (i != 0 && j != 0) {
                    if (cells[i][j].getCellCard().isPresent() && (cells[i][j+1].getCellCard().isPresent() ||
                            cells[i][j-1].getCellCard().isPresent() || cells[i+1][j].getCellCard().isPresent()
                            || cells[i-1][j].getCellCard().isPresent())) {
                        found = true;
                    }
                }
            }
        }
        if (!found) {
            toBeRefilled = true;
        }
        return toBeRefilled;
    }
}
