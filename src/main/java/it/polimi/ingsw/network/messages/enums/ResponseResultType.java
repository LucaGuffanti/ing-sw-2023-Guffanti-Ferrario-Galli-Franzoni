package it.polimi.ingsw.network.messages.enums;

/**
 * A response result can be either a success or a failure. This enumeration describes the result of a response.
 * @author Luca Guffanti
 */
public enum ResponseResultType {
    /**
     * The action was performed with success
     */
    SUCCESS,
    /**
     * The action was denied
     */
    FAILURE
}
