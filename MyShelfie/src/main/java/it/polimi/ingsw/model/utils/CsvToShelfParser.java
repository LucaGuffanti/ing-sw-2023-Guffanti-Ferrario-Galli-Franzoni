package it.polimi.ingsw.model.utils;

import com.opencsv.exceptions.CsvValidationException;
import it.polimi.ingsw.model.cards.ObjectCard;
import it.polimi.ingsw.model.cards.ObjectTypeEnum;
import it.polimi.ingsw.model.cells.ShelfCell;
import it.polimi.ingsw.model.player.Shelf;

import java.awt.*;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Optional;

import com.opencsv.CSVReader;

import static it.polimi.ingsw.model.utils.Constants.SHELF_HEIGHT;
import static it.polimi.ingsw.model.utils.Constants.SHELF_LENGTH;


public class CsvToShelfParser {

    private static final Map<String, Optional<ObjectCard> > map = Map.of(
            "G", Optional.of(new ObjectCard(ObjectTypeEnum.CAT)),
            "B", Optional.of(new ObjectCard(ObjectTypeEnum.FRAME)),
            "W", Optional.of(new ObjectCard(ObjectTypeEnum.BOOK)),
            "Y", Optional.of(new ObjectCard(ObjectTypeEnum.TOY)),
            "C", Optional.of(new ObjectCard(ObjectTypeEnum.TROPHY)),
            "M", Optional.of(new ObjectCard(ObjectTypeEnum.PLANT)),
            "X", Optional.empty()
            );



    public static Shelf convert(String path) throws Exception {
        ShelfCell[][] cells = new ShelfCell[SHELF_HEIGHT][SHELF_LENGTH];

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
               System.out.println();
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
}
