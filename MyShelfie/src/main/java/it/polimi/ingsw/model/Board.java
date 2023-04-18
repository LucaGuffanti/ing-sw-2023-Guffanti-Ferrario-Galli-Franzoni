package it.polimi.ingsw.model;

import it.polimi.ingsw.model.cards.ObjectCard;
import it.polimi.ingsw.model.cells.BoardCell;
import it.polimi.ingsw.model.cells.BoardCellEnum;
import it.polimi.ingsw.model.cells.Coordinates;
import it.polimi.ingsw.model.utils.Constants;
import it.polimi.ingsw.model.utils.CsvToBoardParser;
import it.polimi.ingsw.model.utils.exceptions.*;

import java.util.*;
import java.util.stream.Collectors;

import static it.polimi.ingsw.model.utils.Constants.BOARD_DIMENSION;

/**
 * This class contains elements and methods which can manipulate the board.
 * @author Marco Galli
 * @see BoardCell
 */
public class Board {

    private static final String pathToCsvFile = "src/main/assets/board/cellTypeConfiguration.csv";
    private final int heightInCells;
    private final int lengthInCells;
    private BoardCell[][] cells;
    private boolean toBeRefilled;

    public Board(int nPlayer, Sack sack) {
        heightInCells = Constants.BOARD_DIMENSION;
        lengthInCells = Constants.BOARD_DIMENSION;
        initBoard(nPlayer, sack);
    }

    /**
     * This method refills board cells that have no more cards, checking if isFull == 0
     * @param sack the game sack
     */
    public void refillBoard(Sack sack) {

        for (int y = 0; y < lengthInCells; y++) {
            for (int x = 0; x < heightInCells; x++) {
                try {
                    if (cells[y][x].getType().equals(BoardCellEnum.ACTIVE) && !cells[y][x].isCovered()) {
                        cells[y][x].setCellCard(Optional.of(sack.pickFromSack()));
                    }
                } catch (EmptySackException e) {
                    e.printStackTrace();
                }
            }
        }
        toBeRefilled = false;
    }

    /**
     * This method initializes the board: firstly the cells are instantiated based on their type,
     * then they get populated.
     * @param nPlayer the number of players in the game
     * @param sack the sack from which object cards are extracted
     */
    public void initBoard(int nPlayer, Sack sack) {
        cells = CsvToBoardParser.parseBoardCellTypeConfiguration(pathToCsvFile, nPlayer);
        refillBoard(sack);
        toBeRefilled = false;
    }

    /**
     *  Remove and return the ObjectCards from the Board at the specified positions in coordinates.
     *  @param coordinates Ordered list of coordinates which indicates the ObjectCells to pick and remove. Each coordinate
     *                     is supposed to point to filled cell.
     *  @return Ordered list of ObjectCards
     */
    public List<ObjectCard> pickCells(List<Coordinates> coordinates) {

        // Collect and remove
        return  coordinates.stream().map(c->{
            BoardCell cell = cells[c.getY()][c.getX()];
            ObjectCard obj =cell.getCellCard().get();
            cell.setCellCard(Optional.empty());
            return obj;
        }).collect(Collectors.toList());


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
                } else if (i == 0) {
                    if (cells[i][j].getCellCard().isPresent() && (cells[i][j+1].getCellCard().isPresent() ||
                            cells[i][j-1].getCellCard().isPresent() || cells[i+1][j].getCellCard().isPresent())) {
                        found = true;
                    }
                } else if (j == 0) {
                    if (cells[i][j].getCellCard().isPresent() && (cells[i][j+1].getCellCard().isPresent() ||
                            cells[i+1][j].getCellCard().isPresent() || cells[i-1][j].getCellCard().isPresent())) {
                        found = true;
                    }
                } else {
                    if (cells[i][j].getCellCard().isPresent() && (cells[i][j+1].getCellCard().isPresent() ||
                            cells[i][j-1].getCellCard().isPresent() || cells[i+1][j].getCellCard().isPresent()
                            || cells[i-1][j].getCellCard().isPresent())) {
                        found = true;
                    }
                }
            }
        }
        toBeRefilled = !found;
        return toBeRefilled;
    }

    public BoardCell getCell(int x, int y) {
        return cells[y][x];
    }

    public BoardCell[][] getCells() {
        return cells;
    }

    /**
     * Check if player can pick a set of BoardCells given their coordinates from the board
     * @param cellsCoordinates the cells to pick
     * @author Daniele Ferrario
     */
    public void checkBoardPickValidity(Collection<Coordinates> cellsCoordinates) throws IllegalBoardCellsPickException {


        // Check if coordinates do not belong to the same row or to the same column
        if(cellsCoordinates.stream().map(Coordinates::getX).distinct().count() > 1 &&
                cellsCoordinates.stream().map(Coordinates::getY).distinct().count() > 1)
            throw new DiagonalBoardCellsCellsPickException();


        for (Coordinates coordinates: cellsCoordinates) {
            // Check if not empty
            if(!this.cells[coordinates.getY()][coordinates.getX()].isCovered())
                throw new EmptyBoardCellsPickException(coordinates.getX(), coordinates.getY());

            // Checking adjacent ones
            if(!canPickSingleCellFromBoard(coordinates.getX(), coordinates.getY()))
                throw new NoEmptyAdjacentBoardCellsPickException();

        }

    }

    /**
     * Check if player can pick a BoardCell from the board given its coordinates
     * @param x
     * @param y
     */
    private boolean canPickSingleCellFromBoard(int x, int y){


        // If target cell is covered
        if(cells[y][x].isCovered()){

            // Check if exists an adjacent cell which is not covered
            boolean expr = (
                    ((x-1 >= 0) && !cells[y][x-1].isCovered()) ||
                            ((y-1 >= 0) && !cells[y-1][x].isCovered()) ||
                            ((x+1 < BOARD_DIMENSION) && !cells[y][x+1].isCovered()) ||
                            ((y+1 < BOARD_DIMENSION) && !cells[y+1][x].isCovered())

            );



            if (expr)
                return true;
        }

        return false;

    }


    public void setCells(BoardCell[][] cells) {
        this.cells = cells;
    }

}
