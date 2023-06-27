package it.polimi.ingsw.network.messages.enums;

/**
 * This enumeration contains the two possible results of the request of the loading of an already started game
 * @author Luca Guffanti
 */
public enum ReloadGameChoice {
    /**
     * The reload is accepted
     */
    ACCEPT_RELOAD,
    /**
     * The reload is declined
     */
    DECLINE_RELOAD
}
