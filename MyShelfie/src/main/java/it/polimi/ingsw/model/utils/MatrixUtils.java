package it.polimi.ingsw.model.utils;

import it.polimi.ingsw.model.cards.ObjectTypeEnum;
import it.polimi.ingsw.model.cells.BoardCell;
import it.polimi.ingsw.model.cells.BoardCellEnum;
import it.polimi.ingsw.model.cells.ShelfCell;
import it.polimi.ingsw.model.player.Shelf;

import java.util.Optional;

import static it.polimi.ingsw.model.utils.Constants.SHELF_HEIGHT;
import static it.polimi.ingsw.model.utils.Constants.SHELF_LENGTH;

public class MatrixUtils {
    public static boolean[][] clone(boolean[][] src) {
        boolean[][] destination = new boolean[src.length][];

        for (int i = 0; i < src.length; ++i) {
            // allocating space for each row of destination array
            destination[i] = new boolean[src[i].length];
            System.arraycopy(src[i], 0, destination[i], 0, destination[i].length);
        }

        return destination;

    }

    public static String printMatrix(boolean[][] m){
        // For debugging
        String s = "";
        String tmp;
        // Printing the founded cells
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[i].length; j++) {
                tmp = (m[i][j] ? "1": "0")+" ";
                s = s.concat(tmp);
                System.out.print(tmp);

            }
            System.out.println();
        }

        return s;
    }

    public static String printMatrix(ShelfCell[][] m){
        // For debugging
        String s = "";
        String tmp;
        // Printing the founded cells
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[i].length; j++) {
                tmp = m[i][j].toString()+"  ";
                s = s.concat(tmp);
                System.out.print(tmp);

            }
            System.out.println();
        }

        return s;
    }

    public static String printMatrix(BoardCell[][] m){
        // For debugging
        String s = "";
        String tmp;
        // Printing the founded cells
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[i].length; j++) {
                tmp = m[i][j].toString()+"  ";
                s = s.concat(tmp);
                System.out.print(tmp);

            }
            System.out.println();
        }

        return s;
    }

    public static boolean[][] createEmptyMatrix(int l, int h){
        boolean[][] cells = new boolean[h][l];

        for (int row = 0; row < h; row++) {
            for (int col = 0; col < l; col++) {
                cells[row][col] = false;
            }
        }

        return cells;
    }

    public static ShelfCell[][] emptyShelfCellMatrixInit(int l, int h){
        ShelfCell[][] cells = new ShelfCell[h][l];
        for (int row = 0; row < h; row++) {
            for (int col = 0; col < l; col++) {
                cells[row][col] = new ShelfCell(Optional.empty());
            }
        }

        return cells;
    }

    /**
     * Given a matrix representing a shelf and a type of object card, this method calculates the dimension
     * of a group of adjacent object cards starting from the x and y coordinates passed as parameters.
     * @param matrix the matrix representing the shelf
     * @param length the length of the shelf
     * @param height the height of the shelf
     * @param referenceMatrix boolean matrix containing already visited cells
     * @param type the type to match
     * @param x x coordinate of the cell from which the algorithm starts
     * @param y y coordinate of the cell from which the algorithm starts
     * @param highestOccupiedCell
     * @return the dimension of a group of adjacent object cards of type "type", starting from the point (x,y).
     */
    public static int calculateAdjacentShelfCardsGroupDimension(ShelfCell[][] matrix, int length, int height, boolean[][] referenceMatrix, ObjectTypeEnum type, int x, int y, int[] highestOccupiedCell){
        int count = 0;
        int row = y;
        int col = x;
        if (matrix[row][col].getCellCard().isPresent() && matrix[row][col].getCellCard().get().getType().equals(type) && !referenceMatrix[row][col]) {
            referenceMatrix[row][col] = true;
            count++;
            if (row + 1 < height && matrix[row+1][col].getCellCard().isPresent() && matrix[row+1][col].getCellCard().get().getType().equals(type) && highestOccupiedCell[col] <= row+1) {
                count = count + calculateAdjacentShelfCardsGroupDimension(matrix, length, height, referenceMatrix, type, col, row+1, highestOccupiedCell);
            }
            if (row - 1 >= 0  && matrix[row-1][col].getCellCard().isPresent() && matrix[row-1][col].getCellCard().get().getType().equals(type)) {
                count = count + calculateAdjacentShelfCardsGroupDimension(matrix, length, height, referenceMatrix, type, col, row-1, highestOccupiedCell);
            }
            if (col + 1 < length && matrix[row][col+1].getCellCard().isPresent() && matrix[row][col+1].getCellCard().get().getType().equals(type) && highestOccupiedCell[col+1] <= row) {
                count = count + calculateAdjacentShelfCardsGroupDimension(matrix, length, height, referenceMatrix, type, col+1, row, highestOccupiedCell);
            }
            if (col - 1 >= 0 && matrix[row][col-1].getCellCard().isPresent() && matrix[row][col-1].getCellCard().get().getType().equals(type) && highestOccupiedCell[col-1] <= row) {
                count = count + calculateAdjacentShelfCardsGroupDimension(matrix, length, height, referenceMatrix, type, col-1, row, highestOccupiedCell);
            }
        }
        return count;
    }

    public static void printBoardCellTypesConfigurationMatrix(BoardCell[][] matrix) {
        for (int y = 0; y < Constants.BOARD_DIMENSION; y++) {
            for (int x = 0; x < Constants.BOARD_DIMENSION; x++) {
                System.out.print(matrix[y][x].getType().toString()+" ");
            }
            System.out.print("\n");
        }
    }

    public static void printBoardCardsConfigurationMatrix(BoardCell[][] matrix) {
        for (int y = 0; y < Constants.BOARD_DIMENSION; y++) {
            for (int x = 0; x < Constants.BOARD_DIMENSION; x++) {
                if (matrix[y][x].getCellCard().isPresent()) {
                    System.out.print(matrix[y][x].getCellCard().get().getType().toString() + " ");
                } else {
                    System.out.print("XX ");
                }
            }
            System.out.print("\n");
        }
    }
}
