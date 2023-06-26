package it.polimi.ingsw.server.model.utils.exceptions;

/**
 * Thrown when, while trying to build or manage a game, a non rule compliant number of players is set
 */
public class WrongNumberOfPlayersException extends Exception{
    public WrongNumberOfPlayersException(int num) {
        super("Got " + num + " players. It should be between 2 and 4");
    }
}
