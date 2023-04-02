package it.polimi.ingsw.model.utils;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import it.polimi.ingsw.model.cards.ObjectCard;
import it.polimi.ingsw.model.cards.ObjectTypeEnum;
import it.polimi.ingsw.model.cells.BoardCell;
import it.polimi.ingsw.model.cells.BoardCellEnum;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Optional;

/**
 * This class contains utility methods for parsing csv files describing what are defined as
 * <ul>
 *   <li><b>board cell type configuration</b>: the csv contains the <b>type of each cell</b> in the board</li>
 *   <li><b>board object card configuration</b>: the csv contains the <b>Object cards</b> in the board</li>
 * </ul>
 *
 * @author Luca Guffanti
 */
public class CsvToBoardParser {

    /**
     * Map between the characters found in the csv and the type of the boardCell. Specifically
     * <br>
     * X -> INACTIVE<br>
     * A -> ALWAYS_ACTIVE<br>
     * T -> THREE_PLAYERS<br>
     * F -> FOUR PLAYERS<br>
     */
    private static final Map<String, BoardCellEnum> strToCellTypeMap = Map.of(
            "X", BoardCellEnum.INACTIVE,
            "A", BoardCellEnum.ALWAYS_ACTIVE,
            "T", BoardCellEnum.THREE_PLAYERS,
            "F", BoardCellEnum.FOUR_PLAYERS
    );

    /**
     * Map between the characters found in the csv and the Object card held by a cell. The mapping
     * refers to the color. Specifically
     * <br>
     * G -> CAT<br>
     * B -> FRAME<br>
     * W -> BOOK<br>
     * Y -> TOY<br>
     * C -> TROPHY</>br>
     * M -> PLANT<br>
     * X -> EMPTY<br>
     */
    private static final Map<String, Optional<ObjectCard>> map = Map.of(
            "G", Optional.of(new ObjectCard(ObjectTypeEnum.CAT)),
            "B", Optional.of(new ObjectCard(ObjectTypeEnum.FRAME)),
            "W", Optional.of(new ObjectCard(ObjectTypeEnum.BOOK)),
            "Y", Optional.of(new ObjectCard(ObjectTypeEnum.TOY)),
            "C", Optional.of(new ObjectCard(ObjectTypeEnum.TROPHY)),
            "M", Optional.of(new ObjectCard(ObjectTypeEnum.PLANT)),
            "X", Optional.empty()
    );
    /**
     * This method builds a board matrix starting from a configuration where each cell is associated to
     * its type
     * @see BoardCellEnum
     * @param path the path of the csv file
     * @return the matrix where cells are initialized with their correct type
     */
    public static BoardCell[][] parseBoardCellTypeConfiguration(String path) {
        BoardCell[][] boardMatrix = new BoardCell[9][9];

        try {
            Reader r = Files.newBufferedReader(Path.of(path));
            CSVReader csvReader = new CSVReader(r);

            for (int y = 0; y < 9; y++) {
                String[] read = csvReader.readNext();
                for (int x = 0; x < 9; x++) {
                    boardMatrix[y][x] = new BoardCell(Optional.empty(), strToCellTypeMap.get(read[x]));
                }
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }

        return boardMatrix;
    }

    /**
     * This method builds a board matrix starting from a configuration where each cell is
     * associated to an objectCard
     * @param path the path of the csv file
     * @return the matrix representing the board containing object cards
     */
    public static BoardCell[][] parseBoardObjectCardConfiguration(String path) {
        // TODO IMPLEMENT METHOD
        return null;
    }
}
