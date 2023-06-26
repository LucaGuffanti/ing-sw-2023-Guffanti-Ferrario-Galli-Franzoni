package it.polimi.ingsw.controller;

import it.polimi.ingsw.client.view.cli.Printer;
import it.polimi.ingsw.network.ServerNetworkHandler;
import it.polimi.ingsw.network.messages.*;
import it.polimi.ingsw.network.messages.enums.MessageType;
import it.polimi.ingsw.network.messages.enums.ResponseResultType;
import it.polimi.ingsw.network.utils.Logger;
import it.polimi.ingsw.server.controller.GameController;
import it.polimi.ingsw.server.controller.GameStatusEnum;
import it.polimi.ingsw.server.controller.turn.PickFromBoardPhase;
import it.polimi.ingsw.server.controller.turn.PutInShelfPhase;
import it.polimi.ingsw.server.controller.utils.GameObjectConverter;
import it.polimi.ingsw.server.model.Board;
import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.GameCheckout;
import it.polimi.ingsw.server.model.Sack;
import it.polimi.ingsw.server.model.cells.Coordinates;
import it.polimi.ingsw.server.model.player.Player;
import it.polimi.ingsw.server.model.utils.CsvToShelfParser;
import it.polimi.ingsw.server.model.utils.MatrixUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;


/**
 * This class implements methods that test the functionalities provided by the {@link GameController} class
 * @author Luca Guffanti
 */
public class GameControllerTest {
    private FakeServerNetworkHandler handler;
    private GameController gameController;


    /**
     * This method verifies that the constructor correctly operates.
     */
    @BeforeEach
    void init() {
        handler = new FakeServerNetworkHandler();
        gameController = new GameController(handler);
        System.out.println("Instantiating handler and controller");
        assertNotNull(gameController);
    }

    /**
     * This method verifies that, when requested, the controller is able to
     * instantiate a new Game object
     */
    @Test
    public void createGameTest() {
       String adminName = "TestUser1";
       int id = 0;
       for (int i = 2; i <= 4; i++) {
           gameController.createGame(adminName, i, id);
           assertNotNull(gameController.getGame());
           assertNotNull(gameController.getGame().getPlayers());
           assertEquals(1, gameController.getGame().getPlayers().size());
           assertEquals(adminName, gameController.getGame().getPlayers().get(0).getNickname());
           assertEquals(adminName, gameController.getGame().getGameInfo().getAdmin());
           assertEquals(id, gameController.getGame().getGameInfo().getGameID());
           assertEquals(i, gameController.getGame().getGameInfo().getNPlayers());
           assertEquals(GameStatusEnum.ACCEPTING_PLAYERS, gameController.getGameStatus());
       }
    }

    /**
     * This test verifies that, once created, the game can accept a correct number of players without any problems
     * and that when the number of players is reached the game status is set to started
     */
    @Test
    public void onPlayerJoinTest_alwaysGranted() {
        String user = "TestUser2";
        int id = 0;

        for (int numPlayers = 2; numPlayers <= 4; numPlayers++) {
            System.out.println("CREATING A NEW GAME for " + numPlayers + " players");
            gameController.createGame(user, numPlayers, id);
            for (int joiningPlayerID = 1; joiningPlayerID < numPlayers; joiningPlayerID++) {
                String joiningPlayerName = user + joiningPlayerID;
                gameController.onPlayerJoin(joiningPlayerName);
                if (handler.getCurrentMessage().getType().equals(MessageType.ACCESS_RESULT)) {
                    AccessResultMessage msg = (AccessResultMessage) handler.getCurrentMessage();
                    assertEquals(ResponseResultType.SUCCESS, msg.getResultType());
                }
                if (handler.getCurrentMessage().getType().equals(MessageType.NEW_PLAYER)) {
                    NewPlayerMessage msg = (NewPlayerMessage) handler.getCurrentMessage();
                    assertEquals(joiningPlayerName, msg.getJoinedPlayer());
                }
            }
            assertEquals(gameController.getGame().getGameInfo().getNPlayers(), gameController.getGame().getPlayers().size());
            assertEquals(GameStatusEnum.STARTED, gameController.getGameStatus());
        }
    }

    @Test
    public void onPlayerJoinTest_notGrantedSequential() {
        String user = "TestUser3";
        int id = 0;

        for (int numPlayers = 2; numPlayers <= 4; numPlayers++) {
            System.out.println("CREATING A NEW GAME for " + numPlayers + " players");
            gameController.createGame(user, numPlayers, id);
            for (int joiningPlayerID = 1; joiningPlayerID < numPlayers; joiningPlayerID++) {
                String joiningPlayerName = user + joiningPlayerID;
                gameController.onPlayerJoin(joiningPlayerName);
                if (handler.getCurrentMessage().getType().equals(MessageType.ACCESS_RESULT)) {
                    AccessResultMessage msg = (AccessResultMessage) handler.getCurrentMessage();
                    assertEquals(ResponseResultType.SUCCESS, msg.getResultType());
                }
                if (handler.getCurrentMessage().getType().equals(MessageType.NEW_PLAYER)) {
                    NewPlayerMessage msg = (NewPlayerMessage) handler.getCurrentMessage();
                    assertEquals(joiningPlayerName, msg.getJoinedPlayer());
                }
            }
            assertEquals(gameController.getGame().getGameInfo().getNPlayers(), gameController.getGame().getPlayers().size());
            assertEquals(GameStatusEnum.STARTED, gameController.getGameStatus());
            gameController.onPlayerJoin("ExcessPlayer");
            assertEquals(MessageType.ACCESS_RESULT, handler.getCurrentMessage().getType());
            AccessResultMessage failure = (AccessResultMessage) handler.getCurrentMessage();
            assertEquals(ResponseResultType.FAILURE, failure.getResultType());
        }
    }

    /**
     * This method verifies that parallel join requests are
     * @throws InterruptedException
     */
    @Test
    public void onPlayerJoinTest_Casual() throws InterruptedException {
        String user = "TestUser4";
        int id = 0;

        for (int numPlayers = 2; numPlayers <= 4; numPlayers++) {
            System.out.println("CREATING A NEW GAME for " + numPlayers + " players");
            gameController.createGame(user, numPlayers, id);
            ExecutorService service = Executors.newFixedThreadPool(numPlayers);
            for (int joiningPlayerID = 1; joiningPlayerID < numPlayers+1; joiningPlayerID++) {
                int finalJoiningPlayerID = joiningPlayerID;
                service.submit(() ->
                        {
                            String userid = user+ finalJoiningPlayerID;
                            gameController.onPlayerJoin(userid);
                        }
                );
            }
            service.awaitTermination(1000, TimeUnit.MILLISECONDS);
        }
    }

    @Test
    public void beginTurnTest() {
        String user = "TestUser5";
        int id = 0;

        for (int numPlayers = 2; numPlayers <= 4; numPlayers++) {
            System.out.println("CREATING A NEW GAME for " + numPlayers + " players");
            gameController.createGame(user, numPlayers, id);
            for (int joiningPlayerID = 1; joiningPlayerID < numPlayers; joiningPlayerID++) {
                String joiningPlayerName = user + joiningPlayerID;
                gameController.onPlayerJoin(joiningPlayerName);
                if (handler.getCurrentMessage().getType().equals(MessageType.ACCESS_RESULT)) {
                    AccessResultMessage msg = (AccessResultMessage) handler.getCurrentMessage();
                    assertEquals(ResponseResultType.SUCCESS, msg.getResultType());
                }
                if (handler.getCurrentMessage().getType().equals(MessageType.NEW_PLAYER)) {
                    NewPlayerMessage msg = (NewPlayerMessage) handler.getCurrentMessage();
                    assertEquals(joiningPlayerName, msg.getJoinedPlayer());
                }
            }
            assertEquals(gameController.getGame().getGameInfo().getNPlayers(), gameController.getGame().getPlayers().size());
            assertEquals(GameStatusEnum.STARTED, gameController.getGameStatus());

            assertEquals(0, gameController.getActivePlayerIndex());
            assertNotNull(gameController.getTurnPhase());
            assertTrue(gameController.getTurnPhase() instanceof PickFromBoardPhase);
            assertNotNull(gameController.getGame().getBoard());
            MatrixUtils.printMatrix(gameController.getGame().getBoard().getCells());
        }
    }

    @Test
    public void pickFromBoardTest_ValidMessage_ExpectedAdvanceOfState() {
        String user = "TestUser6";
        int id = 0;

        for (int numPlayers = 2; numPlayers <= 4; numPlayers++) {
            System.out.println("CREATING A NEW GAME for " + numPlayers + " players");
            gameController.createGame(user, numPlayers, id);
            for (int joiningPlayerID = 1; joiningPlayerID < numPlayers; joiningPlayerID++) {
                String joiningPlayerName = user + joiningPlayerID;
                gameController.onPlayerJoin(joiningPlayerName);
                if (handler.getCurrentMessage().getType().equals(MessageType.ACCESS_RESULT)) {
                    AccessResultMessage msg = (AccessResultMessage) handler.getCurrentMessage();
                    assertEquals(ResponseResultType.SUCCESS, msg.getResultType());
                }
                if (handler.getCurrentMessage().getType().equals(MessageType.NEW_PLAYER)) {
                    NewPlayerMessage msg = (NewPlayerMessage) handler.getCurrentMessage();
                    assertEquals(joiningPlayerName, msg.getJoinedPlayer());
                }
            }
            assertEquals(gameController.getGame().getGameInfo().getNPlayers(), gameController.getGame().getPlayers().size());
            assertEquals(GameStatusEnum.STARTED, gameController.getGameStatus());

            assertEquals(0, gameController.getActivePlayerIndex());
            assertNotNull(gameController.getTurnPhase());
            assertTrue(gameController.getTurnPhase() instanceof PickFromBoardPhase);
            assertNotNull(gameController.getGame().getBoard());
            MatrixUtils.printMatrix(gameController.getGame().getBoard().getCells());

            ArrayList<Coordinates> coords = new ArrayList<>();
            coords.add(new Coordinates(1,5));

            Message gameMessage = new PickFromBoardMessage(
                    gameController.getOrderedPlayersNicks().get(gameController.getActivePlayerIndex()),
                    coords
            );

            gameController.onGameMessageReceived(gameMessage);
            assertTrue((gameController.getTurnPhase() instanceof PutInShelfPhase));
            assertEquals(gameController.getChosenCoords(), coords);
        }
    }

    @Test
    public void pickFromBoardTest_InvalidMessage_WrongMessageType() {
        String user = "TestUser7";
        int id = 0;

        for (int numPlayers = 2; numPlayers <= 4; numPlayers++) {
            System.out.println("CREATING A NEW GAME for " + numPlayers + " players");
            gameController.createGame(user, numPlayers, id);
            for (int joiningPlayerID = 1; joiningPlayerID < numPlayers; joiningPlayerID++) {
                String joiningPlayerName = user + joiningPlayerID;
                gameController.onPlayerJoin(joiningPlayerName);
                if (handler.getCurrentMessage().getType().equals(MessageType.ACCESS_RESULT)) {
                    AccessResultMessage msg = (AccessResultMessage) handler.getCurrentMessage();
                    assertEquals(ResponseResultType.SUCCESS, msg.getResultType());
                }
                if (handler.getCurrentMessage().getType().equals(MessageType.NEW_PLAYER)) {
                    NewPlayerMessage msg = (NewPlayerMessage) handler.getCurrentMessage();
                    assertEquals(joiningPlayerName, msg.getJoinedPlayer());
                }
            }
            assertEquals(gameController.getGame().getGameInfo().getNPlayers(), gameController.getGame().getPlayers().size());
            //assertEquals(GameStatusEnum.STARTED, gameController.getGameStatus());

            assertEquals(0, gameController.getActivePlayerIndex());
            assertNotNull(gameController.getTurnPhase());
            assertTrue(gameController.getTurnPhase() instanceof PickFromBoardPhase);
            assertNotNull(gameController.getGame().getBoard());
            MatrixUtils.printMatrix(gameController.getGame().getBoard().getCells());

            ArrayList<Coordinates> coords = new ArrayList<>();
            coords.add(new Coordinates(1,5));

            Message gameMessage = new LoginRequestMessage(
                    gameController.getOrderedPlayersNicks().get(0)
            );

            gameController.onGameMessageReceived(gameMessage);
            assertTrue((gameController.getTurnPhase() instanceof PickFromBoardPhase));
        }
    }

    @Test
    public void pickFromBoardTest_InvalidMessage_WrongUser() {
        String user = "TestUser8";
        int id = 0;

        for (int numPlayers = 2; numPlayers <= 4; numPlayers++) {
            System.out.println("CREATING A NEW GAME for " + numPlayers + " players");
            gameController.createGame(user, numPlayers, id);
            for (int joiningPlayerID = 1; joiningPlayerID < numPlayers; joiningPlayerID++) {
                String joiningPlayerName = user + joiningPlayerID;
                gameController.onPlayerJoin(joiningPlayerName);
                if (handler.getCurrentMessage().getType().equals(MessageType.ACCESS_RESULT)) {
                    AccessResultMessage msg = (AccessResultMessage) handler.getCurrentMessage();
                    assertEquals(ResponseResultType.SUCCESS, msg.getResultType());
                }
                if (handler.getCurrentMessage().getType().equals(MessageType.NEW_PLAYER)) {
                    NewPlayerMessage msg = (NewPlayerMessage) handler.getCurrentMessage();
                    assertEquals(joiningPlayerName, msg.getJoinedPlayer());
                }
            }
            assertEquals(gameController.getGame().getGameInfo().getNPlayers(), gameController.getGame().getPlayers().size());
            assertEquals(GameStatusEnum.STARTED, gameController.getGameStatus());

            assertEquals(0, gameController.getActivePlayerIndex());
            assertNotNull(gameController.getTurnPhase());
            assertTrue(gameController.getTurnPhase() instanceof PickFromBoardPhase);
            assertNotNull(gameController.getGame().getBoard());
            MatrixUtils.printMatrix(gameController.getGame().getBoard().getCells());

            ArrayList<Coordinates> coords = new ArrayList<>();
            coords.add(new Coordinates(1,5));

            Message gameMessage = new LoginRequestMessage(
                    gameController.getOrderedPlayersNicks().get(1)
            );

            gameController.onGameMessageReceived(gameMessage);
            assertTrue((gameController.getTurnPhase() instanceof PickFromBoardPhase));
        }
    }

    @Test
    public void pickFromBoardTest_InvalidMessage_InvalidPick() {
        String user = "TestUser9";
        int id = 0;

        for (int numPlayers = 2; numPlayers <= 4; numPlayers++) {
            System.out.println("CREATING A NEW GAME for " + numPlayers + " players");
            gameController.createGame(user, numPlayers, id);
            for (int joiningPlayerID = 1; joiningPlayerID < numPlayers; joiningPlayerID++) {
                String joiningPlayerName = user + joiningPlayerID;
                gameController.onPlayerJoin(joiningPlayerName);
                if (handler.getCurrentMessage().getType().equals(MessageType.ACCESS_RESULT)) {
                    AccessResultMessage msg = (AccessResultMessage) handler.getCurrentMessage();
                    assertEquals(ResponseResultType.SUCCESS, msg.getResultType());
                }
                if (handler.getCurrentMessage().getType().equals(MessageType.NEW_PLAYER)) {
                    NewPlayerMessage msg = (NewPlayerMessage) handler.getCurrentMessage();
                    assertEquals(joiningPlayerName, msg.getJoinedPlayer());
                }
            }
            assertEquals(gameController.getGame().getGameInfo().getNPlayers(), gameController.getGame().getPlayers().size());
            assertEquals(GameStatusEnum.STARTED, gameController.getGameStatus());

            assertEquals(0, gameController.getActivePlayerIndex());
            assertNotNull(gameController.getTurnPhase());
            assertTrue(gameController.getTurnPhase() instanceof PickFromBoardPhase);
            assertNotNull(gameController.getGame().getBoard());
            MatrixUtils.printMatrix(gameController.getGame().getBoard().getCells());

            ArrayList<Coordinates> coords = new ArrayList<>();
            coords.add(new Coordinates(4,4));

            Message gameMessage = new LoginRequestMessage(
                    gameController.getOrderedPlayersNicks().get(1)
            );

            gameController.onGameMessageReceived(gameMessage);
            assertTrue((gameController.getTurnPhase() instanceof PickFromBoardPhase));
        }
    }
    @Test
    public void putInShelfTest_ValidMessage_ExpectedEndOfTurn() {
        String user = "TestUser10";
        int id = 0;

        for (int numPlayers = 2; numPlayers <= 4; numPlayers++) {
            System.out.println("CREATING A NEW GAME for " + numPlayers + " players");
            gameController.createGame(user, numPlayers, id);
            for (int joiningPlayerID = 1; joiningPlayerID < numPlayers; joiningPlayerID++) {
                String joiningPlayerName = user + joiningPlayerID;
                gameController.onPlayerJoin(joiningPlayerName);
                if (handler.getCurrentMessage().getType().equals(MessageType.ACCESS_RESULT)) {
                    AccessResultMessage msg = (AccessResultMessage) handler.getCurrentMessage();
                    assertEquals(ResponseResultType.SUCCESS, msg.getResultType());
                }
                if (handler.getCurrentMessage().getType().equals(MessageType.NEW_PLAYER)) {
                    NewPlayerMessage msg = (NewPlayerMessage) handler.getCurrentMessage();
                    assertEquals(joiningPlayerName, msg.getJoinedPlayer());
                }
            }
            assertEquals(gameController.getGame().getGameInfo().getNPlayers(), gameController.getGame().getPlayers().size());
            assertEquals(GameStatusEnum.STARTED, gameController.getGameStatus());

            assertEquals(0, gameController.getActivePlayerIndex());
            assertNotNull(gameController.getTurnPhase());
            assertTrue(gameController.getTurnPhase() instanceof PickFromBoardPhase);
            assertNotNull(gameController.getGame().getBoard());
            MatrixUtils.printMatrix(gameController.getGame().getBoard().getCells());

            ArrayList<Coordinates> coords = new ArrayList<>();
            coords.add(new Coordinates(1,5));

            Message gameMessage = new PickFromBoardMessage(
                    gameController.getOrderedPlayersNicks().get(gameController.getActivePlayerIndex()),
                    coords
            );

            gameController.onGameMessageReceived(gameMessage);
            assertTrue((gameController.getTurnPhase() instanceof PutInShelfPhase));
            assertEquals(gameController.getChosenCoords(), coords);

            int columnSelection = 0;
            gameMessage = new SelectColumnMessage(
                    gameController.getOrderedPlayersNicks().get(gameController.getActivePlayerIndex()),
                    columnSelection
            );
            gameController.onGameMessageReceived(gameMessage);
            System.out.println("Resulting Board:");
            MatrixUtils.printMatrix(gameController.getGame().getBoard().getCells());
            System.out.println("Resulting player's shelf");
            MatrixUtils.printMatrix(gameController.getGame().getPlayerByNick(
                    gameController.getOrderedPlayersNicks().get(gameController.getActivePlayerIndex()-1)
            ).getShelf().getCells());
            assertEquals(1, gameController.getActivePlayerIndex());
            assertTrue(gameController.getTurnPhase() instanceof PickFromBoardPhase );
        }
    }
    @Test
    public void putInShelfTest_InvalidMessage_WrongUser() {
        String user = "TestUser11";
        int id = 0;

        for (int numPlayers = 2; numPlayers <= 4; numPlayers++) {
            System.out.println("CREATING A NEW GAME for " + numPlayers + " players");
            gameController.createGame(user, numPlayers, id);
            for (int joiningPlayerID = 1; joiningPlayerID < numPlayers; joiningPlayerID++) {
                String joiningPlayerName = user + joiningPlayerID;
                gameController.onPlayerJoin(joiningPlayerName);
                if (handler.getCurrentMessage().getType().equals(MessageType.ACCESS_RESULT)) {
                    AccessResultMessage msg = (AccessResultMessage) handler.getCurrentMessage();
                    assertEquals(ResponseResultType.SUCCESS, msg.getResultType());
                }
                if (handler.getCurrentMessage().getType().equals(MessageType.NEW_PLAYER)) {
                    NewPlayerMessage msg = (NewPlayerMessage) handler.getCurrentMessage();
                    assertEquals(joiningPlayerName, msg.getJoinedPlayer());
                }
            }
            assertEquals(gameController.getGame().getGameInfo().getNPlayers(), gameController.getGame().getPlayers().size());
            assertEquals(GameStatusEnum.STARTED, gameController.getGameStatus());

            assertEquals(0, gameController.getActivePlayerIndex());
            assertNotNull(gameController.getTurnPhase());
            assertTrue(gameController.getTurnPhase() instanceof PickFromBoardPhase);
            assertNotNull(gameController.getGame().getBoard());
            MatrixUtils.printMatrix(gameController.getGame().getBoard().getCells());

            ArrayList<Coordinates> coords = new ArrayList<>();
            coords.add(new Coordinates(1,5));

            Message gameMessage = new PickFromBoardMessage(
                    gameController.getOrderedPlayersNicks().get(gameController.getActivePlayerIndex()),
                    coords
            );

            gameController.onGameMessageReceived(gameMessage);
            assertTrue((gameController.getTurnPhase() instanceof PutInShelfPhase));
            assertEquals(gameController.getChosenCoords(), coords);

            int columnSelection = 0;
            gameMessage = new SelectColumnMessage(
                    gameController.getOrderedPlayersNicks().get((gameController.getActivePlayerIndex()+1)%4),
                    columnSelection
            );
            gameController.onGameMessageReceived(gameMessage);
            assertTrue(gameController.getTurnPhase() instanceof PutInShelfPhase);
        }
    }

    @Test
    public void putInShelfTest_InvalidMessage_FullColumn() throws Exception {
        String user = "TestUser12";
        int id = 0;

        for (int numPlayers = 2; numPlayers <= 4; numPlayers++) {
            System.out.println("CREATING A NEW GAME for " + numPlayers + " players");
            gameController.createGame(user, numPlayers, id);
            for (int joiningPlayerID = 1; joiningPlayerID < numPlayers; joiningPlayerID++) {
                String joiningPlayerName = user + joiningPlayerID;
                gameController.onPlayerJoin(joiningPlayerName);
                if (handler.getCurrentMessage().getType().equals(MessageType.ACCESS_RESULT)) {
                    AccessResultMessage msg = (AccessResultMessage) handler.getCurrentMessage();
                    assertEquals(ResponseResultType.SUCCESS, msg.getResultType());
                }
                if (handler.getCurrentMessage().getType().equals(MessageType.NEW_PLAYER)) {
                    NewPlayerMessage msg = (NewPlayerMessage) handler.getCurrentMessage();
                    assertEquals(joiningPlayerName, msg.getJoinedPlayer());
                }
            }
            assertEquals(gameController.getGame().getGameInfo().getNPlayers(), gameController.getGame().getPlayers().size());
            assertEquals(GameStatusEnum.STARTED, gameController.getGameStatus());

            assertEquals(0, gameController.getActivePlayerIndex());
            assertNotNull(gameController.getTurnPhase());
            assertTrue(gameController.getTurnPhase() instanceof PickFromBoardPhase);
            assertNotNull(gameController.getGame().getBoard());
            MatrixUtils.printMatrix(gameController.getGame().getBoard().getCells());

            ArrayList<Coordinates> coords = new ArrayList<>();
            coords.add(new Coordinates(1,5));

            Message gameMessage = new PickFromBoardMessage(
                    gameController.getOrderedPlayersNicks().get(gameController.getActivePlayerIndex()),
                    coords
            );

            gameController.onGameMessageReceived(gameMessage);
            assertTrue((gameController.getTurnPhase() instanceof PutInShelfPhase));
            assertEquals(gameController.getChosenCoords(), coords);

            // forcing the shelf to be full
            Logger.externalInjection("FORCING THE SHELF FOR THE ACTIVE PLAYER: FULL SHELF");
            gameController.getGame().getPlayerByNick(gameController.getOrderedPlayersNicks().get(gameController.getActivePlayerIndex()))
                    .setShelf(CsvToShelfParser.convert("src/test/resources/shelfTEST/fullShelf.csv"));

            int columnSelection = 0;
            gameMessage = new SelectColumnMessage(
                    gameController.getOrderedPlayersNicks().get(gameController.getActivePlayerIndex()),
                    columnSelection
            );
            gameController.onGameMessageReceived(gameMessage);
            assertTrue(gameController.getTurnPhase() instanceof PutInShelfPhase);
        }
    }

    @Test
    public void putInShelfTest_ValidMessage_ShelfBecomesFull() throws Exception {
        String user = "TestUser13";
        int id = 0;

        for (int numPlayers = 2; numPlayers <= 4; numPlayers++) {
            System.out.println("CREATING A NEW GAME for " + numPlayers + " players");
            gameController.createGame(user, numPlayers, id);
            for (int joiningPlayerID = 1; joiningPlayerID < numPlayers; joiningPlayerID++) {
                String joiningPlayerName = user + joiningPlayerID;
                gameController.onPlayerJoin(joiningPlayerName);
                if (handler.getCurrentMessage().getType().equals(MessageType.ACCESS_RESULT)) {
                    AccessResultMessage msg = (AccessResultMessage) handler.getCurrentMessage();
                    assertEquals(ResponseResultType.SUCCESS, msg.getResultType());
                }
                if (handler.getCurrentMessage().getType().equals(MessageType.NEW_PLAYER)) {
                    NewPlayerMessage msg = (NewPlayerMessage) handler.getCurrentMessage();
                    assertEquals(joiningPlayerName, msg.getJoinedPlayer());
                }
            }
            assertEquals(gameController.getGame().getGameInfo().getNPlayers(), gameController.getGame().getPlayers().size());
            assertEquals(GameStatusEnum.STARTED, gameController.getGameStatus());

            assertEquals(0, gameController.getActivePlayerIndex());
            assertNotNull(gameController.getTurnPhase());
            assertTrue(gameController.getTurnPhase() instanceof PickFromBoardPhase);
            assertNotNull(gameController.getGame().getBoard());
            MatrixUtils.printMatrix(gameController.getGame().getBoard().getCells());

            ArrayList<Coordinates> coords = new ArrayList<>();
            coords.add(new Coordinates(1,5));

            Message gameMessage = new PickFromBoardMessage(
                    gameController.getOrderedPlayersNicks().get(gameController.getActivePlayerIndex()),
                    coords
            );

            gameController.onGameMessageReceived(gameMessage);
            assertTrue((gameController.getTurnPhase() instanceof PutInShelfPhase));
            assertEquals(gameController.getChosenCoords(), coords);

            // forcing the shelf to be full
            Logger.externalInjection("FORCING THE SHELF FOR THE ACTIVE PLAYER: SHELF WITH ONLY AN EMPTY SPACE");
            gameController.getGame().getPlayerByNick(gameController.getOrderedPlayersNicks().get(gameController.getActivePlayerIndex()))
                    .setShelf(CsvToShelfParser.convert("src/test/resources/shelfTEST/onlyOneEmptyCellThirdColumn.csv"));

            int columnSelection = 2;
            gameMessage = new SelectColumnMessage(
                    gameController.getOrderedPlayersNicks().get(gameController.getActivePlayerIndex()),
                    columnSelection
            );
            gameController.onGameMessageReceived(gameMessage);
            assertTrue(gameController.getTurnPhase() instanceof PickFromBoardPhase);
        }
    }


    /**
     * Testing if the user is recognized as winner, being the one who has completed the shelf.
     * (No other points given to other players)
     * @throws Exception
     */
    @Test
    public void gameCheckoutTest() throws Exception {
        String user = "TestUser13";
        int id = 0;

        for (int numPlayers = 2; numPlayers <= 4; numPlayers++) {
            gameController.createGame(user, numPlayers, id);

            for (int joiningPlayerID = 1; joiningPlayerID < numPlayers; joiningPlayerID++) {
                String joiningPlayerName = user + joiningPlayerID;
                gameController.onPlayerJoin(joiningPlayerName);

                if (handler.getCurrentMessage().getType().equals(MessageType.ACCESS_RESULT)) {
                    AccessResultMessage msg = (AccessResultMessage) handler.getCurrentMessage();
                    assertEquals(ResponseResultType.SUCCESS, msg.getResultType());
                }

                if (handler.getCurrentMessage().getType().equals(MessageType.NEW_PLAYER)) {
                    NewPlayerMessage msg = (NewPlayerMessage) handler.getCurrentMessage();
                    assertEquals(joiningPlayerName, msg.getJoinedPlayer());
                }

            }

        }

        gameController.getGame().getPlayers().get(1).getAchievements().setFirstToFinish(true);
        List<Player> players = gameController.getGame().getPlayers();
        GameCheckout gameCheckout = gameController.getGame().endGame(players.stream().map(Player::getNickname).collect(Collectors.toList()));
        assertEquals("TestUser13"+1, gameCheckout.getWinner());
    }

    /**
     * Here goes the tests which aims to cover every possible event
     * in the methods in a more specific way
     */


    /**
     * End Turn method
     */
    @Test
    public void endTurnFullMessageCreation() {

        GameController gameController1 = new GameController(new FakeServerNetworkHandler());
        gameController1.createGame("Daniele", 2, 0);
        gameController1.onPlayerJoin("Luca");
        //gameController1.reloadExistingGame();
        //gameController1.endTurn();
    }

    //TODO ADD JAVADOC
    //TODO ADD TEST CASE FOR THE END OF THE GAME (GAME FOR 2 PLAYERS, WITH THE SECOND PLAYER WITH SHELF FILLED WITH
    // ONLY AN EMPTY SPACE)
}
