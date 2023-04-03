package it.polimi.ingsw.model;

import it.polimi.ingsw.model.cells.Coordinates;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.utils.exceptions.MaxPlayersException;
import it.polimi.ingsw.model.utils.exceptions.WrongNumberOfPlayersException;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;


public class GameTest {
    @Test
    void pickCells_expectedValid() throws WrongNumberOfPlayersException, MaxPlayersException {
        int PLAYERS_NUMBER = 2;
        Set<Coordinates> testCellsCoordinates = new HashSet<>();
        testCellsCoordinates.add(new Coordinates(3,1));
        testCellsCoordinates.add(new Coordinates(3,2));

        Board bard = new Board(PLAYERS_NUMBER, new Sack());
        Game game = new Game(new Player("admin"),PLAYERS_NUMBER, 0);
        game.addPlayer("Daniele");
        game.initGame();
        assertEquals(true, game.playerCanPickFromCellsBoard(testCellsCoordinates));

    }

    @Test
    void pickEmptyCells_expectedNotValid() throws WrongNumberOfPlayersException, MaxPlayersException {
        int PLAYERS_NUMBER = 2;
        Set<Coordinates> testCellsCoordinates = new HashSet<>();
        testCellsCoordinates.add(new Coordinates(3,2));
        testCellsCoordinates.add(new Coordinates(3,3));

        Board bard = new Board(PLAYERS_NUMBER, new Sack());

        Game game = new Game(new Player("admin"),PLAYERS_NUMBER, 0);
        game.addPlayer("Daniele");
        game.initGame();
        assertEquals(false, game.playerCanPickFromCellsBoard(testCellsCoordinates));

    }

    @Test
    void pickCellsOnEdge_expectedValid() throws WrongNumberOfPlayersException, MaxPlayersException {
        int PLAYERS_NUMBER = 3;
        Set<Coordinates> testCellsCoordinates = new HashSet<>();
        testCellsCoordinates.add(new Coordinates(0,5));
        testCellsCoordinates.add(new Coordinates(1,5));

        Board bard = new Board(PLAYERS_NUMBER, new Sack());

        Game game = new Game(new Player("admin"),PLAYERS_NUMBER, 0);
        game.addPlayer("Daniele");
        game.addPlayer("Armando");

        game.initGame();
        assertEquals(true, game.playerCanPickFromCellsBoard(testCellsCoordinates));

    }
}
