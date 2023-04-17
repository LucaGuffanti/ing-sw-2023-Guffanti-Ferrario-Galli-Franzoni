package it.polimi.ingsw.client.controller;

/**
 * The
 */
public enum ClientPhasesEnum {
    DISCONNECTED,
    LOBBY,
    WAITING_FOR_TURN,
    PICK_FORM_BOARD,
    COLUMN_CHOICE,
    FINAL_RESULTS_SHOW,
    END;

    ClientPhasesEnum() {
    }


}
