package it.polimi.ingsw.controller;

import it.polimi.ingsw.client.view.cli.Printer;
import it.polimi.ingsw.server.controller.GameController;
import it.polimi.ingsw.server.controller.save.SaveFileData;
import it.polimi.ingsw.server.controller.save.SaveFileManager;
import it.polimi.ingsw.server.controller.utils.GameObjectConverter;
import it.polimi.ingsw.server.model.Board;
import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.cards.ObjectTypeEnum;
import it.polimi.ingsw.server.model.player.Shelf;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

/**
 * This class contains tests that verify the correct behavior
 * of the methods that save a game state to disk and that retrieve a game state from disk
 */
public class SaveFileTest {

    @Test
    public void savingTest() {
        FakeServerNetworkHandler fakeServerNetworkHandler = new FakeServerNetworkHandler();
        GameController gameController = new GameController(fakeServerNetworkHandler);
        gameController.createGame("Test0", 4, 0);
        gameController.onPlayerJoin("Test1");
        gameController.onPlayerJoin("Test2");
        gameController.onPlayerJoin("Test3");

        Printer.printBoard(GameObjectConverter.fromBoardToMatrix(gameController.getGame().getBoard()));
        Printer.printBoard(GameObjectConverter.fromShelfToMatrix(gameController.
                getGame().getPlayerByNick(gameController.getOrderedPlayersNicks().get(gameController.getActivePlayerIndex()))
                .getShelf()));
        SaveFileManager.saveGameState(gameController.getGame(), gameController, "src/test/resources/saveTEST","src/test/resources/saveTEST/testSave.json");
    }

    @Test
    public void loadingTest() throws IOException {
        String testFilePath = "src/test/resources/saveTEST/testSave.json";
        FakeServerNetworkHandler fakeServerNetworkHandler = new FakeServerNetworkHandler();
        GameController gameController = new GameController(fakeServerNetworkHandler, testFilePath);
        //GameController gameController = new GameController(fakeServerNetworkHandler);
        gameController.createGame("Test0", 4, 0);
        gameController.onPlayerJoin("Test1");
        gameController.onPlayerJoin("Test2");
        gameController.onPlayerJoin("Test3");

        gameController.reloadExistingGame();

        Board oldBoard = gameController.getGame().getBoard();
        Shelf oldActiveShelf = gameController.
                getGame().getPlayerByNick(gameController.getOrderedPlayersNicks().get(gameController.getActivePlayerIndex()))
                .getShelf();
        SaveFileManager.saveGameState(gameController.getGame(), gameController, "src/test/resources/saveTEST", testFilePath);

        // Re-initialize
        gameController = new GameController(fakeServerNetworkHandler, testFilePath);
        gameController.createGame("Test0", 4, 0);
        gameController.onPlayerJoin("Test1");
        gameController.onPlayerJoin("Test2");
        gameController.onPlayerJoin("Test3");

        gameController.reloadExistingGame();

        Printer.printBoard(GameObjectConverter.fromBoardToMatrix(oldBoard));
        Printer.printBoard(GameObjectConverter.fromBoardToMatrix(gameController.getGame().getBoard()));
        Printer.printBoard(GameObjectConverter.fromShelfToMatrix(oldActiveShelf));
        Printer.printBoard(GameObjectConverter.fromShelfToMatrix(gameController.
                getGame().getPlayerByNick(gameController.getOrderedPlayersNicks().get(gameController.getActivePlayerIndex()))
                .getShelf()));

    }

    @Test
    public void reloadingTest() throws IOException {
        SaveFileData data = SaveFileManager.loadGameState(new File("src/test/resources/saveTEST/testSave.json"));
        System.out.println(data.toString());
    }
}
