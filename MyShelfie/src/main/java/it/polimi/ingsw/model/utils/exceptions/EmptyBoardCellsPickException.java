package it.polimi.ingsw.model.utils.exceptions;

public class EmptyBoardCellsPickException extends IllegalBoardCellsPickException {
    public EmptyBoardCellsPickException(int x, int y) {
        super("Cannot pick a tile from the board at position ("+x+","+y+") because BoardCell is empty.");
    }
}
