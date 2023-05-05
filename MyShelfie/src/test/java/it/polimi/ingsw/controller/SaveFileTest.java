package it.polimi.ingsw.controller;

import it.polimi.ingsw.client.view.cli.Printer;
import it.polimi.ingsw.server.controller.GameController;
import it.polimi.ingsw.server.controller.save.SaveFileManager;
import it.polimi.ingsw.server.controller.utils.GameObjectConverter;
import it.polimi.ingsw.server.model.cards.ObjectTypeEnum;
import org.junit.Test;

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
        SaveFileManager.saveGameState(gameController.getGame(), gameController, "src/test/resources/saveTEST/testSave.json");
    }
}
