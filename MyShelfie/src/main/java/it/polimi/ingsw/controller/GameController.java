package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.phases.TurnPhase;
import it.polimi.ingsw.model.Game;

import java.util.ArrayList;

/**
 * The GameController is the controller of the game: it keeps an instance of the game, interacting with it when messages are received
 * and receiving notifications when some situations are met.
 * @author Luca Guffanti
 */
public class GameController {
    /**
     * The game instance
     */
    private Game game;
    /**
     * The turnPhase in which the turn is.
     */
    private TurnPhase turnPhase;
    /**
     * The list of players that log into the game. It will be shuffled when the game starts
     */
    private ArrayList<String> OrderedPlayersNicks;
    /**
     * Index at which the list of players contains the player that's active for a given turn.
     */
    private int activePlayerIndex;
    /**
     * Whether a player in his turn has completed the first common goal
     */
    private boolean firstCommonGoalCompletedByActivePlayer;
    /**
     * Whether a player in his turn has completed the second common goal
     */
    private boolean secondCommonGoalCompletedByActivePlayer;


    public GameController() {

    }
    public Game getGame() {
        return game;
    }

    public TurnPhase getTurnPhase() {
        return turnPhase;
    }

    public ArrayList<String> getOrderedPlayersNicks() {
        return OrderedPlayersNicks;
    }

    public int getActivePlayerIndex() {
        return activePlayerIndex;
    }

    public boolean isFirstCommonGoalCompletedByActivePlayer() {
        return firstCommonGoalCompletedByActivePlayer;
    }

    public boolean isSecondCommonGoalCompletedByActivePlayer() {
        return secondCommonGoalCompletedByActivePlayer;
    }
}
