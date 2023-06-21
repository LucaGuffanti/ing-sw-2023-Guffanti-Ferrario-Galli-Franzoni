package it.polimi.ingsw.client.controller.utils;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.client.view.cli.Printer;
import it.polimi.ingsw.server.model.cards.ObjectTypeEnum;
import it.polimi.ingsw.server.model.cells.Coordinates;
import it.polimi.ingsw.server.model.utils.Constants;

import java.util.Arrays;
import java.util.List;

/**
 * This object contains methods that check whether different types of selections follow the rules of the game.
 * @author Luca Guffanti, Marco Galli
 */
public class PickChecker {
    /**
     * This method checks that 3 coordinates are correctly ordered, either in ascending and descending
     * order and that they are subsequent
     * @param c1 the first coordinate
     * @param c2 the second coordinate
     * @param c3 the third coordinate
     * @return c1"<"c2"<"c3 && c1+2==c2+1==c3 OR c3"<"c2"<"c1 && c3+2==c2+1==c1
     */
    public static boolean checkTripleAdjacency(int c1, int c2, int c3) {
        int[] arr = new int[3];
        arr[0] = c1;
        arr[1] = c2;
        arr[2] = c3;

        arr = Arrays.stream(arr).sorted().toArray();

        return arr[0]+1 == arr[1] && arr[0]+2 == arr[2];
    }

    /**
     * @param first first set of coordinates
     * @param second second set of coordinates
     * @param newChoice third set of coordinates
     * @return whether the coordinates are correctly ordered
     */
    public static boolean checkTripleOrdering(Coordinates first, Coordinates second, Coordinates newChoice) {
        int x1 = first.getX();
        int y1 = first.getY();

        int x2 = second.getX();
        int y2 = second.getY();

        int xc = newChoice.getX();
        int yc = newChoice.getY();

        // all the tiles must be on the same row or on the same column
        if (x1 != xc && x2 != xc && y1 != yc && y2 != yc) {
            return false;
        }

        if (x1 == x2 && x2 == xc) {
            // if the x coordinates are equal, then the tiles must be one adjacent to the other on the y axe
            return checkTripleAdjacency(y1, y2, yc);
        } else {
            // if the y coordinates are equal, then the tiles must be one adjacent to the other on the x axe
            return checkTripleAdjacency(x1, x2, xc);
        }
    }

    /**
     *
     * @param first first set of coordinates
     * @param newChoiche second set of coordinates
     * @return whether the pair of coordinates is correctly ordered
     */
    private static boolean checkDoubleOrdering(Coordinates first, Coordinates newChoiche) {
        int x1 = first.getX();
        int y1 = first.getY();

        int xc = newChoiche.getX();
        int yc = newChoiche.getY();

        // two tiles must be on the same row or on the same column
        if (y1 != yc && x1 != xc) {
            return false;
        }

        if (x1 == xc) {
            // if the x coordinates are equal, then the tiles must be one adjacent to the other on the y axe
            return y1 == yc+1 || y1 == yc-1;
        } else {
            // if the y coordinates are equal, then the tiles must be one adjacent to the other on the x axe
            return x1 == xc+1 || x1 == xc-1;
        }
    }

    /**
     * @param board the current board
     * @param j x coordinate of the tile
     * @param i y coordinate of the tile
     * @return whether the tile centered at (j,i) has any free sides
     */
    public static boolean hasFreeSides(ObjectTypeEnum[][] board, int j, int i) {
        if (i == 0 || j == 0 || i == 8 || j == 8) {
            return true;
        } else {
            return board[i+1][j] == null ||
                    board[i-1][j] == null ||
                    board[i][j+1] == null ||
                    board[i][j-1] == null;
        }
    }

    /**
     *
     * @param clicked list of selected tiles
     * @return whether the selection follows the rules of the game
     */
    public static boolean checkAdjacencies(List<Coordinates> clicked) {
        for (Coordinates coord : clicked) {
            if (!PickChecker.hasFreeSides(ClientManager.getInstance().getStateContainer().getCurrentState().getBoard(),
                    coord.getX(),
                    coord.getY())) {
                return false;
            }
        }
        if (clicked.size() == 1) {
            // this means that the chosen tile would be the first one.
            return true;
        } else if (clicked.size() == 2) {
            // this means that the chosen tile would be the second one
            return checkDoubleOrdering(clicked.get(0), clicked.get(1));
        } else if (clicked.size() == 3) {
            // this means that the chosen tile would be the third one
            return checkTripleOrdering(clicked.get(0), clicked.get(1), clicked.get(2));
        } else {
            Printer.error("UNEXPECTED CASE - RETURNING FALSE ");
            return false;
        }
    }

    /**
     *
     * @param shelf the shelf matrix
     * @param dim the dimension of the would-be selection
     * @return whether a shelf has enough space in at least one of its column
     */
    public static boolean shelfIsFull(ObjectTypeEnum[][] shelf, int dim) {
        boolean isFull = false;
        int freeCells = 0;
        int tmpFreeCells;
        for (int j = 0; j < Constants.SHELF_LENGTH; j++) {
            tmpFreeCells = Constants.SHELF_HEIGHT;
            for (int i = Constants.SHELF_HEIGHT-1; i >= 0; i--, tmpFreeCells--) {
                if (shelf[i][j] == null) {
                    break;
                }
            }
            if (freeCells < tmpFreeCells) {
                freeCells = tmpFreeCells;
            }
            if (freeCells > 2) {
                break;
            }
        }
        if (freeCells < dim) {
            isFull = true;
        }
        return isFull;
    }
}
