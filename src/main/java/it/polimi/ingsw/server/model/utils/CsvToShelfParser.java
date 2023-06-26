package it.polimi.ingsw.server.model.utils;

import com.opencsv.exceptions.CsvValidationException;
import it.polimi.ingsw.server.model.cards.ObjectCard;
import it.polimi.ingsw.server.model.cards.ObjectTypeEnum;
import it.polimi.ingsw.server.model.cells.ShelfCell;
import it.polimi.ingsw.server.model.player.Shelf;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Optional;

import com.opencsv.CSVReader;

/**
 * Object containing methods that parse a CSV representation of a shelf
 * @author Luca Guffanti, Marco Galli, Daniele Ferrario, Davide Franzoni
 */
public class CsvToShelfParser {
    /**
     * Map between the character in the CSV and a built tile
     */
    private static final Map<String, Optional<ObjectCard> > map = Map.of(
            "G", Optional.of(new ObjectCard(ObjectTypeEnum.CAT)),
            "B", Optional.of(new ObjectCard(ObjectTypeEnum.FRAME)),
            "W", Optional.of(new ObjectCard(ObjectTypeEnum.BOOK)),
            "Y", Optional.of(new ObjectCard(ObjectTypeEnum.TOY)),
            "C", Optional.of(new ObjectCard(ObjectTypeEnum.TROPHY)),
            "M", Optional.of(new ObjectCard(ObjectTypeEnum.PLANT)),
            "X", Optional.empty()
            );
    /**
     * Map between the character in the CSV and a tile type
     */
    private static final Map<String, ObjectTypeEnum> strToCellObjectCardMap = Map.of(
            "G", ObjectTypeEnum.CAT,
            "B", ObjectTypeEnum.FRAME,
            "W", ObjectTypeEnum.BOOK,
            "Y", ObjectTypeEnum.TOY,
            "C", ObjectTypeEnum.TROPHY,
            "M", ObjectTypeEnum.PLANT
    );


    /**
     * This method generates a shelf by parsing a CSV file
     * @param path path to the CSV file
     * @return a newly generated shelf
     * @throws Exception thrown if something goes wrong during the parsing of the files
     */

    public static Shelf convert(String path) throws Exception {
        ShelfCell[][] cells = new ShelfCell[Constants.SHELF_HEIGHT][Constants.SHELF_LENGTH];

        try(CSVReader reader = new CSVReader(new FileReader(path)))
        {
            String [] nextLine;
            int x = 0, y = 0;

            // Reads one line at a time from the csv file (nextLine is an array of strings)
            while ((nextLine = reader.readNext()) != null)
            {
               for (String word: nextLine) {
                   cells[y][x] = new ShelfCell(map.get(word));
                   x++;
               }
               y++;
               x = 0;
            }
        }
        catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }

        Shelf shelf = new Shelf();
        shelf.setCells(cells);
        return shelf;
    }

    /**
     * This method generates a matrix of types of tiles starting from a CSV file
     * @param path path to the CSV file
     * @return the matrix of types of tiles representing board
     */
    public static ObjectTypeEnum[][] simpleConvert(String path) {
        ObjectTypeEnum[][] cells = new ObjectTypeEnum[Constants.SHELF_HEIGHT][Constants.SHELF_LENGTH];

        try(CSVReader reader = new CSVReader(new InputStreamReader(CsvToShelfParser.class.getResourceAsStream(path))))
        {
            String [] nextLine;
            int x = 0, y = 0;

            // Reads one line at a time from the csv file (nextLine is an array of strings)
            while ((nextLine = reader.readNext()) != null)
            {
                for (String word: nextLine) {
                    if (word.equals("X")) {
                        cells[y][x] = null;
                    } else {
                        cells[y][x] = strToCellObjectCardMap.get(word);
                    }
                    x++;
                }
                y++;
                x = 0;
            }
        }
        catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }

        return cells;
    }
}
