package it.polimi.ingsw.client.controller.messageHandling;

import it.polimi.ingsw.client.controller.messageHandling.messageHandlers.MessagesHandler;
import it.polimi.ingsw.client.controller.stateController.*;
import it.polimi.ingsw.network.messages.Message;

import java.beans.PropertyChangeSupport;

/**
 * A Reducer is a MessageHandler that implements the client state update logic. Reducer methods are pure functions
 * that will accept the previous state of the app and the Message containing the payload used to calculate
 * the next state object.
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
     * This method is called by the StateContainer after a dispatch request and acts as
     * a wrapper for every type of message that is passed.
     * @param oldClientState
     * @param message
     * @see StateContainer
     * @return The next state of the application.
     */
    public static ClientState reduce(ClientState oldClientState, Message message){
        // Get the right handler for the message.
        Reducer handler = (Reducer) message.getHandlerForClient();
        return handler.executeReduce(oldClientState, message);
    }


    /**
     * This method implements effectively the state update which depends on the
     * specific handler implementation relative to the message type. It's only called after Reducer.reduce(...) wrapper method.
     *
     * @param oldState
     * @param m
     * @return The next state of the application.
     *
     */
    protected abstract ClientState executeReduce(ClientState oldState, Message m);
}
