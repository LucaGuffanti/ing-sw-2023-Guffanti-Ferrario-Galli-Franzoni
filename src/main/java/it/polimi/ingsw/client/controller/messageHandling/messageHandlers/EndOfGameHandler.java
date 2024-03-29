package it.polimi.ingsw.client.controller.messageHandling.messageHandlers;

import it.polimi.ingsw.client.controller.ClientPhasesEnum;
import it.polimi.ingsw.client.controller.messageHandling.Reducer;
import it.polimi.ingsw.client.controller.stateController.ClientState;
import it.polimi.ingsw.network.messages.EndOfGameMessage;
import it.polimi.ingsw.network.messages.Message;


/**
 * Handles the reception of the message representing
 * the end of the game

 * @author Davide Franzoni
 */
public class EndOfGameHandler extends Reducer {

    /**
     * This method retrieves data from the message regarding the winner and the points of the player,
     * setting the phase to FINAL_RESULTS_SHOW
     * @param oldClientState The old state
     * @param m The received message
     * @return the new state of the client
     */
    @Override
    public ClientState reduce(ClientState oldClientState, Message m){


        ClientState state = null;
        EndOfGameMessage endOfGameMessage = (EndOfGameMessage) m;

        try {
            state = (ClientState) oldClientState.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }

        state.setCurrentPhase(
                ClientPhasesEnum.FINAL_RESULTS_SHOW
        );
        state.setWinnerUserName(
                endOfGameMessage.getWinner()
        );
        state.setNameToPointMap(
                endOfGameMessage.getNameToPointsMap()
        );

        return state;
    }

}
