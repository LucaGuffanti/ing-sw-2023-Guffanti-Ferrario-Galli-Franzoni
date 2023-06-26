package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.FakeServerNetworkHandler;
import it.polimi.ingsw.server.controller.GameController;
import it.polimi.ingsw.server.model.*;
import it.polimi.ingsw.server.model.cards.*;
import it.polimi.ingsw.server.model.cards.goalCards.CommonGoalCard;
import it.polimi.ingsw.server.model.cards.goalCards.FixedPatternCommonGoalCard;
import it.polimi.ingsw.server.model.cells.Coordinates;
import it.polimi.ingsw.server.model.player.Player;
import it.polimi.ingsw.server.model.player.Shelf;
import it.polimi.ingsw.server.model.utils.MatrixUtils;
import it.polimi.ingsw.server.model.utils.exceptions.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


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
        game.addPlayerDEBUG(player);
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
        game.addPlayerDEBUG(player);
        game.addPlayerDEBUG(player2);
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
        game.addPlayerDEBUG(player);
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
        game.addPlayerDEBUG(player);
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
        game.addPlayerDEBUG(player);
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
        game.addPlayerDEBUG("Daniele");
        game.addPlayerDEBUG("Armando");
        game.initGame();


        game.moveCardsToPlayerShelf(player, testCellsCoordinates, 0);
    }

    /**
     *
     * @throws WrongNumberOfPlayersException
     */
    @Test
    void awardPointCardTest() throws WrongNumberOfPlayersException {
        int PLAYERS_NUMBER = 2;
        Game game = new Game(new Player("admin"),PLAYERS_NUMBER, 0);
        Player player = new Player("Daniele");
        game.addPlayerDEBUG(player);

        ArrayList<CommonGoalCard> cards = new ArrayList<>();

        cards.add(GoalCardsDeckSingleton.getInstance().getCommonGoalCardById("1"));
        cards.add(GoalCardsDeckSingleton.getInstance().getCommonGoalCardById("2"));

        ArrayList<PointCard> pointCards = new ArrayList<>();
        pointCards.add(new PointCard(PointEnumeration.EIGHT_POINTS, 8));

        cards.get(0).setPointsCards(pointCards);
        cards.get(1).setPointsCards(pointCards);

        game.getGameInfo().setSelectedCommonGoals(cards);

        player.getAchievements().setCompletedFirstCommonGoal(false);
        player.getAchievements().setCompletedSecondCommonGoal(false);
        game.awardTurnWisePoints(player);
    }

    /**
     * Exceptions handling
     */

    @Test
    void playersNumberExceptions() throws WrongNumberOfPlayersException, MaxPlayersException {
        int PLAYERS_NUMBER = 3;
        List<Coordinates> testCellsCoordinates = new ArrayList<>();
        testCellsCoordinates.add(new Coordinates(0,5));
        testCellsCoordinates.add(new Coordinates(1,5));


        try {
            Game game = new Game(new Player("admin"), 0, 0);

        }
        catch (Exception ex){
            assertEquals(new WrongNumberOfPlayersException(0).getMessage(), ex.getMessage());
        }

        GameController controller = new GameController(new FakeServerNetworkHandler());

        Game game = new Game(new Player("admin"), PLAYERS_NUMBER, 0, controller);

        game.addPlayer(new Player(null, "Daniele"));
        game.addPlayer(new Player(null, "Armando"));

        try{
            game.addPlayer(new Player(null, "Davide"));

        }catch (Exception ex){
            assertEquals(new MaxPlayersException("The game is full").getMessage(), ex.getMessage());

        }

        assertThrows(WrongNumberOfPlayersException.class, ()-> {
            game.getGameInfo().setNPlayers(5);
            game.initGame();
        });
    }


}
