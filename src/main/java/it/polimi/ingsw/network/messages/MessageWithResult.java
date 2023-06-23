package it.polimi.ingsw.network.messages;


import it.polimi.ingsw.network.messages.enums.ResponseResultType;

/**
 * This interface represents messages that contain the result to a requested action
 */
public interface MessageWithResult {
    public ResponseResultType getResultType();

}
