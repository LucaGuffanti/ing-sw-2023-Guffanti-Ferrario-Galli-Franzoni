package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Shelf;
import it.polimi.ingsw.model.utils.complexMethodsResponses.CheckShelfPortionResult;

import java.util.*;

import static it.polimi.ingsw.model.utils.Constants.SHELF_HEIGHT;
import static it.polimi.ingsw.model.utils.Constants.SHELF_LENGTH;

/**
 * CommonGoalCards represents a goal for all players. When the goal is reached,
 * the user will get a PointCard relatively to how many player have previously completed the same goal.
 *
 * The pattern matching algorithm is implemented directly in this class.
 *
 * @author Daniele Ferrario
 *
 */
public class CommonGoalCard extends GoalCard{
    private List<PointCard> pointsCards = new ArrayList<>();

    public CommonGoalCard(String id, List<PointCard> pointsCard, PatternRules patternRules) {
        super(id, patternRules);
        this.pointsCards = pointsCard;
    }

    /**
     * This method calculates the points the user will receive
     * based on how many players have already reached the goal.
     * The most valuable PointCard ( the pointCard on the top of
     * pointsCard list) will be returned to the player and popped
     * out of pointsCards.
     *
     * @param player        The current player which shelf has to be analyzed.
     * @return PointCard    A card representing game points.
     */
    public PointCard calculatePoints(Player player){

        checkPattern(player);

        return null; // @todo: add point calculation
    }

    /**
     * This methods will return an exact copy of this card
     *
     * @return GoalCard
     */
    public GoalCard returnEqualCard(){
        return new CommonGoalCard(id, pointsCards, patternRules);
    }
    @Override
    protected int checkPattern(Player player){

        Shelf shelf = player.getShelf();
        CommonPatternRules rules = (CommonPatternRules) super.patternRules;

        int[][] previouslyFoundFlags = createEmptyMatrix(SHELF_LENGTH, SHELF_HEIGHT);

        // Counter of every subPattern we find
        int totalSubPatternsFound = 0;

        // Counter for subPatterns we find with only one type
        HashMap<ObjectTypeEnum, Integer> numSubPatternsByType = new HashMap<>();
        for (ObjectTypeEnum objectType: ObjectTypeEnum.values()) {
            numSubPatternsByType.put(objectType, 0);
        }


        // Iterate the subPattern through every cell of the shelf
        boolean radiallySymmetric = patternRules.getPositionalPattern().isRadiallySymmetric();

        // @todo: ADDING ANALYSIS ON ROTATED SUBPATTERN


        for (int y = 0; y < SHELF_HEIGHT; y++) {
            for (int x = 0; x < SHELF_LENGTH; x++) {

                // Check if the shelf portion contains a valid subpattern
                CheckShelfPortionResult result = checkShelfPortion(rules, shelf, previouslyFoundFlags, x, y);

                // Checking if found
                if (result.isValid()) {

                    // Marking the shelf cells we found
                    // @todo: should doing this directly in checkShelfPortion
                    previouslyFoundFlags = markFoundCells(previouslyFoundFlags, rules.getPositionalPattern(), x, y);


                    totalSubPatternsFound += 1;

                    // If the subPattern we found contains only one type, we count it in the hashMap
                    if (result.getCommonType().isPresent()) {
                        ObjectTypeEnum key = result.getCommonType().get();
                        numSubPatternsByType.put(key, numSubPatternsByType.get(key) + 1);
                    }

                }

            }
        }

        return totalSubPatternsFound;
    }

    static private int[][] markFoundCells(int[][] previouslyFoundFlags, SubPattern subPattern, int shelfX, int shelfY){

        int[][] newMatrix = previouslyFoundFlags;

        for (SubPatternCell coveredCell: subPattern.getCoveredCells()) {
            int x = coveredCell.getX();
            int y = coveredCell.getY();

            newMatrix[shelfX+x][shelfY+y] = 1;
        }

        return newMatrix;
    }
    private CheckShelfPortionResult checkShelfPortion(CommonPatternRules rules, Shelf shelf, int[][] foundedShelf, int shelfX, int shelfY){

        Optional<ObjectTypeEnum> commonColor = Optional.empty();

        SubPattern subPattern = rules.getPositionalPattern();

        int sHeight = subPattern.getHeight();
        int sLength = subPattern.getLength();
        Set<ObjectTypeEnum> diffColors = new HashSet<>();

        if(shelfX+sLength > shelf.getLengthInCells() || shelfY+sHeight > shelf.getHeightInCells())
            return new CheckShelfPortionResult(false, commonColor);

        for (SubPatternCell coveredCell: subPattern.getCoveredCells()) {
            int x = coveredCell.getX();
            int y = coveredCell.getY();


            if(!shelf.getCell(shelfX + x, shelfY + y).isFull())
                return new CheckShelfPortionResult(false, commonColor);
            if(foundedShelf[shelfX+x][shelfY+y] == 1)
                return new CheckShelfPortionResult(false, commonColor);

            diffColors.add(shelf.getCell(shelfX+x, shelfY+y).getCellCard().getType());

        }


        if(diffColors.size() > subPattern.getMaxDifferentTypes() || diffColors.size() < subPattern.getMinDifferentTypes())
            return new CheckShelfPortionResult(false, commonColor);
        else if(diffColors.size() == 1){
            commonColor = Optional.of(shelf.getCell(shelfX, shelfY).getCellCard().getType());
        }

        return new CheckShelfPortionResult(true, commonColor);

    }
    private int[][] createEmptyMatrix(int l, int h){
        int[][] cells = new int[l][h];

        for (int i = 0; i < l; i++) {
            for (int j = 0; j < h; j++) {
                cells[i][j] = 0;

            }
        }

        return cells;
    }
}
