package it.polimi.ingsw.model.utils.exceptions;

public class DiagonalBoardCellsCellsPickException extends IllegalBoardCellsPickException {
    public DiagonalBoardCellsCellsPickException(){
        super("Tiles can be picked only from a column or a row");
    }
}
