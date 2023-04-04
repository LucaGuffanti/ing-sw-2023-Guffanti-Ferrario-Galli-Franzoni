package it.polimi.ingsw.model;

import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.utils.exceptions.MaxPlayersException;
import it.polimi.ingsw.model.utils.exceptions.WrongNumberOfPlayersException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    @Test
    void refillBoard() {
    }

    @Test
    void initBoard() {
    }

    @Test
    void shouldBeRefilled() throws WrongNumberOfPlayersException, MaxPlayersException {
        Board board = new Board(2, new Sack());
        Game game = new Game(new Player("admin"), 2, 0);
        game.addPlayer("Daniele");
        game.addPlayer("Marco");
        game.initGame();
    }
}
