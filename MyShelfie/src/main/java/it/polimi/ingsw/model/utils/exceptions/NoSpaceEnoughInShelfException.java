package it.polimi.ingsw.model.utils.exceptions;

public class NoSpaceEnoughInShelfException extends IllegalShelfActionException{
    public NoSpaceEnoughInShelfException(int availableSpace) {
        super("There is not enough space in the shelf for this amount of tiles. Total Available space: "+availableSpace+".");
    }
}
