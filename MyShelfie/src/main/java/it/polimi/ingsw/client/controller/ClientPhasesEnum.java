package it.polimi.ingsw.client.controller;

/**
 * All the phases in which the Client can be found.
 * @author Daniele Ferrario
 */
public enum ClientPhasesEnum {
    DISCONNECTED,
    CONNECTED,
    LOBBY,
    WAITING_FOR_TURN,
    PICK_FORM_BOARD,
    COLUMN_CHOICE,
    FINAL_RESULTS_SHOW,
    END;




}
