package it.polimi.ingsw.server.model.cards.goalCards;

import it.polimi.ingsw.server.model.cards.ObjectTypeEnum;
import it.polimi.ingsw.server.model.cards.Pattern;
import it.polimi.ingsw.server.model.cards.PatternCell;
import it.polimi.ingsw.server.model.cells.ShelfCell;
import it.polimi.ingsw.server.model.player.Player;
import it.polimi.ingsw.server.model.player.Shelf;

import java.util.Map;
import java.util.Set;

/**
 * A PersonalGoalCard represents a shelf configuration to match by the
 * card owner. The Pattern height and length match the shelf's frame dimensions.
 *
 * @author Luca Guffanti, Daniele Ferrario
 */
public class PersonalGoalCard extends GoalCard implements FixedPatternShapedCard {

    // The fixed pattern to detect
    private Pattern pattern;

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

    public PersonalGoalCard(String id, Pattern pattern) {
         super(id);
         this.pattern = new Pattern(pattern);
    }

    public PersonalGoalCard(PersonalGoalCard p) {
        super(p.getId());
        this.pattern = p.getPattern();
    }


    /**
     * @author Luca Guffanti
     * @param player the player who owns the shelf that should be controlled
     * @return the number of matches found between the shelf cells and the pattern that characterizes the personalGoal
     * card
     * */

    /**
     * this method calculates the points made by a player who's completing his own personal goal
     * card
     * @param player the player whose shelf will be checked
     * @return the total number of points achieved by the player
     */
    @Override
    protected int checkPattern(Player player) {
        int numOfMatches = 0;
        Shelf playerShelf = player.getShelf();
        Pattern patternToMatch = this.pattern;

        // retrieving the cells from the pattern that characterizes the personal goal
        Set<PatternCell> cells = patternToMatch.getCoveredCells();

        for (PatternCell cell : cells) {
            int x = cell.getX();
            int y = cell.getY();


            if (playerShelf.getCell(x,y).getCellCard().isPresent()) {
                ShelfCell cellAtXY = playerShelf.getCell(x, y);
                ObjectTypeEnum typeAtXY = cellAtXY.getCellCard().get().getType();

                if (typeAtXY.equals(cell.getAdmittedType().get())) {
                    numOfMatches++;
                }

            }

        }
        return numOfMatches;
    }



    /**
     * This method calls the calculation of points acquired by the player who's completing the shelf
     * @param player the player whose shelf will be checked
     * @return the points obtained by (partially or completely) accomplishing the personal goal
     */
    @Override
    public int calculatePoints(Player player){

        int points;
        int matches = checkPattern(player);
        points = matchesToPointsMap.get(matches);
        return points;
    }

    /**
     * @return an equal card
     */
    @Override
    public GoalCard returnEqualCard(){
        return new PersonalGoalCard(this);
    }

    public Pattern getPattern() {
        return pattern;
    }
}
