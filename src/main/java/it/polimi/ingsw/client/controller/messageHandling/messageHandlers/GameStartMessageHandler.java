package it.polimi.ingsw.client.controller.messageHandling.messageHandlers;

import it.polimi.ingsw.client.controller.messageHandling.Reducer;
import it.polimi.ingsw.client.controller.stateController.ClientState;
import it.polimi.ingsw.network.messages.GameStartMessage;
import it.polimi.ingsw.network.messages.Message;

/**
 * Handles the reception of the message representing
 * all the objects required for the view in the beginning of a game.
 *
 * @see GameStartMessage
 * @author Daniele Ferrario
 */
public class GameStartMessageHandler extends Reducer {


    /**
     *  Sets the OrderedPlayers list, the user's Personal Goal Card,
     *  Common Goal Cards and the Board in the beginning of the game.
     *
     * @param oldClientState the old state of the client
     * @param m the received message
     * @return The next state of the app.
     */
    @Override
    public ClientState reduce(ClientState oldClientState, Message m){
        ClientState state = null;
        GameStartMessage gameStartMessage = (GameStartMessage) m;

        try {
            state = (ClientState) oldClientState.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }

        state.setOrderedPlayersNames(gameStartMessage.getOrderedPlayers());

        // Getting the turn index of user
        int userPosition = state.getOrderedPlayersNames().indexOf(state.getUsername());

        // Setting the personal goal card associated to the player
        state.setPersonalGoalCardId(gameStartMessage.getClientPersonalGoals().get(userPosition));
        state.setCommonGoalCards(gameStartMessage.getClientCommonGoalCards());
        state.setBoard(gameStartMessage.getClientBoard());
        state.setShelves(gameStartMessage.getShelves());
        state.setFirstToCompleteShelf(gameStartMessage.getFirstToCompleteTheShelf());

        return state;
    }


}
