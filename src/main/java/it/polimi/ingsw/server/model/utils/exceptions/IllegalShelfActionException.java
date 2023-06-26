package it.polimi.ingsw.server.model.utils.exceptions;

/**
 * Generic exception regarding a move made on the shelf that does not comply with the rules
 * @author Daniele Ferrario
 */
public abstract class IllegalShelfActionException extends Exception{
    public IllegalShelfActionException(String message) {
        super(message);
    }
}
