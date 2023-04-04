package it.polimi.ingsw.model.utils.exceptions;

public abstract class IllegalBoardCellsPickException extends Exception{
    public IllegalBoardCellsPickException(String message){
        super(message);
    }
}
