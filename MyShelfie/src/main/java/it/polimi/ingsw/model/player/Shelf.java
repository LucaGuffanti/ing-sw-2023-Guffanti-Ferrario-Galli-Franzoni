package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.cards.ObjectCard;
import it.polimi.ingsw.model.cells.ShelfCell;
import it.polimi.ingsw.model.utils.Constants;

import java.awt.*;
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

    private int lengthInCells = Constants.SHELF_LENGTH;
    private int heightInCells = Constants.SHELF_HEIGHT;

    public int getLengthInCells() {
        return lengthInCells;
    }

    public int getHeightInCells() {
        return heightInCells;
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

    public Shelf() {
        cells = new ShelfCell[Constants.SHELF_HEIGHT][Constants.SHELF_LENGTH];
        isFull = false;
        highestOccupiedCell = new int[]{Constants.SHELF_HEIGHT, Constants.SHELF_HEIGHT,
                Constants.SHELF_HEIGHT, Constants.SHELF_HEIGHT, Constants.SHELF_HEIGHT};
    }

    public Shelf(int length, int height, ShelfCell[][] cells) {
        this.cells = cells;
        isFull = false;
        this.lengthInCells = length;
        this.heightInCells = height;
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
    private boolean checkCardsAddability(List<ObjectCard> cards, int column) {
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
     * @param cards list of object card that a player has taken from the board
     * @param column column where a player wants to insert his object cards into the shelf
     * @return outcome of the addition
     */
    public boolean addCardsToColumn(List<ObjectCard> cards, int column) {
        boolean success;
        if (checkCardsAddability(cards, column)) {
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
