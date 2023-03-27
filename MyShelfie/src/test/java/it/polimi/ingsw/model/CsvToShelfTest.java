package it.polimi.ingsw.model;

import it.polimi.ingsw.model.utils.CsvToShelfParser;
import it.polimi.ingsw.model.utils.MatrixUtils;
import org.junit.Test;

public class CsvToShelfTest {

    @Test
    public void test(){
        try {
            CsvToShelfParser.readLineByLine("src/main/assets/shelfConfigurations/allAngles.csv");
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
