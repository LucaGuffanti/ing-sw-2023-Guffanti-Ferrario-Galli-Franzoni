
package it.polimi.ingsw.server.model.player;

import it.polimi.ingsw.server.model.cards.ObjectCard;
import it.polimi.ingsw.server.model.cells.ShelfCell;
import it.polimi.ingsw.server.model.utils.Constants;
import it.polimi.ingsw.server.model.utils.exceptions.NoSpaceEnoughInShelfColumnException;
import it.polimi.ingsw.server.model.utils.exceptions.NoSpaceEnoughInShelfException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * This class contains a player's shelf status and manipulates it with some methods.
 * @author Luca Guffanti, Daniele Ferrario, Marco Galli
 * @see ShelfCell
 * @see ObjectCard
 */
public class Shelf {
    /**
     * A matrix of shelf cells
     */
    private ShelfCell[][] cells;

    /**
     * A boolean which indicates if the shelf is full
     */
    private boolean isFull;

    /**
     * For each column, it indicates the highest occupied cell
     */
    private int[] highestOccupiedCells;

    /**
     * The length of the shelf
     */
    private int lengthInCells = Constants.SHELF_LENGTH;

    /**
     * The height of the shelf
     */
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

    public int[] getHighestOccupiedCells() {
        return highestOccupiedCells;
    }

    public int getHighestOccupiedCellIndex(int colIndex){
        return highestOccupiedCells[colIndex];
    }

    public ShelfCell getCell(int x, int y){
        return cells[y][x];
    }

    public Shelf() {
        cells = new ShelfCell[Constants.SHELF_HEIGHT][Constants.SHELF_LENGTH];

        for (int y = 0; y < heightInCells; y++) {
            for (int x = 0; x < lengthInCells; x++) {
                cells[y][x] = new ShelfCell(Optional.empty());
            }
        }
        isFull = false;
        highestOccupiedCells = new int[lengthInCells];
        Arrays.fill(highestOccupiedCells, Constants.SHELF_HEIGHT);

    }

    public Shelf(int length, int height, ShelfCell[][] cells) {
        this.cells = cells;
        isFull = false;
        this.lengthInCells = length;
        this.heightInCells = height;
        highestOccupiedCells = new int[lengthInCells];
        Arrays.fill(highestOccupiedCells, Constants.SHELF_HEIGHT);
        fixHighestOccupiedCell();
    }

    /**
     * This method updates the highest occupied cell
     */
    private void fixHighestOccupiedCell() {
        for (int x = 0; x < lengthInCells; x++) {
            // Initializing supposing empty column, so the height exceed by 1 the column bound (y=5+1)
            highestOccupiedCells[x] = heightInCells;

            // If an ObjectType is found at a certain height, reduce the last height found by one
            for (int y = 0; y < heightInCells; y++) {
                if (cells[y][x].getCellCard().isPresent()) {
                    highestOccupiedCells[x]--;
                }

            }
        }
        isFull = checkFullness();
    }

    /**
     * This method checks if a list of object cards, taken by a player from the board, can be added to a shelf.
     * @param column column where a player wants to insert his object cards.
     * @param cardsNumber the number of card to insert in the column.
     */
    public void checkIfEnoughSpaceInColumn(int cardsNumber, int column) throws NoSpaceEnoughInShelfColumnException {
        if (highestOccupiedCells[column] - cardsNumber < 0) {
            throw new NoSpaceEnoughInShelfColumnException(column, highestOccupiedCells[column]);
        }
    }

    /**
     * This method checks if there is enough space in the shelf for selected tiles
     * @param cardsNumber the number of tiles
     * @throws NoSpaceEnoughInShelfException if there is no space in the shelf for the tiles
     */
    public void checkIfEnoughSpace(int cardsNumber) throws NoSpaceEnoughInShelfException {
        int emptyCellsNumber =  (int) Arrays.stream(highestOccupiedCells).sum();
        if (emptyCellsNumber<cardsNumber)
            throw new NoSpaceEnoughInShelfException(emptyCellsNumber);
    }

    /**
     * This method adds to a shelf a list of object card taken from the board by a player.
     * @param cards list of object card that a player has taken from the board
     * @param column column where a player wants to insert his object cards into the shelf
     */
    public void addCardsToColumn(List<ObjectCard> cards, int column) {

        for (ObjectCard card : cards) {
            highestOccupiedCells[column]--;
            cells[highestOccupiedCells[column]][column].setCellCard(Optional.of(card));
        }

        isFull = checkFullness();
    }

    /**
     * This method checks if the shelf is full
     * @return a boolean which indicates if the shelf is full
     */
    public boolean checkFullness() {
        for (int i = 0; i < lengthInCells; i++) {
            if (highestOccupiedCells[i] != 0) {
                return false;
            }
        }
        return true;
    }
}