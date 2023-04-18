package it.polimi.ingsw.model;

import it.polimi.ingsw.model.cells.BoardCell;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.utils.CsvToBoardParser;
import it.polimi.ingsw.model.utils.MatrixUtils;
import it.polimi.ingsw.model.utils.exceptions.MaxPlayersException;
import it.polimi.ingsw.model.utils.exceptions.WrongNumberOfPlayersException;
import org.junit.jupiter.api.Test;

import javax.swing.plaf.synth.SynthIcon;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    @Test
    void refillBoardTest() {
        String pathToTypeFile = "src/test/resources/boardTEST/expectedCellType_2players.csv";
        String pathToObjectCardFile = "src/test/resources/boardTEST/boardToRefill.csv";
        Sack s = new Sack();
        Board board = new Board(2, s);
        board.setCells(CsvToBoardParser.parseBoardObjectCardConfiguration(pathToTypeFile, pathToObjectCardFile, 2));

        MatrixUtils.printMatrix(board.getCells());
        System.out.println(s.getCards().size());
        board.refillBoard(s);
        System.out.println(s.getCards().size());
        System.out.println("\n\n");
        MatrixUtils.printMatrix(board.getCells());
    }

    @Test
    void shouldBeRefilledTest() throws WrongNumberOfPlayersException, MaxPlayersException {
        Board board = new Board(2, new Sack());
        System.out.println("Board initialized, refill needed: false");
        MatrixUtils.printBoardCardsConfigurationMatrix(board.getCells());
        System.out.println("shouldBeRefilled returns: "+board.shouldBeRefilled()+"\n");

        String pathToTypeFile = "src/test/resources/boardTEST/expectedCellType_2players.csv";
        String pathToObjectCardFile = "src/test/resources/boardTEST/expectedCellObjectCard_2players.csv";
        board.setCells(CsvToBoardParser.parseBoardObjectCardConfiguration(pathToTypeFile, pathToObjectCardFile, 2));
        System.out.println("New full board imported, refill needed: false");
        MatrixUtils.printBoardCardsConfigurationMatrix(board.getCells());
        System.out.println("shouldBeRefilled returns: "+board.shouldBeRefilled()+"\n");

        pathToObjectCardFile = "src/test/resources/boardTEST/emptyBoard.csv";
        board.setCells(CsvToBoardParser.parseBoardObjectCardConfiguration(pathToTypeFile, pathToObjectCardFile, 2));
        System.out.println("New empty board imported, refill needed: true");
        MatrixUtils.printBoardCardsConfigurationMatrix(board.getCells());
        System.out.println("shouldBeRefilled returns: "+board.shouldBeRefilled()+"\n");

        pathToObjectCardFile = "src/test/resources/boardTEST/boardToRefill.csv";
        board.setCells(CsvToBoardParser.parseBoardObjectCardConfiguration(pathToTypeFile, pathToObjectCardFile, 2));
        System.out.println("New board to refill imported, refill needed: true");
        MatrixUtils.printBoardCardsConfigurationMatrix(board.getCells());
        System.out.println("shouldBeRefilled returns: "+board.shouldBeRefilled()+"\n");
    }
}
