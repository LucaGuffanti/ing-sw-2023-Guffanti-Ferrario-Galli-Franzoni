package it.polimi.ingsw.network.messages.enums;
import it.polimi.ingsw.network.messages.*;
/**
 * The message type enumeration contains all the possible type headers of messages that are recognized by the system
 * and exchanged in the system.
 * @author Luca Guffanti
 * */
public enum MessageType {
    /**
     * header of a {@link ConnectionEstablishedMessage} message
     */
    CONNECTION_ESTABLISHED,
    /**
     * header of a {@link LoginRequestMessage} message
     */
    LOGIN_REQUEST,
    /**
     * header of a {@link LoginResponseMessage} message
     */
    LOGIN_RESPONSE,
    /**
     * header of a {@link JoinGameMessage} message
     */
    JOIN_GAME,
    /**
     * header of a {@link PickNumberOfPlayersMessage} message
     */
    PICK_NUMBER_OF_PLAYERS,
    /**
     * header of a {@link NumberOfPlayersSelectionMessage} message
     */
    NUMBER_OF_PLAYERS_SELECTION,
    /**
     * header of a {@link AccessResultMessage} message
     */
    ACCESS_RESULT,
    /**
     * header of a {@link NewPlayerMessage} message
     */
    NEW_PLAYER,
    /**
     * header of a {@link GameStartMessage} message
     */
    GAME_START,
    /**
     * header of a {@link BeginningOfTurnMessage} message
     */
    BEGINNING_OF_TURN,
    /**
     * header of a {@link PickFromBoardMessage} message
     */
    PICK_FROM_BOARD,
    /**
     * header of a {@link PickFromBoardResultMessage} message
     */
    PICK_FROM_BOARD_RESULT,
    /**
     * header of a {@link SelectColumnMessage} message
     */
    SELECT_COLUMN,
    /**
     * header of a {@link SelectColumnResultMessage} message
     */
    SELECT_COLUMN_RESULT,
    /**
     * header of a {@link EndOfTurnMessage} message
     */
    END_OF_TURN,
    /**
     * header of a {@link EndOfGameMessage} message
     */
    END_OF_GAME,
    /**
     * header of a {@link PingRequestMessage} message
     */
    PING_REQUEST,
    /**
     * header of a {@link ChatMessage} message
     */
    CHAT_MESSAGE,
    /**
     * header of a {@link FoundSavedGameMessage} message
     */
    FOUND_SAVED_GAME,
    /**
     * header of a {@link FoundSavedGameResponseMessage} message
     */
    FOUND_SAVED_GAME_RESPONSE,
    /**
     * header of a {@link AbortedGameMessage} message
     */
    ABORTED_GAME,
    /**
     * header of a {@link WaitForLobbyMessage} message
     */
    WAIT_FOR_LOBBY
}
