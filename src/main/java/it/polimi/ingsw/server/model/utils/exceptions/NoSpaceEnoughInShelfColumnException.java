package it.polimi.ingsw.server.model.utils.exceptions;

/**
 * Thrown when it's impossible to add tiles to a particular column of the shelf as there's no more space
 * @author Daniele Ferrario
 */
public class NoSpaceEnoughInShelfColumnException extends IllegalShelfActionException{
    public NoSpaceEnoughInShelfColumnException(int columnIndex, int availableSpace) {
        super("There is not enough space in column n. " + columnIndex+".\nAvailable space: "+availableSpace+".");
    }
}
