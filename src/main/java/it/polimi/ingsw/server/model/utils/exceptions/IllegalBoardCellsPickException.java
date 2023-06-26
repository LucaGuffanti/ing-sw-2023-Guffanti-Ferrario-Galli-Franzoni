package it.polimi.ingsw.server.model.utils.exceptions;

/**
 * Thrown when an illegal cell pick is submitted
 * @author Daniele Ferrario
 */
public abstract class IllegalBoardCellsPickException extends Exception{
    public IllegalBoardCellsPickException(String message){
        super(message);
    }
}
