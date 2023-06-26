package it.polimi.ingsw.server.model.utils.exceptions;

/**
 * Thrown when a move is trying to pick tiles which don't have at least a free side
 * @author Daniele Ferrario
 */
public class NoEmptyAdjacentBoardCellsPickException extends IllegalBoardCellsPickException {
    public NoEmptyAdjacentBoardCellsPickException(){
        super("Not all tiles have an adjacent free board cell");
    }
}
