package it.polimi.ingsw.model.utils;

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

}
