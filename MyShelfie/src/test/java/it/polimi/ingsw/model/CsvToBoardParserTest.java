package it.polimi.ingsw.model;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import it.polimi.ingsw.server.model.cells.BoardCell;
import it.polimi.ingsw.server.model.cells.BoardCellEnum;
import it.polimi.ingsw.server.model.utils.Constants;
import it.polimi.ingsw.server.model.utils.CsvToBoardParser;
import it.polimi.ingsw.server.model.utils.MatrixUtils;
import org.junit.Test;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * This class contains methods that verify the correct behavior of the parser in different cases.
 * @author Luca Guffanti
 */
public class CsvToBoardParserTest {

    private static final Map<String, BoardCellEnum> strToTypeMap = Map.of(
            "X", BoardCellEnum.INACTIVE,
            "A", BoardCellEnum.ACTIVE
    );

    /**
     * This method verifies that the parser can correctly parse a type of cells configuration (more specifically the one
     * used to initialize the board object), checking that the parsing correctly works for each possible number of players.
     */
    @Test
    public void CsvToBoardParserTypeConfigurationTest() {
        String pathToFile = "src/main/assets/board/cellTypeConfiguration.csv";

        for (int numPlayers = Constants.MIN_PLAYER; numPlayers <= Constants.MAX_PLAYER; numPlayers++) {
            System.out.println("Checking csv parsing with "+numPlayers+" players");
            BoardCell[][] boardCell = CsvToBoardParser.parseBoardCellTypeConfiguration(pathToFile, numPlayers);
            MatrixUtils.printBoardCellTypesConfigurationMatrix(boardCell);
            assertNotNull(boardCell);
            String pathToResult = "src/test/resources/boardTEST/expectedCellType_"+numPlayers+"players.csv";
            try {
                Reader r = Files.newBufferedReader(Path.of(pathToResult));
                CSVReader csvReader = new CSVReader(r);
                BoardCell[][] result = new BoardCell[Constants.BOARD_DIMENSION][Constants.BOARD_DIMENSION];
                for (int y = 0; y < Constants.BOARD_DIMENSION; y++) {
                    String[] read = csvReader.readNext();
                    for (int x = 0; x < Constants.BOARD_DIMENSION; x++) {
                        result[y][x] = new BoardCell(Optional.empty(),strToTypeMap.get(read[x]));
                    }
                }

                for (int y = 0; y < Constants.BOARD_DIMENSION; y++) {
                    for (int x = 0; x < Constants.BOARD_DIMENSION; x++) {
                        System.out.println("Checking ("+x+","+y+")");
                        assertEquals(result[y][x].getType(), boardCell[y][x].getType());
                    }
                }
            } catch (IOException | CsvValidationException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * This method tests different board configuration according to csv files, one have type configuration,
     * the other have object cards configuration
     * @author Marco Galli
     */
    @Test
    public void CsvToBoardParserObjectCardsConfigurationTest() {
        for (int numPlayers = Constants.MIN_PLAYER; numPlayers <= Constants.MAX_PLAYER; numPlayers++ ) {
            System.out.println("Checking csv parsing with "+numPlayers+" players");
            String pathToTypeFile = "src/test/resources/boardTEST/expectedCellType_"+numPlayers+"players.csv";
            String pathToObjectCardFile = "src/test/resources/boardTEST/expectedCellObjectCard_"+numPlayers+"players.csv";

            BoardCell[][] boardCell = CsvToBoardParser.parseBoardObjectCardConfiguration(pathToTypeFile, pathToObjectCardFile, 2);
            MatrixUtils.printBoardCardsConfigurationMatrix(boardCell);
            assertNotNull(boardCell);
            System.out.println();
        }
    }
}
