package it.polimi.ingsw.server.model.utils.exceptions;

/**
 * Thrown when the sack is empty and the board is being refilled
 * @author Luca Guffanti
 */
public class EmptySackException extends Exception{
    public EmptySackException() {
            super("The sack is empty.");
    }
}
