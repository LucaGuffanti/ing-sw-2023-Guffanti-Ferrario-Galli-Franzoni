package it.polimi.ingsw.controller;

import it.polimi.ingsw.network.NetworkHandler;
import it.polimi.ingsw.network.messages.*;
import it.polimi.ingsw.network.messages.enums.MessageType;
import it.polimi.ingsw.network.messages.enums.ResponseResultType;
import it.polimi.ingsw.network.utils.Logger;
import it.polimi.ingsw.server.controller.GameController;
import it.polimi.ingsw.server.controller.GameStatusEnum;
import it.polimi.ingsw.server.controller.turn.PickFromBoardPhase;
import it.polimi.ingsw.server.controller.turn.PutInShelfPhase;
import it.polimi.ingsw.server.model.cells.Coordinates;
import it.polimi.ingsw.server.model.utils.CsvToShelfParser;
import it.polimi.ingsw.server.model.utils.MatrixUtils;
import org.junit.BeforeClass;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;


/**
 * This class implements methods that test the functionalities provided by the {@link GameController} class
 * @author Luca Guffanti
 */
public class GameControllerTest {
    private NetworkHandler handler;
    private GameController gameController;


    /**
     * This method verifies that the constructor correctly operates.
     */
    @BeforeEach
    void init() {
        handler = new NetworkHandler();
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
       String adminName = "TestUser";
       int id = 0;
       for (int i = 2; i <= 4; i++) {
           gameController.createGame(adminName, i, id);
           assertNotNull(gameController.getGame());
           assertNotNull(gameController.getGame().getPlayers());
           assertEquals(1, gameController.getGame().getPlayers().size());
           assertEquals(adminName, gameController.getGame().getPlayers().get(0).getNickname());
           assertEquals(adminName, gameController.getGame().getGameInfo().getAdmin().getNickname());
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
        String user = "TestUser";
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
        String user = "TestUser";
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
        String user = "TestUser";
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
        String user = "TestUser";
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
        String user = "TestUser";
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
        String user = "TestUser";
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
                    gameController.getOrderedPlayersNicks().get(0)
            );

            gameController.onGameMessageReceived(gameMessage);
            assertTrue((gameController.getTurnPhase() instanceof PickFromBoardPhase));
        }
    }

    @Test
    public void pickFromBoardTest_InvalidMessage_WrongUser() {
        String user = "TestUser";
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
        String user = "TestUser";
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
        String user = "TestUser";
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
        String user = "TestUser";
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
        String user = "TestUser";
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
        String user = "TestUser";
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
    //TODO ADD JAVADOC
    //TODO ADD TEST CASE FOR THE END OF THE GAME (GAME FOR 2 PLAYERS, WITH THE SECOND PLAYER WITH SHELF FILLED WITH
    // ONLY AN EMPTY SPACE)
}
