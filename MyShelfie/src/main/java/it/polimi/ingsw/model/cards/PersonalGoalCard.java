package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Shelf;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class PersonalGoalCard extends GoalCard{

    /**
     * Contains the mapping between correct matches of the subpattern and the
     * points awarded.
     *
     * <table>
     *     <tr><td><b>Matches</b> </td><td><b>Points</b></td></tr>
     *     <tr><td>0</td> <td>0</td></tr>
     *     <tr><td>1</td> <td>1</td></tr>
     *     <tr><td>2</td> <td>2</td></tr>
     *     <tr><td>3</td> <td>4</td></tr>
     *     <tr><td>4</td> <td>6</td></tr>
     *     <tr><td>5</td> <td>9</td></tr>
     *     <tr><td>6</td> <td>12</td></tr>
     * </table>
     */
    private static final Map<Integer, Integer> matchesToPointsMap = Map.of(
            0, 0,
            1, 1,
            2, 2,
            3, 4,
            4, 6,
            5, 9,
            6, 12
    );

    public PersonalGoalCard(String id, PatternRules personalPatternRules) {
         super(id, personalPatternRules);
    }

    /**
     * @author Luca Guffanti
     * @param player the player who owns the shelf that should be controlled
     * @return the number of matches found between the shelf cells and the pattern that characterizes the personalGoal
     * card
     * */
    @Override
    protected int checkPattern(Player player) {
        int numOfMatches = 0;
        Shelf playerShelf = player.getShelf();
        SubPattern patternToMatch = patternRules.getSubPattern();

        Set<SubPatternCell> cells = patternToMatch.getCoveredCells();

        for (SubPatternCell cell : cells) {
            int x = cell.getX();
            int y = cell.getY();

            if (playerShelf.getCell(x, y).equals(cell)) {
                numOfMatches++;
            }
        }

        return numOfMatches;
    }

    //TODO RETURN EQUAL CARD SHOULD BE DEFINED AS ABSTRACT IN THE SUPERCLASS

    /**
     * @author Luca Guffanti
     * @param player the player whose shelf will be checked
     * @return the points obtained by (partially or completely) accomplishing the personal goal
     */
    public int calculatePoints(Player player){

        int points;
        int matches = checkPattern(player);
        points = matchesToPointsMap.get(matches);
        return points;
    }

    @Override
    public GoalCard returnEqualCard(){
        return new PersonalGoalCard(this.id, this.patternRules);
    }
}
