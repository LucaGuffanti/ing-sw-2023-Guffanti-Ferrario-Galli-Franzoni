package it.polimi.ingsw.client.controller.stateController;

import it.polimi.ingsw.client.controller.messageHandling.Reducer;
import it.polimi.ingsw.client.controller.utils.PropsChangesNotifier;
import it.polimi.ingsw.network.messages.Message;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.*;

/**
 * A StateContainer is an object which holds the application's state,
 * which can be acquired or updated through the apposite StateContainer supplied methods.
 * @see StateContainer#updateState(Message)
 */
public class StateContainer {
    /**
     * The history of the states
     */
    private List<ClientState> statesHistory;

    /**
     * Application notifier, triggered on state update.
     */
    private final PropsChangesNotifier<ClientState> notifier;

    /**
     * The reference support for the notifier.
     */
    private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    public StateContainer(ClientState initialState) {
        this.statesHistory = new ArrayList<>();
        this.statesHistory.add(initialState);
        this.notifier = new PropsChangesNotifier<>();

    }

    public ClientState getCurrentState() {
        return this.statesHistory.get(statesHistory.size()-1);
    }

    public List<ClientState> getStatesHistory() {
        return statesHistory;
    }

    /**
     * UpdateState is the method which handles the state update requests.
     * The new state will be calculated based on the message payload through the
     * appropriate Reducer.
     *
     * The updated attributes of the state will be notified to the listeners.
     * @param message Message object containing the payload.
     */
    public void updateState(Message message){

        // Get the right handler for the message.
        Reducer handler = (Reducer) message.getHandlerForClient();

        // Transform the current state in the next one and add it to the states History
        ClientState oldState = statesHistory.get(statesHistory.size()-1);
        ClientState newState = handler.reduce(this.getCurrentState(), message);

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
