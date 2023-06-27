package it.polimi.ingsw.network.utils;

import it.polimi.ingsw.network.messages.enums.ResponseResultType;

/**
 * This class contains different messages descriptions. The attributes are self-descriptive
 * @author Luca Guffanti
 */
public class ResponsesDescriptions {
    public static final String BADLY_FORMATTED = "The request was badly formatted.";
    public static final String PICKING_ERROR_WRONG_PICK = "The cards you tried to pick cannot be picked... Retry!";
    public static final String PICKING_ERROR_NO_SPACE = "You tried to pick too many cards: there's not enough space in your shelf!";
    public static final String PICKING_SUCCESS = "You picked your cards, now select a column in which to put them!";
    public static final String SHELF_ERROR_WRONG_SELECTION = "There's not enough space in the column you chose for all the cards... Retry!";
    public static final String SHELF_SUCCESS = "You successfully put the cards in the shelf!";
    public static final String JOIN_SUCCESS = "You successfully joined the game.";
    public static final String NEW_PLAYER_JOINED = " joined the game.";
    public static final String JOIN_FAILURE_MAX_PLAYERS = "You can't join the game because it's already full";
    public static final String JOIN_FAILURE_ALREADY_STARTED = "You can't join the game because it's already started.";
    public static final String JOIN_FAILURE_ALREADY_CREATED = "You can't create a game that's already been created.";
    public static final String END_OF_TURN_REFILL_BOARD = "The board was refilled.";
    public static final String END_OF_TURN_COMPLETED_SHELF = "The shelf was completed.";
    public static final String END_OF_TURN_CG1_COMPLETED = "The first common goal card was completed.";
    public static final String END_OF_TURN_CG2_COMPLETED = "The second common goal card was completed.";
    public static final String NEW_TURN = "A new turn has started.";
    public static final String GAME_STARTED = "The game has started.";
    public static final String GAME_ENDED = "The game has ended.";
    public static final String LOGIN_SUCCESS = "You've successfully joined the game";
    public static final String RECONNECTION_SUCCESS = "You've successfully logged BACK into the server";
    public static final String LOGIN_FAILURE_ALREADY_TAKEN = "Your username was already taken";
    public static final String FOUND_COMPATIBLE_SAVED_GAME = "A compatible saved game was found. Do you want to reload it?";
    public static final String FAILURE_NOT_ADMITTED = "You were not admitted because there were too many players in queue";
}
