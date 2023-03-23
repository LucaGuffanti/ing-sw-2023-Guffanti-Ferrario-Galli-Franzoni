package it.polimi.ingsw.model.utils.exceptions;

public class WrongNumberOfPlayersException extends Exception{
    public WrongNumberOfPlayersException(int num) {
        super("Got " + num + " players. It should be between 2 and 4");
    }
}
