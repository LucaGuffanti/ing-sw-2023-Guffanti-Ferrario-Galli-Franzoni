package it.polimi.ingsw.model;

import it.polimi.ingsw.model.cells.ShelfCell;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Shelf;
import it.polimi.ingsw.model.utils.MatrixUtils;

import java.util.Map;

public class EndGameAdjacencyGoalChecker {


    Map<Integer, Integer> matchesToPoints = Map.of(
            3, 2,
            4, 3,
            5, 5,
            6, 8
    );

    public static int calculateAdjacencyPoints(Player player) {
        ShelfCell[][] shelfMatrix = player.getShelf().getCells();
        int shelfLength = player.getShelf().getLengthInCells();
        int shelfheight = player.getShelf().getHeightInCells();
        int totalPoints = 0;

        boolean[][] referenceMatrix = MatrixUtils.createEmptyMatrix(shelfLength, shelfheight);



        return 0;
    }

    private static void checkAdjacency() {
        return;
    }
}
