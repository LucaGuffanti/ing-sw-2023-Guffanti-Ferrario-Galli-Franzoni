package it.polimi.ingsw.client.controller.stateController;

import it.polimi.ingsw.client.controller.messageHandling.Reducer;
import it.polimi.ingsw.network.messages.Message;

import java.util.Observable;
import java.util.Observer;
import java.util.Set;

/**
 * A StateContainer is an object which holds the application's state,
 * which can be acquired or updated through the supplied methods.
 * @TODO: Remove deprecated Observable/Observer paradigm.
 */
public class StateContainer extends Observable {
    private ClientState state;
    private Set<Observer> stateObservers;

    public StateContainer(ClientState initialState) {
        this.state = initialState;
    }

    public ClientState getState() {
        return state;
    }

    /**
     * Dispatch methods are the only way to request a state update to the StateContainer.
     * The new state will be calculated based on the message payload.
     * @param message
     */
    public void dispatch(Message message){
        state = Reducer.reduce(this.state, message);
    }

    /**
     * Add the observer to the set.
     * @Todo: implementing state changes notification logic
     */
    public void subscribe(Observer observer){
        stateObservers.add(observer);

    }

}
