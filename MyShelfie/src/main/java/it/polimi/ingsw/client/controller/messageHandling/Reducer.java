package it.polimi.ingsw.client.controller.messageHandling;

import it.polimi.ingsw.client.controller.messageHandling.messageHandlers.MessagesHandler;
import it.polimi.ingsw.client.controller.stateController.ClientState;
import it.polimi.ingsw.network.messages.Message;

/**
 * A Reducer is a MessageHandler that implements the client state update logic. Reducer methods are pure functions
 * that will accept the previous state of the app and the Message containing the payload used to calculate
 * the next state object.
 *
 * @author Daniele Ferrario
 */
public abstract class Reducer implements MessagesHandler {

    /**
     * This method is called by the StateContainer after a dispatch request and acts as
     * a wrapper for every type of message that is passed.
     * @param oldClientState
     * @param message
     * @return The next state of the application.
     */
    public static ClientState reduce(ClientState oldClientState, Message message){
        // Get the right handler for the message.
        Reducer handler = (Reducer) message.getHandler();
        return handler.executeReduce(oldClientState, message);
    }

    /**
     * This method implements effectively the state update which depends on the
     * specific handler relative to the message type. It's only called after Reducer.reduce(...) wrapper method.
     *
     * @param oldState
     * @param m
     * @return The next state of the application.
     *
     */
    protected abstract ClientState executeReduce(ClientState oldState, Message m);
}
