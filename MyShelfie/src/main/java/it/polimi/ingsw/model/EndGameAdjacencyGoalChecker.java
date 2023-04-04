package it.polimi.ingsw.model;

import it.polimi.ingsw.model.cards.ObjectTypeEnum;
import it.polimi.ingsw.model.cells.ShelfCell;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.utils.MatrixUtils;

import java.util.Map;

/**
 * This class contains the methods used to calculate the points that can be earned
 * by inserting adjacent object cars into the shelf. <br>
 *
 *
 * @author Luca Guffanti
 */
public class EndGameAdjacencyGoalChecker {

    /**
     * Map that associates the total dimension of a group of adjacent equal object cards to the points that will
     * be earned by the player.
     */
    private static final Map<Integer, Integer> matchesToPointsMap = Map.of(
            0, 0,
            1, 0,
            2, 0,
            3, 2,
            4, 3,
            5, 5,
            6, 8
    );

    /**
     * This method calculates and returns the points made by a player based on adjacent cards into the shelf.
     * @param player the player whose shelf will be checked for adjacent cards
     * @return the total points earned by the player
     */
    public static int calculateAdjacencyPoints(Player player) {
        ShelfCell[][] shelfMatrix = player.getShelf().getCells();
        int shelfLength = player.getShelf().getLengthInCells();
        int shelfHeight = player.getShelf().getHeightInCells();
        int totalPoints = 0;

        boolean[][] referenceMatrix = MatrixUtils.createEmptyMatrix(shelfLength, shelfHeight);

        int[] highestOccupiedCell = player.getShelf().getHighestOccupiedCell();
        int count;
        for (ObjectTypeEnum type : ObjectTypeEnum.values()) {
            for (int y = 0; y < shelfHeight; y++) {
                count = 0;
                for (int x = 0; x < shelfLength; x++) {
                    if (!referenceMatrix[y][x] && shelfMatrix[y][x].getCellCard().isPresent() && shelfMatrix[y][x].getCellCard().get().getType().equals(type)) {
                        count = MatrixUtils.calculateAdjacentShelfCardsGroupDimension(
                                shelfMatrix,
                                shelfLength,
                                shelfHeight,
                                referenceMatrix,
                                type,
                                x,
                                y,
                                highestOccupiedCell
                        );

                        // System.out.print("I found " + count + " for type: " + type.toString()+". ");
                        if (count > 6) {
                            count = 6;
                        }
                        // System.out.print("Giving " + matchesToPointsMap.get(count)+ " points.\n");
                        totalPoints += matchesToPointsMap.get(count);
                    }
                }
            }
        }

        return totalPoints;
    }

}
