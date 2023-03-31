package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.cells.ShelfCell;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Shelf;
import it.polimi.ingsw.model.utils.MatrixUtils;
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

    @Override
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

        boolean[][] previouslyFoundFlags = MatrixUtils.createEmptyMatrix(SHELF_LENGTH, SHELF_HEIGHT);

        // Counter of every subPattern we find
        int totalSubPatternsFound = 0;

        // Counter for subPatterns we find with only one type
        HashMap<ObjectTypeEnum, Integer> numSubPatternsByType = new HashMap<>();
        for (ObjectTypeEnum objectType: ObjectTypeEnum.values()) {
            numSubPatternsByType.put(objectType, 0);
        }

        CommonPatternRules castedPatternRules = (CommonPatternRules) patternRules;
        // Checking if radially symmetric.
        boolean shouldRotate = castedPatternRules.getShouldRotate();
        SubPattern subPattern = rules.getSubPattern();
        SubPattern rotatedSubPattern = rules.getRotatedSubPattern();

        int cycles = 0;
        SubPattern s = subPattern;

        // Repeat the process with the rotated subPattern (if necessary).
        while(cycles < (shouldRotate ? 2 : 1)) {

            // Iterate the subPattern through every cell of the shelf.
            for (int y = 0; y < SHELF_HEIGHT; y++) {
                for (int x = 0; x < SHELF_LENGTH; x++) {

                    // Check if the shelf portion contains a valid subpattern.
                    CheckShelfPortionResult result = checkShelfPortion(s, shelf,  previouslyFoundFlags, x, y, castedPatternRules.getAdmitsAdjacency());
                    previouslyFoundFlags = result.getUpdatedFoundCellsMatrix();

                    // Checking if found
                    if (result.isValid()) {

                        totalSubPatternsFound += 1;

                        // If the subPattern we found contains only one type, we count it in the hashMap.
                        if (result.getCommonType().isPresent()) {
                            ObjectTypeEnum key = result.getCommonType().get();
                            numSubPatternsByType.put(key, numSubPatternsByType.get(key) + 1);
                        }

                    }

                }
            }


            cycles+=1;
            s = rotatedSubPattern;

        }

        return totalSubPatternsFound;
    }


    /**
     *
     * This method checks for the subpattern in a portion of the shelf, based on the patternRules.
     * @param subPattern            The subPattern to match
     * @param shelf                 The player shelf.
     * @param oldFoundCellsMatrix   A matrix representing the cells which has been found being part of a subPattern in previous iteration.
     * @param shelfX                X coordinate of the shelf portion
     * @param shelfY                Y coordinate of the shelf portion
     * @return An object containing a "subPattern found" flag and an optional ObjectType which every ShelfCell in the subPattern share.
     */
    private CheckShelfPortionResult checkShelfPortion(SubPattern subPattern, Shelf shelf, boolean[][] oldFoundCellsMatrix, int shelfX, int shelfY, boolean admitsAdjacency){

        Optional<ObjectTypeEnum> commonColor = Optional.empty();
        boolean[][] foundCellsMatrix = MatrixUtils.clone(oldFoundCellsMatrix);

        int sHeight = subPattern.getHeight();
        int sLength = subPattern.getLength();
        Set<ObjectTypeEnum> diffColors = new HashSet<>();

        // Check is SubPattern exceed the shelf frame in this position.
        if(shelfX+sLength > shelf.getLengthInCells() || shelfY+sHeight > shelf.getHeightInCells())
            return new CheckShelfPortionResult(false, commonColor, oldFoundCellsMatrix);

        // Iterate through the subPattern covberedCells.
        for (SubPatternCell coveredCell: subPattern.getCoveredCells()) {

            // Absolute coordinates.
            int absX = shelfX + coveredCell.getX();
            int absY = shelfY + coveredCell.getY();

            // If the shelf cell is empty.
            if(shelf.getCell(absX, absY).getCellCard().isEmpty())
                return new CheckShelfPortionResult(false, commonColor, oldFoundCellsMatrix);


            // If the cell has been previously found in a subpattern (or adjacent to it when requested).
            if(oldFoundCellsMatrix[absY][absX])
                return new CheckShelfPortionResult(false, commonColor, oldFoundCellsMatrix);

            // Saving the current color associated to the card
            ShelfCell sc = shelf.getCell(absX, absY);
            ObjectCard oc = sc.getCellCard().get();
            diffColors.add(oc.getType());

            //Marking the shelf cell and its adjacent
            foundCellsMatrix[absY][absX] = true;
            if(!admitsAdjacency) {
                if (absX + 1 < SHELF_LENGTH)
                    foundCellsMatrix[absY][absX + 1] = true;
                if (absY + 1 < SHELF_HEIGHT)
                    foundCellsMatrix[absY + 1][absX] = true;
                if (absX - 1 >= 0)
                    foundCellsMatrix[absY][absX - 1] = true;
                if (absY - 1 >= 0)
                    foundCellsMatrix[absY - 1][absX] = true;
            }
            /*
            System.out.println("x: "+absY+", y: "+ absY);
            MatrixUtils.printMatrix(foundCellsMatrix);
            */
        }


        // If the subpattern cells contains more different colors than requested
        if(diffColors.size() > subPattern.getMaxDifferentTypes() || diffColors.size() < subPattern.getMinDifferentTypes())
            return new CheckShelfPortionResult(false, commonColor, oldFoundCellsMatrix);
        // If the subPattern share only one color
        else if(diffColors.size() == 1){
            commonColor = Optional.of(shelf.getCell(shelfX, shelfY).getCellCard().get().getType());
        }

        // If each criterion is met, also return the updated matrix with the new cells found
        return new CheckShelfPortionResult(true, commonColor, foundCellsMatrix);

    }

}
