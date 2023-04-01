package it.polimi.ingsw.model.cards.goalCards;

import it.polimi.ingsw.model.cards.PointCard;
import it.polimi.ingsw.model.player.Player;

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

    // @todo: to complete with adjacency algorithm
    @Override
    protected int checkPattern(Player player) {
        return 1;
    }

    @Override
    public GoalCard returnEqualCard() {
        return new SnakesCommonGoalCard(this);
    }
}
