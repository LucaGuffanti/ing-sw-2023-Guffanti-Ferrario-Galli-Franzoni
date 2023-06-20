package it.polimi.ingsw.server.model.cards.goalCards;

import it.polimi.ingsw.server.model.cards.*;
import it.polimi.ingsw.server.model.cards.*;
import it.polimi.ingsw.server.model.cells.ShelfCell;
import it.polimi.ingsw.server.model.player.Player;
import it.polimi.ingsw.server.model.player.Shelf;
import it.polimi.ingsw.server.model.utils.JsonFixedPatternGoalCardsParser;
import it.polimi.ingsw.server.model.utils.MatrixUtils;
import it.polimi.ingsw.server.model.utils.complexMethodsResponses.CheckShelfPortionResult;

import java.util.*;

import static it.polimi.ingsw.server.model.utils.Constants.SHELF_HEIGHT;
import static it.polimi.ingsw.server.model.utils.Constants.SHELF_LENGTH;

/**
 * Since most of the CommonGoalCards share the same logic to detect a pattern because of the same
 * rules applied to a repeated (or not) <b>fixed shaped Pattern</b>, this class contains all the implementation
 * to the pattern matching algorithm.<br>
 * Cards of this type are loaded during runtime from a json file.
 *
 * @see FixedPatternShapedCard
 * @see Pattern
 * @see JsonFixedPatternGoalCardsParser
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

    public FixedPatternCommonGoalCard(String id, Pattern pattern, int minNumberOfOccurrences, boolean shouldRotate, boolean admitsAdjacency, boolean patternsShareSameColor) {
        super(id);
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
        this.patternsShareSameColor = f.isPatternsShareSameColor();
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

        // Counter for every pattern with we find indexed by their only type.
        // ex [ {Cat, 2}, {Plant, 1}, ... ] => 2 patterns with Cat tiles only and 1 pattern of Plant tiles only
        HashMap<ObjectTypeEnum, Integer> numSubPatternsByCommonType = new HashMap<>();
        for (ObjectTypeEnum objectType: ObjectTypeEnum.values()) {
            numSubPatternsByCommonType.put(objectType, 0);
        }


        int cycles = 0;
        Pattern s = this.pattern;

        // Repeat the process with the rotated pattern (if necessary, i.e. when the pattern is not radially symmetric).
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
                        // Used for cards where groups of tiles with the same type are required.
                        if (result.getCommonType().isPresent()) {
                            ObjectTypeEnum key = result.getCommonType().get();
                            numSubPatternsByCommonType.put(key, numSubPatternsByCommonType.get(key) + 1);
                        }

                    }

                }
            }


            cycles+=1;
            s = this.rotatedPattern;

        }

        int maxValidPatternsFound;
        // If the patterns should share the same color, pick the number of occurrences
        // relative to the most frequent color/type
        if(patternsShareSameColor){
            maxValidPatternsFound= Collections.max(numSubPatternsByCommonType.values());
        }else{
            // Else, pick every pattern found
            maxValidPatternsFound = totalSubPatternsFound;
        }
        return maxValidPatternsFound;
    }


    /**
     *
     * This method checks for the Pattern in a portion of the shelf
     *
     * @param pattern               The pattern to match
     * @param shelf                 The player's shelf.
     * @param oldFoundCellsMatrix   A matrix representing the cells which has been found being part of a pattern in a previous iteration.
     * @param shelfX                X coordinate of the shelf portion
     * @param shelfY                Y coordinate of the shelf portion
     * @return An object containing a "pattern found" flag and the optional ObjectType which every ShelfCell in the pattern share.
     */
    private CheckShelfPortionResult checkShelfPortion(Pattern pattern, Shelf shelf, boolean[][] oldFoundCellsMatrix, int shelfX, int shelfY){

        // There can be a common type between the found tiles
        Optional<ObjectTypeEnum> commonColor = Optional.empty();

        // This is the matrix that contains the cells we found compatible with the pattern in this and the previous algorithm iteration.
        boolean[][] foundCellsMatrix = MatrixUtils.clone(oldFoundCellsMatrix);

        // This is the matrix that contains flags for the position of pattern cells and its adjacent ones.
        // It's used to check weather or not there is at least one adjacent cell containing a tile
        // with the same common type as the one of the pattern.
        boolean[][] adjacentPatternCellsMatrix = new boolean[SHELF_HEIGHT][SHELF_LENGTH];

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

            //Marking the shelf cell
            foundCellsMatrix[absY][absX] = true;

            if(!admitsAdjacency) {
                if (absX + 1 < SHELF_LENGTH)
                    adjacentPatternCellsMatrix[absY][absX + 1] = true;
                if (absY + 1 < SHELF_HEIGHT)
                    adjacentPatternCellsMatrix[absY + 1][absX] = true;
                if (absX - 1 >= 0)
                    adjacentPatternCellsMatrix[absY][absX - 1] = true;
                if (absY - 1 >= 0)
                    adjacentPatternCellsMatrix[absY - 1][absX] = true;
            }
        }


        // If the subpattern cells contains more different colors than requested
        if(diffColors.size() > pattern.getMaxDifferentTypes() || diffColors.size() < pattern.getMinDifferentTypes())
            return new CheckShelfPortionResult(false, commonColor, oldFoundCellsMatrix);

        // If the pattern share only one color
        else if(diffColors.size() == 1){
            commonColor = Optional.of(shelf.getCell(shelfX, shelfY).getCellCard().get().getType());

            // If the patterns should be separated, no cells adjacent to pattern has to contain a tile with the same color.
            // This implies that if there are more cells with the same tile type in the adjacentMatrix than the ones covered
            // with the pattern, then the criteria is not met.
            if(!admitsAdjacency){

                int sameTypeTilesCount = 0;
                int max = pattern.getCoveredCells().size(); //
                for(int j = 0; j<SHELF_HEIGHT; j++){
                    for(int i=0; i<SHELF_LENGTH; i++){
                        if(adjacentPatternCellsMatrix[j][i]){

                            Optional<ObjectCard> cardOpt = shelf.getCell(i, j).getCellCard();
                            ObjectCard card;


                            if(cardOpt.isPresent()){
                                card = cardOpt.get();
                                if(card.getType().equals(commonColor.get())){
                                    sameTypeTilesCount +=1;
                                }
                            }
                        }
                    }

                }

                // This means that in a neighborhood of the pattern cells, there is at least one
                // cell with a tile of the same type.
                if(sameTypeTilesCount != max)
                    return new CheckShelfPortionResult(false, commonColor, oldFoundCellsMatrix);

            }

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
