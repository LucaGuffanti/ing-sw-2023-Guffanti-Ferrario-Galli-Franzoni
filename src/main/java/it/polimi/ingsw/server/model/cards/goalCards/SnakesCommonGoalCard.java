package it.polimi.ingsw.server.model.cards.goalCards;

import it.polimi.ingsw.server.model.cards.ObjectTypeEnum;
import it.polimi.ingsw.server.model.cards.PointCard;
import it.polimi.ingsw.server.model.cells.ShelfCell;
import it.polimi.ingsw.server.model.player.Player;
import it.polimi.ingsw.server.model.utils.MatrixUtils;

import java.util.ArrayList;
/**
 * This class describes the goalCard that requires the building of 4 separated groups of four adjacent
 * ObjectCards of the same type.<br>
 * <table>
 *     <tr>
 *         <td>X</td><td>C</td><td>X</td><td>X</td><td>X</td>
 *     </tr>
 *     <tr>
 *         <td>X</td><td>C</td><td>C</td><td>C</td><td>X</td>
 *     </tr>
 *     <tr>
 *         <td>X</td><td>A</td><td>B</td><td>B</td><td>B</td>
 *     </tr>
 *     <tr>
 *         <td>A</td><td>A</td><td>X</td><td>X</td><td>B</td>
 *     </tr>
 *     <tr>
 *         <td>A</td><td>X</td><td>A</td><td>X</td><td>X</td>
 *     </tr>
 *     <tr>
 *         <td>A</td><td>A</td><td>A</td><td>X</td><td>X</td>
 *     </tr>
 * </table>
 * Where A,B,C are an ObjectCard of different types and X is an empty space
 * @author Luca Guffanti, Daniele Ferrario
 */
public class SnakesCommonGoalCard extends CommonGoalCard implements FreePatternShapedCard{

    public SnakesCommonGoalCard(SnakesCommonGoalCard p) {
        super (p.getId(), new ArrayList<PointCard>(p.getPointsCards()));
    }

    public SnakesCommonGoalCard(String id) {
        super(id);
    }


    @Override
    protected int checkPattern(Player player) {
        ShelfCell[][] shelfMatrix = player.getShelf().getCells();
        int shelfLength = player.getShelf().getLengthInCells();
        int shelfHeight = player.getShelf().getHeightInCells();
        int totalPoints = 0;
        int[] highestOccupiedCell = player.getShelf().getHighestOccupiedCells();

        boolean[][] referenceMatrix = MatrixUtils.createEmptyMatrix(shelfLength, shelfHeight);

        int count;
        for (ObjectTypeEnum type : ObjectTypeEnum.values()) {
            for (int y = 0; y < shelfHeight; y++) {
                count = 0;
                for (int x = 0; x < shelfLength; x++) {
                    if (referenceMatrix[y][x] == false && shelfMatrix[y][x].getCellCard().isPresent() && shelfMatrix[y][x].getCellCard().get().getType().equals(type)) {
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

                        if (count == 4) {
                            totalPoints++;
                        }
                    }
                }
            }
        }
        return totalPoints == 4 ? 1 : 0;
    }

    @Override
    public GoalCard returnEqualCard() {
        return new SnakesCommonGoalCard(this.id);
    }
}
