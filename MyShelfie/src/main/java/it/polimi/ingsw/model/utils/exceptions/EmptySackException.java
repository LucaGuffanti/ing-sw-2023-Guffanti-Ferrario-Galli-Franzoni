package it.polimi.ingsw.model.utils.exceptions;

public class EmptySackException extends Exception{
    public EmptySackException() {
            super("The sack is empty.");
    }
}
