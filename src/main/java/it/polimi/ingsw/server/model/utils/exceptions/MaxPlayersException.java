package it.polimi.ingsw.server.model.utils.exceptions;

/**
 * Thrown when a player is trying to access a game that is full
 * @author Luca Guffanti
 */
public class MaxPlayersException extends Exception{
    public MaxPlayersException(String message) {
        super(message);
    }
}
