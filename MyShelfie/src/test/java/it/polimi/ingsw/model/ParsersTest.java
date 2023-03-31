package it.polimi.ingsw.model;

import it.polimi.ingsw.model.player.Shelf;
import it.polimi.ingsw.model.utils.CsvToShelfParser;
import it.polimi.ingsw.model.utils.MatrixUtils;
import org.junit.Test;

public class CsvToShelfTest {

    @Test
    public void test(){
        try {
            Shelf result =  CsvToShelfParser.convert("src/main/assets/shelfConfigurations/twoSquaresSameColorNotAdjacent.csv");
            MatrixUtils.printMatrix(result.getCells());
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
