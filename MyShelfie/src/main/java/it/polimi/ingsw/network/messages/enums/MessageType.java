package it.polimi.ingsw.network.messages.enums;

/**
 * The message type enumeration contains all the possible types of messages that are recognized by the system
 * and exchanged in the system.
 * @author Luca Guffanti
 * */
public enum MessageType {
    CONNECTION_ESTABLISHED,
    LOGIN_REQUEST,
    LOGIN_RESPONSE,
    JOIN_GAME,
    PICK_NUMBER_OF_PLAYERS,
    NUMBER_OF_PLAYERS_SELECTION,
    ACCESS_RESULT,
    NEW_PLAYER,
    GAME_START,
    BEGINNING_OF_TURN,
    PICK_FROM_BOARD,
    PICK_FROM_BOARD_RESULT,
    SELECT_COLUMN,
    SELECT_COLUMN_RESULT,
    END_OF_TURN,
    END_OF_GAME,
    PING_REQUEST,
    PING_REPLY,
    LOGOUT_MESSAGE,
    CHAT_MESSAGE,
    FOUND_SAVED_GAME,
    FOUND_SAVED_GAME_RESPONSE,
    ABORTED_GAME,
    REJOIN_GAME,
    WAIT_FOR_LOBBY


    //TODO ADD MESSAGES FOR PLAYER RECONNECTION
}
