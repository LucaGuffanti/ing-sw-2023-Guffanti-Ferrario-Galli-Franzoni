package it.polimi.ingsw.model;

import it.polimi.ingsw.server.model.Board;
import it.polimi.ingsw.server.model.Sack;
import it.polimi.ingsw.server.model.utils.CsvToBoardParser;
import it.polimi.ingsw.server.model.utils.MatrixUtils;
import it.polimi.ingsw.server.model.utils.exceptions.MaxPlayersException;
import it.polimi.ingsw.server.model.utils.exceptions.WrongNumberOfPlayersException;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

class BoardTest {

    @Test
    void refillBoardTest() {
        String pathToTypeFile = "/boardTEST/expectedCellType_2players.csv";
        String pathToObjectCardFile = "/boardTEST/boardToRefill.csv";
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
        assertEquals(false, board.shouldBeRefilled());

        String pathToTypeFile = "/boardTEST/expectedCellType_2players.csv";
        String pathToObjectCardFile = "/boardTEST/expectedCellObjectCard_2players.csv";
        board.setCells(CsvToBoardParser.parseBoardObjectCardConfiguration(pathToTypeFile, pathToObjectCardFile, 2));
        System.out.println("New full board imported, refill needed: false");
        MatrixUtils.printBoardCardsConfigurationMatrix(board.getCells());
        System.out.println("shouldBeRefilled returns: "+board.shouldBeRefilled()+"\n");
        assertEquals(false, board.shouldBeRefilled());

        pathToObjectCardFile = "/boardTEST/emptyBoard.csv";
        board.setCells(CsvToBoardParser.parseBoardObjectCardConfiguration(pathToTypeFile, pathToObjectCardFile, 2));
        System.out.println("New empty board imported, refill needed: true");
        MatrixUtils.printBoardCardsConfigurationMatrix(board.getCells());
        System.out.println("shouldBeRefilled returns: "+board.shouldBeRefilled()+"\n");
        assertEquals(true, board.shouldBeRefilled());

        pathToObjectCardFile = "/boardTEST/boardToRefill.csv";
        board.setCells(CsvToBoardParser.parseBoardObjectCardConfiguration(pathToTypeFile, pathToObjectCardFile, 2));
        System.out.println("New board to refill imported, refill needed: true");
        MatrixUtils.printBoardCardsConfigurationMatrix(board.getCells());
        System.out.println("shouldBeRefilled returns: "+board.shouldBeRefilled()+"\n");
        assertEquals(true, board.shouldBeRefilled());

        pathToObjectCardFile = "/boardTEST/notToRefill.csv";
        board.setCells(CsvToBoardParser.parseBoardObjectCardConfiguration(pathToTypeFile, pathToObjectCardFile, 2));
        System.out.println("New board to refill imported, refill needed: false");
        MatrixUtils.printBoardCardsConfigurationMatrix(board.getCells());
        System.out.println("shouldBeRefilled returns: "+board.shouldBeRefilled()+"\n");
        assertEquals(false, board.shouldBeRefilled());

        pathToObjectCardFile = "/boardTEST/notToRefill2.csv";
        board.setCells(CsvToBoardParser.parseBoardObjectCardConfiguration(pathToTypeFile, pathToObjectCardFile, 2));
        System.out.println("New board to refill imported, refill needed: false");
        MatrixUtils.printBoardCardsConfigurationMatrix(board.getCells());
        System.out.println("shouldBeRefilled returns: "+board.shouldBeRefilled()+"\n");
        assertEquals(false, board.shouldBeRefilled());

    }
}
