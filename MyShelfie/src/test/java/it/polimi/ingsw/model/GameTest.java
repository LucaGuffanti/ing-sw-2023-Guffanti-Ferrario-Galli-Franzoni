package it.polimi.ingsw.model;

import it.polimi.ingsw.model.cards.ObjectCard;
import it.polimi.ingsw.model.cards.ObjectTypeEnum;
import it.polimi.ingsw.model.cells.Coordinates;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Shelf;
import it.polimi.ingsw.model.utils.MatrixUtils;
import it.polimi.ingsw.model.utils.exceptions.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;


public class GameTest {
    @Test
    void pickCells_expectedValid() throws WrongNumberOfPlayersException, MaxPlayersException, IllegalBoardCellsPickException, IllegalShelfActionException {
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
    void noAvailableSpaceInShelf_expectedNotValid() throws WrongNumberOfPlayersException, NoSpaceEnoughInShelfException, MaxPlayersException {
        int PLAYERS_NUMBER = 3;
        List<Coordinates> testCellsCoordinates = new ArrayList<>();
        testCellsCoordinates.add(new Coordinates(2,2));
        testCellsCoordinates.add(new Coordinates(2,3));

        Board bard = new Board(PLAYERS_NUMBER, new Sack());
        Player player = new Player("Daniele");
        Player player2 = new Player("Armando");

        Game game = new Game(new Player("admin"),PLAYERS_NUMBER, 0);
        game.addPlayer(player);
        game.addPlayer(player2);
        game.initGame();

        ArrayList<ObjectCard> list = new ArrayList<>();
        Shelf shelf = player.getShelf();

        // Every column is if filled with 5 tiles
        for (int col = 0; col < shelf.getLengthInCells(); col++) {
            list = new ArrayList<>();
            list.add(new ObjectCard(ObjectTypeEnum.CAT));
            list.add(new ObjectCard(ObjectTypeEnum.CAT));
            list.add(new ObjectCard(ObjectTypeEnum.CAT));
            list.add(new ObjectCard(ObjectTypeEnum.CAT));
            list.add(new ObjectCard(ObjectTypeEnum.CAT));
            if(col != 0)
                list.add(new ObjectCard(ObjectTypeEnum.CAT));

            shelf.addCardsToColumn(list, col);
        }

        MatrixUtils.printMatrix(shelf.getCells());
        MatrixUtils.printBoardCardsConfigurationMatrix(game.getBoard().getCells());
        assertThrows(NoSpaceEnoughInShelfException.class, () -> game.checkIfEnoughSpaceInShelf(shelf, testCellsCoordinates.size()));
        assertThrows(NoSpaceEnoughInShelfColumnException.class, () -> game.checkIfEnoughSpaceInColumn(shelf, testCellsCoordinates.size(), 0));
        assertThrows(NoSpaceEnoughInShelfException.class, () -> game.moveCardsToPlayerShelf(player, testCellsCoordinates,0));


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
    void pickCellsOnEdge_expectedValid() throws WrongNumberOfPlayersException, MaxPlayersException, IllegalBoardCellsPickException, IllegalShelfActionException {
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
