package it.polimi.ingsw.model.cards.goalCards;

import it.polimi.ingsw.model.cards.*;
import it.polimi.ingsw.model.cells.ShelfCell;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Shelf;
import it.polimi.ingsw.model.utils.MatrixUtils;
import it.polimi.ingsw.model.utils.complexMethodsResponses.CheckShelfPortionResult;

import java.util.*;

import static it.polimi.ingsw.model.utils.Constants.SHELF_HEIGHT;
import static it.polimi.ingsw.model.utils.Constants.SHELF_LENGTH;

/**
 * Since most of the CommonGoalCards share the same logic to detect a pattern, because of the same
 * rules applied to a repeated (or not) <b>fixed shaped Pattern</b>, this class contains all the implementation
 * to the pattern matching algorithm.
 * @see Pattern
 *
 * This type of cards are loaded during runtime from a json File
 * @see it.polimi.ingsw.model.utils.JsonGoalCardsParser
 *
 *
 * @author Daniele Ferrario
 *
 */
public class FixedPatternCommonGoalCard extends CommonGoalCard implements FixedPatternShapedCard {

    // The fixed pattern to detect
    private Pattern pattern;
    // The fixed pattern rotated 90 degrees anticlockwise
    private Pattern rotatedPattern;
    // The minimum number of occurrences of the pattern in the player's shelf
    private int minNumberOfOccurrences;
    // this indicates that the pattern should also be checked in its rotated version
    private boolean shouldRotate;
    // This indicates that the patterns can be adjacent
    private boolean admitsAdjacency;
    // This indicates that the patterns should share the same color/type
    // (implies that the single patterns should contain at max 1 type)
    private boolean patternsShareSameColor;

    public Pattern getPattern() {
        return pattern;
    }

    public Pattern getRotatedPattern() {
        return rotatedPattern;
    }

    public int getMinNumberOfOccurrences() {
        return minNumberOfOccurrences;
    }

    public boolean isShouldRotate() {
        return shouldRotate;
    }

    public boolean isAdmitsAdjacency() {
        return admitsAdjacency;
    }

    public boolean isPatternsShareSameColor() {
        return patternsShareSameColor;
    }

    public FixedPatternCommonGoalCard(String id, ArrayList<PointCard> pointsCard, Pattern pattern, int minNumberOfOccurrences, boolean shouldRotate, boolean admitsAdjacency, boolean patternsShareSameColor) {
        super(id, pointsCard);
        this.pattern = pattern;
        this.minNumberOfOccurrences = minNumberOfOccurrences;
        this.shouldRotate = shouldRotate;
        this.admitsAdjacency = admitsAdjacency;
        this.patternsShareSameColor = patternsShareSameColor;
        this.rotatedPattern = rotatePattern(pattern);
    }

    public FixedPatternCommonGoalCard(FixedPatternCommonGoalCard f) {
        super(f.getId(), f.getPointsCards());
        this.pattern = new Pattern(f.getPattern());
        this.minNumberOfOccurrences = f.getMinNumberOfOccurrences();
        this.shouldRotate = f.isShouldRotate();
        this.admitsAdjacency = f.isAdmitsAdjacency();
        this.patternsShareSameColor = isPatternsShareSameColor();
        this.rotatedPattern = rotatePattern(this.pattern);

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
    @Override
    public int calculatePoints(Player player){



        if(pointsCards.size() == 0){
            return 0;
        }

        // If there are no more pointsCards, don't compute the pattern matching algorithm
        // @TODO: Maybe rise an exception

        // Start pattern matching algorithm
        int patternsFound = checkPattern(player);

        // If less patterns than requested are found, the goal has not been reached
        if(patternsFound < minNumberOfOccurrences) {
            return 0;
        }

        // Get the highest pointCard and remove it from the pile
        PointCard highestPointCard = this.pointsCards.get(pointsCards.size()-1);
        this.pointsCards.remove(pointsCards.size()-1);

        return highestPointCard.getPointsGiven();

    }

    /**
     *
     * This method finds the number of Patterns in the player's shelf
     *
     * @see Pattern
     * @param player        The current player whom shelf has to be analyzed
     * @return int          The number of subPatterns found in the player shelf.
     */
    @Override
    protected int checkPattern(Player player){

        Shelf shelf = player.getShelf();

        boolean[][] previouslyFoundFlags = MatrixUtils.createEmptyMatrix(SHELF_LENGTH, SHELF_HEIGHT);

        // Counter of every pattern we find
        int totalSubPatternsFound = 0;

        // Counter for subPatterns we find with only one type
        HashMap<ObjectTypeEnum, Integer> numSubPatternsByType = new HashMap<>();
        for (ObjectTypeEnum objectType: ObjectTypeEnum.values()) {
            numSubPatternsByType.put(objectType, 0);
        }


        int cycles = 0;
        Pattern s = this.pattern;

        // Repeat the process with the rotated pattern (if necessary).
        while(cycles < (this.shouldRotate ? 2 : 1)) {

            // Iterate the pattern through every cell of the shelf.
            for (int y = 0; y < SHELF_HEIGHT; y++) {
                for (int x = 0; x < SHELF_LENGTH; x++) {

                    // Check if the shelf portion contains a valid subpattern.
                    CheckShelfPortionResult result = checkShelfPortion(s, shelf,  previouslyFoundFlags, x, y);
                    previouslyFoundFlags = result.getUpdatedFoundCellsMatrix();

                    // Checking if found
                    if (result.isValid()) {

                        totalSubPatternsFound += 1;

                        // If the pattern we found contains only one type, we count it in the hashMap.
                        if (result.getCommonType().isPresent()) {
                            ObjectTypeEnum key = result.getCommonType().get();
                            numSubPatternsByType.put(key, numSubPatternsByType.get(key) + 1);
                        }

                    }

                }
            }


            cycles+=1;
            s = this.rotatedPattern;

        }

        return totalSubPatternsFound;
    }


    /**
     *
     * This method checks for the Pattern in a portion of the shelf
     *
     * @param pattern               The pattern to match
     * @param shelf                 The player's shelf.
     * @param oldFoundCellsMatrix   A matrix representing the cells which has been found being part of a pattern in previous iteration.
     * @param shelfX                X coordinate of the shelf portion
     * @param shelfY                Y coordinate of the shelf portion
     * @return An object containing a "pattern found" flag and an optional ObjectType which every ShelfCell in the pattern share.
     */
    private CheckShelfPortionResult checkShelfPortion(Pattern pattern, Shelf shelf, boolean[][] oldFoundCellsMatrix, int shelfX, int shelfY){

        Optional<ObjectTypeEnum> commonColor = Optional.empty();
        boolean[][] foundCellsMatrix = MatrixUtils.clone(oldFoundCellsMatrix);

        int sHeight = pattern.getHeight();
        int sLength = pattern.getLength();
        Set<ObjectTypeEnum> diffColors = new HashSet<>();

        // Check if Pattern exceed the shelf frame in this position.
        if(shelfX+sLength > shelf.getLengthInCells() || shelfY+sHeight > shelf.getHeightInCells())
            return new CheckShelfPortionResult(false, commonColor, oldFoundCellsMatrix);

        // Iterate through the pattern coveredCells.
        for (PatternCell coveredCell: pattern.getCoveredCells()) {

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

            //Marking the shelf cell (and its adjacent if necessary)
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
        if(diffColors.size() > pattern.getMaxDifferentTypes() || diffColors.size() < pattern.getMinDifferentTypes())
            return new CheckShelfPortionResult(false, commonColor, oldFoundCellsMatrix);
            // If the pattern share only one color
        else if(diffColors.size() == 1){
            commonColor = Optional.of(shelf.getCell(shelfX, shelfY).getCellCard().get().getType());
        }

        // If each criterion is met, also return the updated matrix with the new cells found
        return new CheckShelfPortionResult(true, commonColor, foundCellsMatrix);

    }

    /**
     * Performs a 90° counter-clockwise rotation of the cells of the subpattern, adjusting some parameters (length, height).
     * If the original pattern is radially symmetric then the resultant pattern is equal to the first one.
     *
     * @param pattern the pattern to be rotated
     * @return the rotated pattern, of a copy of the original if it's radiallySymmetric
     * @author Luca Guffanti
     */
    private Pattern rotatePattern(Pattern pattern) {

        int rotatedMinDifferentTypes = pattern.getMinDifferentTypes();
        int rotatedMaxDifferentTypes = pattern.getMaxDifferentTypes();

        int rotatedHeight;
        int rotatedLength;

        Set<PatternCell> rotatedCells;

        if (!this.shouldRotate) {
            rotatedHeight = pattern.getHeight();
            rotatedLength = pattern.getLength();
            rotatedCells = new HashSet<>(pattern.getCoveredCells());
        } else {
            rotatedHeight = pattern.getLength();
            rotatedLength = pattern.getHeight();

            rotatedCells = new HashSet<>();

            // performs the rotation. NEEDS STRICT TESTING
            int originalLength = pattern.getLength();
            for (PatternCell cell : pattern.getCoveredCells()) {
                rotatedCells.add(
                        new PatternCell(
                                cell.getY(),
                                originalLength - 1 - cell.getX(),
                                cell.getAdmittedType()
                        )
                );
            }
        }

        return new Pattern(
                rotatedHeight,
                rotatedLength,
                rotatedCells,
                rotatedMinDifferentTypes,
                rotatedMaxDifferentTypes
        );
    }

    @Override
    public GoalCard returnEqualCard(){
        return new FixedPatternCommonGoalCard(this);
    }

}
