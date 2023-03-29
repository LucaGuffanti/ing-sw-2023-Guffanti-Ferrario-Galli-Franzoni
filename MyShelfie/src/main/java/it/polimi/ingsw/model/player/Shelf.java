package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.cards.ObjectCard;
import it.polimi.ingsw.model.cells.ShelfCell;
import it.polimi.ingsw.model.utils.Constants;

import java.util.List;
import java.util.Optional;

/**
 * This class contains a player's shelf status and manipulates it with some methods
 * @author Marco Galli
 * @see ShelfCell
 * @see ObjectCard
 */


public class Shelf {
    private int heightInCells;
    private int lengthInCells;
    private ShelfCell[][] cells;
    // cells[0][0] means bottom left
    private boolean isFull;
    private int[] highestOccupiedCell;

    public int getHeightInCells() {
        return heightInCells;
    }

    public void setHeightInCells(int heightInCells) {
        this.heightInCells = heightInCells;
    }

    public int getLengthInCells() {
        return lengthInCells;
    }

    public void setLengthInCells(int lengthInCells) {
        this.lengthInCells = lengthInCells;
    }

    public ShelfCell[][] getCells() {
        return cells;
    }

    public void setCells(ShelfCell[][] cells) {
        this.cells = cells;
    }

    public boolean isFull() {
        return isFull;
    }

    public void setFull(boolean full) {
        isFull = full;
    }

    public int[] getHighestOccupiedCell() {
        return highestOccupiedCell;
    }

    public void setHighestOccupiedCell(int[] highestOccupiedCell) {
        this.highestOccupiedCell = highestOccupiedCell;
    }

    public Shelf(int lengthInCells, int heightInCells, ShelfCell[][] cells) {
        this.heightInCells = heightInCells;
        this.lengthInCells = lengthInCells;
        this.cells = cells;
        isFull = false;
        highestOccupiedCell = new int[]{0, 0, 0, 0, 0};
    }

    public ShelfCell getCell(int x, int y){
        return cells[y][x];
    }

    /**
     * This method checks if a list of object cards, taken by a player from the board, can be added to a shelf.
     * @param col column where a player wants to insert object cards
     * @param cards list of object card that a player has taken from the board
     * @return outcome of this check
     */
    private boolean checkCardsAddability(int col, List<ObjectCard> cards) {
        boolean canInsert;
        if (highestOccupiedCell[col] + cards.size() <= heightInCells) {
            canInsert = true;
        } else {
            canInsert = false;
        }
        return canInsert;
    }

    /**
     * This method adds to a shelf a list of object card taken from the board by a player
     * @param col column where a player wants to insert object cards
     * @param cards list of object card that a player has taken from the board
     * @return outcome of the addition
     */
    public boolean addCardsToColumn(int col, List<ObjectCard> cards) {
        boolean success;
        if (checkCardsAddability(col, cards)) {
            for (ObjectCard card : cards) {
                cells[highestOccupiedCell[col]][col].setCellCard(Optional.of(card));
                highestOccupiedCell[col]++;
            }
            success = true;
        } else {
            success = false;
        }
        return success;
    }
}
