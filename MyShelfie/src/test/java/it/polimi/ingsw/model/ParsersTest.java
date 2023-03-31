package it.polimi.ingsw.model;

import it.polimi.ingsw.model.player.Shelf;
import it.polimi.ingsw.model.utils.CsvToShelfParser;
import it.polimi.ingsw.model.utils.JsonGoalCardsParser;
import it.polimi.ingsw.model.utils.MatrixUtils;
import org.junit.Test;

import java.io.IOException;

public class ParsersTest {

    @Test
    public void test(){
        try {
            Shelf result =  CsvToShelfParser.convert("src/main/assets/shelfConfigurations/twoSquaresSameColorNotAdjacent.csv");
            MatrixUtils.printMatrix(result.getCells());
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    @Test
    public void jsonParserPrint(){
        try {
            JsonGoalCardsParser.parseCommonGoals("src/main/assets/cards/commonGoalCards.json", 4);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
