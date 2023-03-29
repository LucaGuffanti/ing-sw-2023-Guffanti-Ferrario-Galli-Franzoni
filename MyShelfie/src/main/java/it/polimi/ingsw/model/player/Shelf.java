package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.cards.ObjectCard;
import it.polimi.ingsw.model.cells.ShelfCell;
import it.polimi.ingsw.model.utils.Constants;

import java.util.List;
import java.util.Optional;

/**
 * This class contains a player's shelf status and manipulates it with some methods.
 * @author Marco Galli
 * @see ShelfCell
 * @see ObjectCard
 */


public class Shelf {
    private ShelfCell[][] cells;
    private boolean isFull;
    private int[] highestOccupiedCell;

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

    public Shelf() {
        cells = new ShelfCell[Constants.SHELF_HEIGHT][Constants.SHELF_LENGTH];
        isFull = false;
        highestOccupiedCell = new int[]{Constants.SHELF_HEIGHT, Constants.SHELF_HEIGHT,
                Constants.SHELF_HEIGHT, Constants.SHELF_HEIGHT, Constants.SHELF_HEIGHT};
    }

    public ShelfCell getCell(int x, int y){
        return cells[y][x];
    }

    /**
     * This method checks if a list of object cards, taken by a player from the board, can be added to a shelf.
     * @param column column where a player wants to insert his object cards into the shelf
     * @param cards list of object card that a player has taken from the board
     * @return outcome of this check
     */
    private boolean checkCardsAddability(int column, List<ObjectCard> cards) {
        boolean canInsert;
        if (highestOccupiedCell[column] - cards.size() >= 0) {
            canInsert = true;
        } else {
            canInsert = false;
        }
        return canInsert;
    }

    /**
     * This method adds to a shelf a list of object card taken from the board by a player.
     * @param column column where a player wants to insert his object cards into the shelf
     * @param cards list of object card that a player has taken from the board
     * @return outcome of the addition
     */
    public boolean addCardsToColumn(int column, List<ObjectCard> cards) {
        boolean success;
        if (checkCardsAddability(column, cards)) {
            for (ObjectCard card : cards) {
                highestOccupiedCell[column]--;
                cells[highestOccupiedCell[column]][column].setCellCard(Optional.of(card));
            }
            success = true;
        } else {
            success = false;
        }
        return success;
    }
}
