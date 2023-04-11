package it.polimi.ingsw.controller.phases;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.Game;

/**
 * A game phase is the representation of a particular phase of the game.
 * @author Luca Guffanti
 */
public abstract class GamePhase {
    GameController controller;
    Game game;
}
