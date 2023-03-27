package it.polimi.ingsw.model.utils;

import com.opencsv.exceptions.CsvValidationException;
import it.polimi.ingsw.model.cards.ObjectCard;
import it.polimi.ingsw.model.cards.ObjectTypeEnum;
import it.polimi.ingsw.model.cells.ShelfCell;
import it.polimi.ingsw.model.player.Shelf;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import com.opencsv.CSVReader;

import static it.polimi.ingsw.model.utils.Constants.SHELF_HEIGHT;
import static it.polimi.ingsw.model.utils.Constants.SHELF_LENGTH;


public class CsvToShelfParser {


    public static void readLineByLine(String path) throws Exception {
        ShelfCell[][] cells = new ShelfCell[SHELF_HEIGHT][SHELF_LENGTH];

        try(CSVReader reader = new CSVReader(new FileReader(path)))
        {

            String [] nextLine;


            // Legge una riga per volta dal file csv ( nextLine è array di stringhe )
            while ((nextLine = reader.readNext()) != null)
            {


                // Leggo una stringa per volta da nextLine che è un array di stringhe rappresentante
                // la riga corrente

                for (String word: nextLine) {

                    //cells[y][x] = new ShelfCell(Optional.of(new ObjectCard(ObjectTypeEnum.CAT)))

                }
                System.out.println();
            }
        }
        catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }

    }
}
