package it.polimi.ingsw.controller.phases;

/**
 * A game phase that does not require an incoming message to operate: it simply executes some operations and is
 * triggered by a controller command
 */
public interface UnrequiredMessageGamePhase {
    void execute();
    void wrapPhaseAndAdvance();
}
