package it.polimi.ingsw.model;

import it.polimi.ingsw.server.model.cells.ShelfCell;
import it.polimi.ingsw.server.model.player.Shelf;
import it.polimi.ingsw.server.model.utils.Constants;
import it.polimi.ingsw.server.model.utils.CsvToShelfParser;
import it.polimi.ingsw.server.model.utils.JsonFixedPatternGoalCardsParser;
import it.polimi.ingsw.server.model.utils.MatrixUtils;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ParsersTest {

    @Test
    public void testCSVShelfParsing(){
        try {
            Shelf result =  CsvToShelfParser.convert("src/test/resources/shelfTEST/allAngles.csv");
            MatrixUtils.printMatrix(result.getCells());
            assertNotNull(result);

        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    @Test
    public void jsonParserPrintTest(){
        try {
            assertNotNull(JsonFixedPatternGoalCardsParser.parseFixedPatternCommonGoals("/assets/cards/fixedPatternShapedCommonGoalCards.json"));
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
