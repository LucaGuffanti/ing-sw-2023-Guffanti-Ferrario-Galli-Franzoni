package it.polimi.ingsw.client.controller;

/**
 * All the phases the client goes through during a game.
 * @author Daniele Ferrario
 */
public enum ClientPhasesEnum {
    /**
     * The client is presented with the login prompt and enters its wanted username
     */
    LOGIN,
    /**
     * After the login phase but before the client joins a lobby, waits for the creation of a lobby, or is
     * requested to choose the number of players
     */
    NOT_JOINED,
    /**
     * When a client tries to join a game which is already started
     */
    ALREADY_STARTED,
    /**
     * When a client in queued to join a lobby while an admin is choosing the number of players
     */
    WAITING_FOR_LOBBY,
    /**
     * The client has joined the lobby but the game hasn't started yet
     */
    LOBBY,
    /**
     * A saved game was found and the client is required to choose between reloading the game and starting a new
     * game from scratch
     */
    DECIDING_FOR_RELOAD,
    /**
     * The game is running, but it's not the client's turn
     */
    WAITING_FOR_TURN,
    /**
     * The client has to choose a tile that should be picked from the board
     */
    PICK_FORM_BOARD,
    /**
     * The client has to choose a column of the shelf in which to insert the picked tiles
     */
    SELECT_COLUMN,
    /**
     * Due to connection issues or a client exiting the game, the game is terminated
     */
    ABORTED_GAME,
    /**
     * The final results of the game are shown
     */
    FINAL_RESULTS_SHOW,
    /**
     * The client is the admin of the game, and is required to choose the number of players
     */
    PICK_PLAYERS,
    /**
     * The client is too far in the queue of waiting players. When the admin chooses the number of
     * players the game is saturated before the client could access the game
     */
    NOT_ADMITTED
}
