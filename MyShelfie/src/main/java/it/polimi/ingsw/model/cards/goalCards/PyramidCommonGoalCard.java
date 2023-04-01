package it.polimi.ingsw.model.cards.goalCards;


import it.polimi.ingsw.model.cards.PointCard;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Shelf;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * This class describes the goalCard that requires the building of a pyramid of ObjectCards. <br>
 * The shelf configuration should be equal (or equal when mirrored) to one of the following schemas: <br>
 * <table>
 *     <tr>
 *         <td>O</td><td>X</td><td>X</td><td>X</td><td>X</td>
 *     </tr>
 *     <tr>
 *         <td>O</td><td>O</td><td>X</td><td>X</td><td>X</td>
 *     </tr>
 *     <tr>
 *         <td>O</td><td>O</td><td>O</td><td>X</td><td>X</td>
 *     </tr>
 *     <tr>
 *         <td>O</td><td>O</td><td>O</td><td>O</td><td>X</td>
 *     </tr>
 *     <tr>
 *         <td>O</td><td>O</td><td>O</td><td>O</td><td>O</td>
 *     </tr>
 *     <tr>
 *         <td>O</td><td>O</td><td>O</td><td>O</td><td>O</td>
 *     </tr>
 * </table>
 * or
 * <table>
 *     <tr>
 *         <td>X</td><td>X</td><td>X</td><td>X</td><td>X</td>
 *     </tr>
 *     <tr>
 *         <td>O</td><td>X</td><td>X</td><td>X</td><td>X</td>
 *     </tr>
 *     <tr>
 *         <td>O</td><td>O</td><td>X</td><td>X</td><td>X</td>
 *     </tr>
 *     <tr>
 *         <td>O</td><td>O</td><td>O</td><td>X</td><td>X</td>
 *     </tr>
 *     <tr>
 *         <td>O</td><td>O</td><td>O</td><td>O</td><td>X</td>
 *     </tr>
 *     <tr>
 *         <td>O</td><td>O</td><td>O</td><td>O</td><td>O</td>
 *     </tr>
 * </table>
 * Where O is an ObjectCard and X is an empty space
 * @author Luca Guffanti
 */

//@todo: This is actually a FixedPatternShapedCard
public class PyramidCommonGoalCard extends CommonGoalCard implements FreePatternShapedCard{
    public PyramidCommonGoalCard(String id, ArrayList<PointCard> pointCards) {
        super (id, pointCards);
    }

    public PyramidCommonGoalCard(String id) {
        super (id);
    }

    public PyramidCommonGoalCard(PyramidCommonGoalCard p) {
        super (p.getId(), new ArrayList<PointCard>(p.getPointsCards()));
    }

    @Override
    protected int checkPattern(Player player) {
        boolean completed = false;
        boolean shouldBeCalculated = true;

        Shelf playerShelf = player.getShelf();
        int shelfLength = playerShelf.getLengthInCells();
        int[] highestOccupiedCell = playerShelf.getHighestOccupiedCell();

        for (int i = 0; i < shelfLength; i++) {
            if (highestOccupiedCell[i] == 6) {
                shouldBeCalculated = false;
                break;
            }
        }

        if (shouldBeCalculated) {
            completed = true;
            for (int i = 0; i < shelfLength - 1; i++) {
                if (highestOccupiedCell[i] != highestOccupiedCell[i+1] - 1) {
                    completed = false;
                    break;
                }
            }
            if (!completed) {
                completed = true;
                for (int i = 0; i < shelfLength - 1; i++) {
                    if (highestOccupiedCell[i] != highestOccupiedCell[i + 1] + 1) {
                        completed = false;
                        break;
                    }
                }
            }
        }
        return completed ? 1 : 0;
    }

    @Override
    public GoalCard returnEqualCard() {
        return new PyramidCommonGoalCard(this);
    }
}
