package it.polimi.ingsw.server.controller;

/**
 * This enumeration contains the possible states in which the game may be
 */
public enum GameStatusEnum {
    ACCEPTING_PLAYERS,
    FOUND_SAVE_FILE,
    STARTED,
    FINAL_TURNS,
    ENDED
}
