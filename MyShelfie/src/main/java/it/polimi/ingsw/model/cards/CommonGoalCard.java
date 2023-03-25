package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.cells.ShelfCell;
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
 * The pattern matching algorithm is implemented directly in this class through checkPattern, which is
 * an interpreter of the patternRules associated to the card.
 *
 * @author Daniele Ferrario
 *
 */
public class CommonGoalCard extends GoalCard{
    private List<PointCard> pointsCards;

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
     * @param player        The current player whom shelf has to be analyzed.
     * @return PointCard    A card representing game points.
     */
    public PointCard calculatePoints(Player player){

        // If there are no more pointsCards, don't compute the pattern matching algorithm
        // @TODO: Maybe rise an exception

        PointCard result;
        if(pointsCards.size() == 0){
            try {
                result = CardBuilder.generatePointCardFromPointsGiven(0);
                return result;
            }catch (Exception ex){System.out.println(ex.getMessage());}
        }

        // Start pattern matching algorithm
        int subPatternsFound = checkPattern(player);

        // If less patterns than requested are found, the goal has not been reached
        if(subPatternsFound <  ((CommonPatternRules) patternRules).getMinNumberOfOccurrences()) {
            try {
                result = CardBuilder.generatePointCardFromPointsGiven(0);
                return result;
            } catch (Exception ex) {System.out.println(ex.getMessage());}
        }

        // Get the highest pointCard and remove it from the pile
        PointCard highestPointCard = this.pointsCards.get(pointsCards.size()-1);
        this.pointsCards.remove(pointsCards.size()-1);


        return highestPointCard;

    }

    /**
     * This methods will return an exact copy of this card
     *
     * @return GoalCard
     */
    public GoalCard returnEqualCard(){
        return new CommonGoalCard(id, pointsCards, patternRules);
    }

    /**
     *
     * This method finds the number of subPatterns in the player shelf
     *
     * @param player        The current player whom shelf has to be analyzed
     * @return int          The number of subPatterns found in the player shelf.
     */
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
        boolean radiallySymmetric = patternRules.getSubPattern().isRadiallySymmetric();

        // @todo: ADDING ANALYSIS ON ROTATED SUBPATTERN


        for (int y = 0; y < SHELF_HEIGHT; y++) {
            for (int x = 0; x < SHELF_LENGTH; x++) {

                // Check if the shelf portion contains a valid subpattern
                CheckShelfPortionResult result = checkShelfPortion(rules, shelf, previouslyFoundFlags, x, y);

                // Checking if found
                if (result.isValid()) {

                    // Marking the shelf cells we found
                    // @TODO should doing this directly in checkShelfPortion
                    previouslyFoundFlags = markFoundCells(previouslyFoundFlags, rules.getSubPattern().getCoveredCells(), x, y);


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


    /**
     *
     * This method checks for the subpattern in a portion of the shelf, based on the patternRules.
     * @param rules                 The rules for the pattern matching.
     * @param shelf                 The player shelf.
     * @param previouslyFoundCells  A matrix representing the cells which has been found being part of a subPattern in previous iteration.
     * @param shelfX                X coordinate of the shelf portion
     * @param shelfY                Y coordinate of the shelf portion
     * @return An object containing a "subPattern found" flag and an optional ObjectType which every ShelfCell in the subPattern share.
     */
    private CheckShelfPortionResult checkShelfPortion(CommonPatternRules rules, Shelf shelf, int[][] previouslyFoundCells, int shelfX, int shelfY){

        Optional<ObjectTypeEnum> commonColor = Optional.empty();

        SubPattern subPattern = rules.getSubPattern();

        int sHeight = subPattern.getHeight();
        int sLength = subPattern.getLength();
        Set<ObjectTypeEnum> diffColors = new HashSet<>();

        if(shelfX+sLength > shelf.getLengthInCells() || shelfY+sHeight > shelf.getHeightInCells())
            return new CheckShelfPortionResult(false, commonColor);

        for (SubPatternCell coveredCell: subPattern.getCoveredCells()) {
            int x = coveredCell.getX();
            int y = coveredCell.getY();


            // If the shelf cell is empty
            if(shelf.getCell(shelfX + x, shelfY + y).getCellCard().isEmpty())
                return new CheckShelfPortionResult(false, commonColor);

            // If the cell has been previously found in a subpattern or adjacent to it if requested
            if(previouslyFoundCells[shelfX+x][shelfY+y] == 1)
                return new CheckShelfPortionResult(false, commonColor);

            // Saving the current color associated to the card

            ShelfCell sc = shelf.getCell(shelfX+x, shelfY+y);
            ObjectCard oc = sc.getCellCard().get();
            diffColors.add(oc.getType());

        }

        // If the subpattern cells contains more different colors than requested
        if(diffColors.size() > subPattern.getMaxDifferentTypes() || diffColors.size() < subPattern.getMinDifferentTypes())
            return new CheckShelfPortionResult(false, commonColor);
        // If the subPattern share only one color
        else if(diffColors.size() == 1){
            commonColor = Optional.of(shelf.getCell(shelfX, shelfY).getCellCard().get().getType());
        }

        return new CheckShelfPortionResult(true, commonColor);

    }

    /**
     * @TODO: MOVING MARKING PROCESS DIRECTLY IN checkShelfPortion()
     * When a cell in the shelf has been found in a subPattern, it is marked
     * in this matrix.
     *
     * @param previouslyFoundFlags
     * @param subPatternCells           SubPattern cells to mark
     * @param shelfX                    X coordinate of the player shelf
     * @param shelfY                    Y coordinate of the player shelf
     * @return  an updated version of the matrix.
     */
    static private int[][] markFoundCells(int[][] previouslyFoundFlags, Set<SubPatternCell> subPatternCells, int shelfX, int shelfY){

        int[][] newMatrix = previouslyFoundFlags;

        for (SubPatternCell coveredCell: subPatternCells) {
            int x = coveredCell.getX();
            int y = coveredCell.getY();

            newMatrix[shelfX+x][shelfY+y] = 1;
        }

        return newMatrix;
    }

    /**
     * This method create an empty matrix
     * @param l
     * @param h
     * @return A matrix l x h filled with zeros.
     */
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
