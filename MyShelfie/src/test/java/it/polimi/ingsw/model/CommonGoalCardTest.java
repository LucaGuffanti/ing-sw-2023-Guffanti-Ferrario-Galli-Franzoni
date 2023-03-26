package it.polimi.ingsw.model;

import it.polimi.ingsw.model.cards.*;
import it.polimi.ingsw.model.cells.ShelfCell;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Shelf;
import it.polimi.ingsw.model.utils.MatrixUtils;
import it.polimi.ingsw.model.utils.exceptions.WrongNumberOfPlayersException;
import it.polimi.ingsw.model.utils.exceptions.WrongPointCardsValueGivenException;
import org.junit.Test;

import javax.swing.text.html.Option;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static it.polimi.ingsw.model.utils.Constants.SHELF_HEIGHT;
import static it.polimi.ingsw.model.utils.Constants.SHELF_LENGTH;

public class CommonGoalCardTest {

    /**
     * This test checks if this pattern is correctly found
     */
    @Test
    public void twoSquaresSameColorNonAdjacent_expectedValid() {

        final int NUMBER_OF_PLAYERS = 4;
        final int EXPECTED_POINT_CARD_VALUE = 8;

        // @TODO: ADDING THE CSV PARSER
        //Shelf shelf = new Shelf(SHELF_LENGTH, SHELF_HEIGHT, MatrixParser.parse("assets/shelf1.csv", SHELF_LENGTH, SHELF_HEIGHT));
        ShelfCell[][] cells = MatrixUtils.emptyShelfCellMatrixInit(SHELF_LENGTH, SHELF_HEIGHT);
        cells[1][1] = new ShelfCell(Optional.of(new ObjectCard(ObjectTypeEnum.CAT)));
        cells[2][1] = new ShelfCell(Optional.of(new ObjectCard(ObjectTypeEnum.CAT)));
        cells[1][2] = new ShelfCell(Optional.of(new ObjectCard(ObjectTypeEnum.CAT)));
        cells[2][2] = new ShelfCell(Optional.of(new ObjectCard(ObjectTypeEnum.CAT)));

        cells[3][3] = new ShelfCell(Optional.of(new ObjectCard(ObjectTypeEnum.CAT)));
        cells[3][4] = new ShelfCell(Optional.of(new ObjectCard(ObjectTypeEnum.CAT)));
        cells[4][3] = new ShelfCell(Optional.of(new ObjectCard(ObjectTypeEnum.CAT)));
        cells[4][4] = new ShelfCell(Optional.of(new ObjectCard(ObjectTypeEnum.CAT)));

        Shelf shelf = new Shelf(SHELF_LENGTH, SHELF_HEIGHT, cells);

        Player player = new Player(shelf, "testUser");

        // Card Data 1--------------
        Set<SubPatternCell> coveredCells = new HashSet<>();
        coveredCells.add(new SubPatternCell(0,0, Optional.empty()));
        coveredCells.add(new SubPatternCell(0,1, Optional.empty()));
        coveredCells.add(new SubPatternCell(1,0, Optional.empty()));
        coveredCells.add(new SubPatternCell(1,1, Optional.empty()));

        SubPattern subPattern = new SubPattern(2,2, coveredCells, 1, 1, true);
        CommonPatternRules rules = new CommonPatternRules(subPattern, 2, false);

        CommonGoalCard exampleCard;

        try {
            exampleCard = new CommonGoalCard("0", CardBuilder.generatePointsCards(NUMBER_OF_PLAYERS), rules);

        }catch(Exception ex){
            System.out.println(ex.getMessage());
            return;
        }

        assertEquals(EXPECTED_POINT_CARD_VALUE, exampleCard.calculatePoints(player).getPointsGiven());
        assertEquals(EXPECTED_POINT_CARD_VALUE - 2, exampleCard.calculatePoints(player).getPointsGiven());
        assertEquals(EXPECTED_POINT_CARD_VALUE - 4, exampleCard.calculatePoints(player).getPointsGiven());
        assertEquals(EXPECTED_POINT_CARD_VALUE - 6, exampleCard.calculatePoints(player).getPointsGiven());
        assertEquals(0, exampleCard.calculatePoints(player).getPointsGiven());
        assertEquals(0, exampleCard.calculatePoints(player).getPointsGiven());

    }

    @Test
    public void twoSquaresSameColorAdjacent_expectedNotValid() {

        final int NUMBER_OF_PLAYERS = 4;
        final int EXPECTED_POINT_CARD_VALUE = 8;

        // @TODO: ADDING THE CSV PARSER
        //Shelf shelf = new Shelf(SHELF_LENGTH, SHELF_HEIGHT, MatrixParser.parse("assets/shelf1.csv", SHELF_LENGTH, SHELF_HEIGHT));
        ShelfCell[][] cells = MatrixUtils.emptyShelfCellMatrixInit(SHELF_LENGTH, SHELF_HEIGHT);
        cells[1][1] = new ShelfCell(Optional.of(new ObjectCard(ObjectTypeEnum.CAT)));
        cells[1][2] = new ShelfCell(Optional.of(new ObjectCard(ObjectTypeEnum.CAT)));
        cells[2][1] = new ShelfCell(Optional.of(new ObjectCard(ObjectTypeEnum.CAT)));
        cells[2][2] = new ShelfCell(Optional.of(new ObjectCard(ObjectTypeEnum.CAT)));

        cells[3][2] = new ShelfCell(Optional.of(new ObjectCard(ObjectTypeEnum.CAT)));
        cells[4][2] = new ShelfCell(Optional.of(new ObjectCard(ObjectTypeEnum.CAT)));
        cells[3][3] = new ShelfCell(Optional.of(new ObjectCard(ObjectTypeEnum.CAT)));
        cells[4][3] = new ShelfCell(Optional.of(new ObjectCard(ObjectTypeEnum.CAT)));

        Shelf shelf = new Shelf(SHELF_LENGTH, SHELF_HEIGHT, cells);

        Player player = new Player(shelf, "testUser");

        // Card Data 1--------------
        Set<SubPatternCell> coveredCells = new HashSet<>();
        coveredCells.add(new SubPatternCell(0,0, Optional.empty()));
        coveredCells.add(new SubPatternCell(0,1, Optional.empty()));
        coveredCells.add(new SubPatternCell(1,0, Optional.empty()));
        coveredCells.add(new SubPatternCell(1,1, Optional.empty()));

        SubPattern subPattern = new SubPattern(2,2, coveredCells, 1, 1, true);
        CommonPatternRules rules = new CommonPatternRules(subPattern, 2, false);

        CommonGoalCard exampleCard;

        try {
            exampleCard = new CommonGoalCard("0", CardBuilder.generatePointsCards(NUMBER_OF_PLAYERS), rules);

        }catch(Exception ex){
            System.out.println(ex.getMessage());
            return;
        }

        assertEquals(0, exampleCard.calculatePoints(player).getPointsGiven());
    }

    @Test
    public void six1x2nonAdjacent_expectedValid() {

        final int NUMBER_OF_PLAYERS = 4;
        final int EXPECTED_POINT_CARD_VALUE = 8;

        // @TODO: ADDING THE CSV PARSER
        //Shelf shelf = new Shelf(SHELF_LENGTH, SHELF_HEIGHT, MatrixParser.parse("assets/shelf1.csv", SHELF_LENGTH, SHELF_HEIGHT));
        ShelfCell[][] cells = MatrixUtils.emptyShelfCellMatrixInit(SHELF_LENGTH, SHELF_HEIGHT);
        cells[0][0] = new ShelfCell(Optional.of(new ObjectCard(ObjectTypeEnum.TOY)));
        cells[1][0] = new ShelfCell(Optional.of(new ObjectCard(ObjectTypeEnum.TOY)));

        cells[0][2] = new ShelfCell(Optional.of(new ObjectCard(ObjectTypeEnum.TOY)));
        cells[1][2] = new ShelfCell(Optional.of(new ObjectCard(ObjectTypeEnum.TOY)));

        cells[0][4] = new ShelfCell(Optional.of(new ObjectCard(ObjectTypeEnum.TOY)));
        cells[1][4] = new ShelfCell(Optional.of(new ObjectCard(ObjectTypeEnum.TOY)));

        cells[2][3] = new ShelfCell(Optional.of(new ObjectCard(ObjectTypeEnum.CAT)));
        cells[3][3] = new ShelfCell(Optional.of(new ObjectCard(ObjectTypeEnum.CAT)));

        cells[3][1] = new ShelfCell(Optional.of(new ObjectCard(ObjectTypeEnum.PLANT)));
        cells[4][1] = new ShelfCell(Optional.of(new ObjectCard(ObjectTypeEnum.PLANT)));

        cells[4][4] = new ShelfCell(Optional.of(new ObjectCard(ObjectTypeEnum.PLANT)));
        cells[5][4] = new ShelfCell(Optional.of(new ObjectCard(ObjectTypeEnum.PLANT)));

        Shelf shelf = new Shelf(SHELF_LENGTH, SHELF_HEIGHT, cells);

        Player player = new Player(shelf, "testUser");

        // Card Data 1--------------
        Set<SubPatternCell> coveredCells = new HashSet<>();
        coveredCells.add(new SubPatternCell(0,0, Optional.empty()));
        coveredCells.add(new SubPatternCell(0,1, Optional.empty()));

        SubPattern subPattern = new SubPattern(2,1, coveredCells, 1, 1, false);
        CommonPatternRules rules = new CommonPatternRules(subPattern, 6, false);

        CommonGoalCard exampleCard;

        try {
            exampleCard = new CommonGoalCard("0", CardBuilder.generatePointsCards(NUMBER_OF_PLAYERS), rules);

        }catch(Exception ex){
            System.out.println(ex.getMessage());
            return;
        }

        assertEquals(EXPECTED_POINT_CARD_VALUE, exampleCard.calculatePoints(player).getPointsGiven());

        assertEquals(EXPECTED_POINT_CARD_VALUE - 2, exampleCard.calculatePoints(player).getPointsGiven());
        assertEquals(EXPECTED_POINT_CARD_VALUE - 4, exampleCard.calculatePoints(player).getPointsGiven());
        assertEquals(EXPECTED_POINT_CARD_VALUE - 6, exampleCard.calculatePoints(player).getPointsGiven());
        assertEquals(0, exampleCard.calculatePoints(player).getPointsGiven());
        assertEquals(0, exampleCard.calculatePoints(player).getPointsGiven());

    }

    @Test
    public void matrixClonePrint(){
        boolean[][] a=  MatrixUtils.createEmptyMatrix(SHELF_LENGTH, SHELF_HEIGHT);
        a[0][0] = true;
        a[5][4] = true;
        boolean[][] b = MatrixUtils.clone(a);

        assertEquals(MatrixUtils.printMatrix(a), MatrixUtils.printMatrix(b));
    }
    @Test
    public void six1x2nonAdjacentRotated_expectedValid() {

        final int NUMBER_OF_PLAYERS = 4;
        final int EXPECTED_POINT_CARD_VALUE = 8;

        // @TODO: ADDING THE CSV PARSER
        //Shelf shelf = new Shelf(SHELF_LENGTH, SHELF_HEIGHT, MatrixParser.parse("assets/shelf1.csv", SHELF_LENGTH, SHELF_HEIGHT));
        ShelfCell[][] cells = MatrixUtils.emptyShelfCellMatrixInit(SHELF_LENGTH, SHELF_HEIGHT);
        cells[0][0] = new ShelfCell(Optional.of(new ObjectCard(ObjectTypeEnum.TOY)));
        cells[1][0] = new ShelfCell(Optional.of(new ObjectCard(ObjectTypeEnum.TOY)));

        cells[0][2] = new ShelfCell(Optional.of(new ObjectCard(ObjectTypeEnum.TOY)));
        cells[1][2] = new ShelfCell(Optional.of(new ObjectCard(ObjectTypeEnum.TOY)));

        cells[0][4] = new ShelfCell(Optional.of(new ObjectCard(ObjectTypeEnum.TOY)));
        cells[1][4] = new ShelfCell(Optional.of(new ObjectCard(ObjectTypeEnum.TOY)));

        cells[2][3] = new ShelfCell(Optional.of(new ObjectCard(ObjectTypeEnum.CAT)));
        cells[3][3] = new ShelfCell(Optional.of(new ObjectCard(ObjectTypeEnum.CAT)));

        cells[3][0] = new ShelfCell(Optional.of(new ObjectCard(ObjectTypeEnum.PLANT)));
        cells[3][1] = new ShelfCell(Optional.of(new ObjectCard(ObjectTypeEnum.PLANT)));

        cells[4][4] = new ShelfCell(Optional.of(new ObjectCard(ObjectTypeEnum.PLANT)));
        cells[5][4] = new ShelfCell(Optional.of(new ObjectCard(ObjectTypeEnum.PLANT)));

        cells[5][0] = new ShelfCell(Optional.of(new ObjectCard(ObjectTypeEnum.PLANT)));
        cells[5][1] = new ShelfCell(Optional.of(new ObjectCard(ObjectTypeEnum.PLANT)));

        Shelf shelf = new Shelf(SHELF_LENGTH, SHELF_HEIGHT, cells);

        Player player = new Player(shelf, "testUser");

        // Card Data 1--------------
        Set<SubPatternCell> coveredCells = new HashSet<>();
        coveredCells.add(new SubPatternCell(0,0, Optional.empty()));
        coveredCells.add(new SubPatternCell(0,1, Optional.empty()));

        SubPattern subPattern = new SubPattern(2,1, coveredCells, 1, 1, false);
        CommonPatternRules rules = new CommonPatternRules(subPattern, 6, false);

        CommonGoalCard exampleCard;

        try {
            exampleCard = new CommonGoalCard("0", CardBuilder.generatePointsCards(NUMBER_OF_PLAYERS), rules);

        }catch(Exception ex){
            System.out.println(ex.getMessage());
            return;
        }

        assertEquals(EXPECTED_POINT_CARD_VALUE, exampleCard.calculatePoints(player).getPointsGiven());
        assertEquals(EXPECTED_POINT_CARD_VALUE - 2, exampleCard.calculatePoints(player).getPointsGiven());
        assertEquals(EXPECTED_POINT_CARD_VALUE - 4, exampleCard.calculatePoints(player).getPointsGiven());
        assertEquals(EXPECTED_POINT_CARD_VALUE - 6, exampleCard.calculatePoints(player).getPointsGiven());
        assertEquals(0, exampleCard.calculatePoints(player).getPointsGiven());
        assertEquals(0, exampleCard.calculatePoints(player).getPointsGiven());

    }

}
