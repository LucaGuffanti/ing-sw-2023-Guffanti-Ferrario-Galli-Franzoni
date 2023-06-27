package it.polimi.ingsw.server.controller;

/**
 * This enumeration contains the possible states in which the game may be
 */
public enum GameStatusEnum {
    /**
     * The game is accepting players
     */
    ACCEPTING_PLAYERS,
    /**
     * A save file was found
     */
    FOUND_SAVE_FILE,
    /**
     * The game is started and is progressing
     */
    STARTED,
    /**
     * A player has completed the shelf
     */
    FINAL_TURNS,
    /**
    * A game has ended
     */
    ENDED
}
