package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.cards.ObjectCard;
import it.polimi.ingsw.model.cards.ObjectTypeEnum;
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
        fixHighestOccupiedCell();
    }

    public boolean isFull() {
        isFull = checkFullness();
        return isFull;
    }


    public int[] getHighestOccupiedCell() {
        return highestOccupiedCell;
    }

    public void setHighestOccupiedCell(int[] highestOccupiedCell) {
        this.highestOccupiedCell = highestOccupiedCell;
    }

    public Shelf() {
        cells = new ShelfCell[Constants.SHELF_HEIGHT][Constants.SHELF_LENGTH];

        for (int y = 0; y < heightInCells; y++) {
            for (int x = 0; x < lengthInCells; x++) {
                cells[y][x] = new ShelfCell(Optional.empty());
            }
        }
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
        fixHighestOccupiedCell();
    }

    private void fixHighestOccupiedCell() {
        for (int i = 0; i < 5; i++) {
            highestOccupiedCell[i] = 6;
        }
        for (int x = 0; x < 5; x++) {
            for (int y = 0; y < 6; y++) {
                if (!cells[y][x].getCellCard().isEmpty()) {
                    highestOccupiedCell[x]--;
                }
            }
        }
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

        isFull = checkFullness();

        return success;
    }

    public boolean checkFullness() {
        for (int i = 0; i < lengthInCells; i++) {
            if (highestOccupiedCell[i] != 0) {
                return false;
            }
        }
        return true;
    }
}
