package it.polimi.ingsw.model.utils.exceptions;

public class WrongPointCardsValueGivenException extends Exception{
    public WrongPointCardsValueGivenException(int num) {
        super("Got " + num + " points to set. The point cards are limited to 0, 2, 4, 6, 8 points ");
    }
}
