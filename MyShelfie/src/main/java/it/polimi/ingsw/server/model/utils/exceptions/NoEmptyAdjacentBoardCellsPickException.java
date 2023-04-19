package it.polimi.ingsw.server.model.utils.exceptions;

public class NoEmptyAdjacentBoardCellsPickException extends IllegalBoardCellsPickException {
    public NoEmptyAdjacentBoardCellsPickException(){
        super("Not all tiles have an adjacent free board cell");
    }
}
