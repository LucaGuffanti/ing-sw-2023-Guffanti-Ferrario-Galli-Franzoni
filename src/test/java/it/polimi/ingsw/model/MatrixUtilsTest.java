package it.polimi.ingsw.model;

import it.polimi.ingsw.server.model.cells.ShelfCell;
import it.polimi.ingsw.server.model.player.Shelf;
import it.polimi.ingsw.server.model.utils.Constants;
import it.polimi.ingsw.server.model.utils.CsvToShelfParser;
import it.polimi.ingsw.server.model.utils.JsonFixedPatternGoalCardsParser;
import it.polimi.ingsw.server.model.utils.MatrixUtils;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MatrixUtilsTest {
    @Test
    public void testMatrixInit(){
        try {
            ShelfCell[][] matrix = MatrixUtils.emptyShelfCellMatrixInit(Constants.SHELF_LENGTH, Constants.SHELF_HEIGHT);
            for (int i = 0; i < Constants.SHELF_HEIGHT; i++) {
                for (int j = 0; j < Constants.SHELF_LENGTH; j++) {
                    assertTrue(matrix[i][j].getCellCard().isEmpty());
                }
            }

        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    @Test
    public void testMatrixPrint(){
        try {
            String res = "1 1 1 0 ";
            boolean[][] matrix = {
                    {true, true},
                    {true, false}
            };

            assertEquals(res, MatrixUtils.printMatrix(matrix));
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

}
