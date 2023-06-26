package it.polimi.ingsw.server.model.utils.exceptions;

/**
 * Thrown when tiles can't be added to a shelf as there's not enough space left
 * @author Daniele Ferrario
 */
public class NoSpaceEnoughInShelfException extends IllegalShelfActionException{
    public NoSpaceEnoughInShelfException(int availableSpace) {
        super("There is not enough space in the shelf for this amount of tiles. Total Available space: "+availableSpace+".");
    }
}
