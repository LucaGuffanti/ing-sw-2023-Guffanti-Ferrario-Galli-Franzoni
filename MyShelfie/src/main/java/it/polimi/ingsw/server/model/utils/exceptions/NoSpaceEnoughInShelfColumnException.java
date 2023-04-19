package it.polimi.ingsw.server.model.utils.exceptions;

public class NoSpaceEnoughInShelfColumnException extends IllegalShelfActionException{
    public NoSpaceEnoughInShelfColumnException(int columnIndex, int availableSpace) {
        super("There is not enough space in column n. " + columnIndex+".\nAvailable space: "+availableSpace+".");
    }
}
