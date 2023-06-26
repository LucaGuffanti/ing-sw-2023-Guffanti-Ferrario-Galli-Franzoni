package it.polimi.ingsw.server.model.utils.exceptions;

/**
 * Thrown when trying to set a non rule compliant number of points to a common goal card
 * @author Daniele Ferrario
 */
public class WrongPointCardsValueGivenException extends Exception{
    public WrongPointCardsValueGivenException(int num) {
        super("Got " + num + " points to set. The point cards are limited to 0, 2, 4, 6, 8 points ");
    }
}
