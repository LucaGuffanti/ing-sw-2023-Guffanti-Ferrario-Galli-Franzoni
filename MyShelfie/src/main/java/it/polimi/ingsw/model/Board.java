package it.polimi.ingsw.model;

import it.polimi.ingsw.model.cells.BoardCell;
import it.polimi.ingsw.model.cells.BoardCellEnum;
import it.polimi.ingsw.model.utils.Constants;
import it.polimi.ingsw.model.utils.CsvToBoardParser;
import it.polimi.ingsw.model.utils.exceptions.EmptySackException;

import java.util.Optional;

/**
 * This class contains elements and methods which can manipulate the board.
 * @author Marco Galli
 * @see BoardCell
 */
public class Board {

    private static final String pathToCsvFile = "src/main/assets/board/cellTypeConfiguration.csv";
    private int heightInCells;
    private int lengthInCells;
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
                    if (cells[y][x].getType().equals(BoardCellEnum.ACTIVE)) {
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
     * This method initializes the board: firstly the cells are instantiated based on their type
     * and they get populated.
     * @param nPlayer the number of players in the game
     * @param sack the sack from which object cards are extracted
     */
    public void initBoard(int nPlayer, Sack sack) {
        cells = CsvToBoardParser.parseBoardCellTypeConfiguration(pathToCsvFile, nPlayer);
        refillBoard(sack);
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

    public BoardCell getCell(int x, int y) {
        return cells[y][x];
    }
}
