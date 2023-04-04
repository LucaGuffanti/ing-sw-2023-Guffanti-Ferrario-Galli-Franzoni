package it.polimi.ingsw.model;

import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.utils.MatrixUtils;
import it.polimi.ingsw.model.utils.exceptions.MaxPlayersException;
import it.polimi.ingsw.model.utils.exceptions.WrongNumberOfPlayersException;
import org.junit.jupiter.api.Test;

import javax.swing.plaf.synth.SynthIcon;

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
        System.out.println("Board initialized");

        Game game = new Game(new Player("admin"), 2, 0);
        game.addPlayer("Marco");
        game.initGame();
        System.out.println("Board should be refilled: "+board.shouldBeRefilled());

    }
}
