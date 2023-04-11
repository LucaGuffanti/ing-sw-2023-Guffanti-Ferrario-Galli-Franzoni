package it.polimi.ingsw.controller.phases;

import it.polimi.ingsw.network.messages.Message;

/**
 * A game phase is requires a message if
 * in its operations it manages an incoming message (join game request, card pick request ...) <br>
 * The game enters the phase and operations are done only when a correct message is received
 */
public interface RequiredMessageGamePhase {
    void manageIncomingMessage(Message message);
    void validate(Message message);
    void execute(Message message);
    void wrapPhaseAndAdvance();
}
