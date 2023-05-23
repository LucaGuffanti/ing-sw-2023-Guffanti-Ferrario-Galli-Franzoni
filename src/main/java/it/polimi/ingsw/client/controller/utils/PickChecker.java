package it.polimi.ingsw.client.controller.utils;

import it.polimi.ingsw.client.view.cli.Printer;
import it.polimi.ingsw.server.controller.utils.GameObjectConverter;
import it.polimi.ingsw.server.model.Board;
import it.polimi.ingsw.server.model.Sack;
import it.polimi.ingsw.server.model.cards.ObjectTypeEnum;
import it.polimi.ingsw.server.model.cells.Coordinates;
import jdk.jfr.Label;

import java.util.List;

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
        if (c1 < c2 && c2 < c3) {
            return c1 +1 == c2 && c2 +1 == c3;
        } else if (c3 < c2 && c2 < c1) {
            return c3 +1 == c2 && c2 +1 == c1;
        }else {
            return false;
        }
    }

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


    public static boolean checkAdjacencies(List<Coordinates> clicked, Coordinates coordsOfImage) {
        if (clicked.size() == 0) {
            // this means that the chosen tile would be the first one.
            return true;
        } else if (clicked.size() == 1) {
            // this means that the chosen tile would be the second one
            return checkDoubleOrdering(clicked.get(0), coordsOfImage);
        } else if (clicked.size() == 2) {
            // this means that the chosen tile would be the third one
            return checkTripleOrdering(clicked.get(0), clicked.get(1), coordsOfImage);
        } else if (clicked.size() == 3) {
            // this means that the chose tile would be the fourth and that is not permitted
            return false;
        } else {
            Printer.error("UNEXPECTED CASE - RETURNING FALSE ");
            return false;
        }
    }

    @Label("DEBUG")
    public static void main(String[] args) {
        Sack s = new Sack();
        Board b = new Board(3, s);
        ObjectTypeEnum[][] board = GameObjectConverter.fromBoardToMatrix(b);

        Printer.printBoard(board);
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] != null) {
                    System.out.println("("+j+", "+i+")->"+PickChecker.hasFreeSides(board, i , j));
                }
            }
        }
    }
}
