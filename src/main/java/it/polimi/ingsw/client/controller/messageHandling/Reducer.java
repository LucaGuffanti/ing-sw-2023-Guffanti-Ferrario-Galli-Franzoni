package it.polimi.ingsw.client.controller.messageHandling;

import it.polimi.ingsw.client.controller.messageHandling.messageHandlers.MessagesHandler;
import it.polimi.ingsw.client.controller.stateController.*;
import it.polimi.ingsw.network.messages.Message;

/**
 * A Reducer is a MessageHandler that implements the client state update logic. Reducer methods are pure functions
 * that accept the previous state of the app and a Message containing the payload to calculate the new StateObject.
 *
 * @apiNote The name is inspired by "Redux" library.
 * "Reducers reduce a set of actions over time (described here in Messages classes) into a single state.
 * The name is inspired from the behaviour of Array.reduce(), in which it happens all at once.
 * In "Redux" ( and in this project ) it happens over the lifetime of the running app."
 *
 * @author Daniele Ferrario
 */
public abstract class Reducer implements MessagesHandler {


    /**
     * This method implements effectively the state update which depends on the
     * specific handler implementation relative to the message type. It's only called after Reducer.reduce(...) wrapper method.
     *
     * @param oldState The old state
     * @param m The Message containing the payload
     * @return The next state of the application.
     *
     */
    public abstract ClientState reduce(ClientState oldState, Message m);
}
