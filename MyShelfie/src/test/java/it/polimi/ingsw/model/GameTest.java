package it.polimi.ingsw.model;

import it.polimi.ingsw.model.cells.Coordinates;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.utils.exceptions.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;


public class GameTest {
    @Test
    void pickCells_expectedValid() throws WrongNumberOfPlayersException, MaxPlayersException, IllegalBoardCellsPickException, NoSpaceEnoughInShelfColumnException {
        int PLAYERS_NUMBER = 2;
        int COLUMN_ID = 0;
        List<Coordinates> testCellsCoordinates = new ArrayList<>();
        testCellsCoordinates.add(new Coordinates(3,1));
        testCellsCoordinates.add(new Coordinates(3,2));

        Player player = new Player("Daniele");

        Game game = new Game(new Player("admin"),PLAYERS_NUMBER, 0);
        game.addPlayer(player);
        game.initGame();

        game.moveCardsToPlayerShelf(player, testCellsCoordinates, 0);
    }

    @Test
    void pickEmptyCells_expectedNotValid() throws WrongNumberOfPlayersException, MaxPlayersException {
        int PLAYERS_NUMBER = 2;
        List<Coordinates> testCellsCoordinates = new ArrayList<>();
        testCellsCoordinates.add(new Coordinates(3,0));
        testCellsCoordinates.add(new Coordinates(3,1));

        Board bard = new Board(PLAYERS_NUMBER, new Sack());
        Player player = new Player("Daniele");

        Game game = new Game(new Player("admin"),PLAYERS_NUMBER, 0);
        game.addPlayer(player);
        game.initGame();

        assertThrows(EmptyBoardCellsPickException.class, () -> game.moveCardsToPlayerShelf(player, testCellsCoordinates, 0));

    }

    @Test
    void pickCellsWithNoEmptyAdjacent_expectedNotValid() throws WrongNumberOfPlayersException, MaxPlayersException {
        int PLAYERS_NUMBER = 2;
        List<Coordinates> testCellsCoordinates = new ArrayList<>();
        testCellsCoordinates.add(new Coordinates(3,1));
        testCellsCoordinates.add(new Coordinates(3,2));
        testCellsCoordinates.add(new Coordinates(3,3));
        testCellsCoordinates.add(new Coordinates(3,4));

        Board bard = new Board(PLAYERS_NUMBER, new Sack());
        Player player = new Player("Daniele");

        Game game = new Game(new Player("admin"),PLAYERS_NUMBER, 0);
        game.addPlayer(player);
        game.initGame();

        assertThrows(NoEmptyAdjacentBoardCellsPickException.class, () -> game.moveCardsToPlayerShelf(player, testCellsCoordinates, 0));

    }


    @Test
    void pickDiagonalCells_expectedNotValid() throws WrongNumberOfPlayersException, MaxPlayersException {
        int PLAYERS_NUMBER = 2;

        // one different x
        List<Coordinates> testCellsCoordinates = new ArrayList<>();
        testCellsCoordinates.add(new Coordinates(3,1));
        testCellsCoordinates.add(new Coordinates(3,2));
        testCellsCoordinates.add(new Coordinates(2,3));

        Board bard = new Board(PLAYERS_NUMBER, new Sack());
        Player player = new Player("Daniele");

        Game game = new Game(new Player("admin"),PLAYERS_NUMBER, 0);
        game.addPlayer(player);
        game.initGame();
        assertThrows(DiagonalBoardCellsCellsPickException.class, () -> game.moveCardsToPlayerShelf(player, testCellsCoordinates, 0));

        // one different y
        testCellsCoordinates.clear();
        testCellsCoordinates.add(new Coordinates(3,1));
        testCellsCoordinates.add(new Coordinates(4,1));
        testCellsCoordinates.add(new Coordinates(5,2));

        assertThrows(DiagonalBoardCellsCellsPickException.class, () -> game.moveCardsToPlayerShelf(player, testCellsCoordinates, 0));

    }

    @Test
    void pickCellsOnEdge_expectedValid() throws WrongNumberOfPlayersException, MaxPlayersException, IllegalBoardCellsPickException, NoSpaceEnoughInShelfColumnException {
        int PLAYERS_NUMBER = 3;
        List<Coordinates> testCellsCoordinates = new ArrayList<>();
        testCellsCoordinates.add(new Coordinates(0,5));
        testCellsCoordinates.add(new Coordinates(1,5));

        Board bard = new Board(PLAYERS_NUMBER, new Sack());
        Player player = new Player("Daniele");

        Game game = new Game(new Player("admin"),PLAYERS_NUMBER, 0);
        game.addPlayer("Daniele");
        game.addPlayer("Armando");
        game.initGame();


        game.moveCardsToPlayerShelf(player, testCellsCoordinates, 0);


    }
}
