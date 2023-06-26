package it.polimi.ingsw.server.model.utils.exceptions;

/**
 * Thrown when a player is trying to pick tiles which don't share either the column or the row
 * @author Daniele Ferrario
 */
public class DiagonalBoardCellsCellsPickException extends IllegalBoardCellsPickException {
    public DiagonalBoardCellsCellsPickException(){
        super("Tiles can be picked only from a column or a row");
    }
}
