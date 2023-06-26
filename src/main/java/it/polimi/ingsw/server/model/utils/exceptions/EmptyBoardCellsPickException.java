package it.polimi.ingsw.server.model.utils.exceptions;

/**
 * Thrown when a player is trying to pick a tile from an empty cell
 * @author Daniele Ferrario
 */
public class EmptyBoardCellsPickException extends IllegalBoardCellsPickException {
    public EmptyBoardCellsPickException(int x, int y) {
        super("Cannot pick a tile from the board at position ("+x+","+y+") because BoardCell is empty.");
    }
}
