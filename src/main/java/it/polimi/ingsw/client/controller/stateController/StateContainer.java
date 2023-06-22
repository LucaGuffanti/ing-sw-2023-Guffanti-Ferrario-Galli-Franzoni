package it.polimi.ingsw.client.controller.stateController;

import it.polimi.ingsw.client.controller.messageHandling.Reducer;
import it.polimi.ingsw.client.controller.utils.PropsChangesNotifier;
import it.polimi.ingsw.network.messages.Message;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.*;

/**
 * A StateContainer is an object which holds the application's state,
 * which can be acquired or updated through the StateContainer supplied method (dispatch).
 * @see StateContainer#dispatch(Message)
 */
public class StateContainer {

    private List<ClientState> statesHistory;
    private final PropsChangesNotifier<ClientState> notifier;
    private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    public StateContainer(ClientState initialState) {
        this.statesHistory = new ArrayList<>();
        this.statesHistory.add(initialState);
        this.notifier = new PropsChangesNotifier<>();

    }

    public ClientState getCurrentState() {
        return this.statesHistory.get(statesHistory.size()-1);
    }

    /**
     * Dispatch methods are the only way to request a state update to the StateContainer.
     * The new state will be calculated based on the message payload.
     * The updated attributes of the state will be notified to the listeners.
     * @param message
     */
    public void dispatch(Message message){
        // Transform current status in the next one and add it to the statesHistory
        ClientState oldState = statesHistory.get(statesHistory.size()-1);
        ClientState newState = Reducer.reduce(this.getCurrentState(), message);
        statesHistory.add(newState);
        try {
            notifier.checkAndNotify(oldState, newState, propertyChangeSupport);
        }catch (Exception e){
            e.printStackTrace();
        };
    }

    /**
     * Add listeners for ClientState updates
     * @param listener
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }



}
