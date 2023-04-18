package it.polimi.ingsw.controller.turn;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.network.messages.Message;

import java.util.function.Predicate;


/**
 * TurnPhase is an abstraction of every phase of the turn.
 * it contains methods that are overridden based on the specific phase the turn may be in
 */
public abstract class TurnPhase {
    protected Game game;
    protected GameController controller;

    public TurnPhase(Game game, GameController controller) {
        this.game = game;
        this.controller = controller;
    }

    /**
     * this method validates the message by considering its dynamic type,
     * the sender name (which must be equal to the active player) and the overall structure of the payload.
     * @param message the message to validate
     * @return true or false if the incoming message is valid or not.
     */
    public abstract boolean validate(Message message);

    /**
     * this method manages an incoming message. a message is validated and if valid its payload will
     * be executed, causing a change in the state of the game. The active player is then notified with a message
     * that encapsulates the result of the execution of the payload.
     * @param message the received message
     */
    public abstract void manageIncomingMessage(Message message);

    /**
     * the last method to be called in any turn phase: the controller is notified and advances the turn, also saving
     * useful information in its internal state.
     */
    public abstract void wrapPhaseAndAdvance(Message message);
}
