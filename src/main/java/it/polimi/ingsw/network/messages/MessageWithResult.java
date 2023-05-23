package it.polimi.ingsw.network.messages;


import it.polimi.ingsw.network.messages.enums.ResponseResultType;

/**
 * This interface represents the methods which return
 * a su
 */
public interface MessageWithResult {
    public ResponseResultType getResultType();

}
