package it.polimi.ingsw.model;

import it.polimi.ingsw.model.player.Shelf;
import it.polimi.ingsw.model.utils.CsvToShelfParser;
import it.polimi.ingsw.model.utils.JsonFixedPatternGoalCardsParser;
import it.polimi.ingsw.model.utils.MatrixUtils;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ParsersTest {

    @Test
    public void test(){
        try {
            Shelf result =  CsvToShelfParser.convert("src/test/resources/shelfTEST/allAngles.csv");
            MatrixUtils.printMatrix(result.getCells());
            assertNotNull(result);
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    @Test
    public void jsonParserPrint(){
        try {
            assertNotNull(JsonFixedPatternGoalCardsParser.parseFixedPatternCommonGoals("src/main/assets/cards/fixedPatternShapedCommonGoalCards.json"));
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
