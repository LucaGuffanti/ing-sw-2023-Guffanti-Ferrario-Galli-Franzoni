package it.polimi.ingsw.server.model.utils.exceptions;

public abstract class IllegalBoardCellsPickException extends Exception{
    public IllegalBoardCellsPickException(String message){
        super(message);
    }
}
