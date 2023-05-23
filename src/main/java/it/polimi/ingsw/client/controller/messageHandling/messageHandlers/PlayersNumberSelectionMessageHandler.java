package it.polimi.ingsw.client.controller.messageHandling.messageHandlers;

import it.polimi.ingsw.client.controller.ClientPhasesEnum;
import it.polimi.ingsw.client.controller.messageHandling.Creator;
import it.polimi.ingsw.client.controller.messageHandling.Reducer;
import it.polimi.ingsw.client.controller.stateController.ClientState;
import it.polimi.ingsw.network.messages.*;

/**
 * Handles the creation and the reception of the message relative
 * to the player number selection by the admin user.
 *
 * @see PickNumberOfPlayersMessage // Request from the server
 * @see NumberOfPlayersSelectionMessage // Response from the Client
 * @author Daniele Ferrario
 */
public class PlayersNumberSelectionMessageHandler extends Reducer implements Creator {


    public static NumberOfPlayersSelectionMessage createMessage(String username, int numOfPlayers){
        return new NumberOfPlayersSelectionMessage(username, numOfPlayers);

    }

    @Override
    protected ClientState executeReduce(ClientState oldClientState, Message m){
        ClientState state = null;
        PickNumberOfPlayersMessage pickNumberOfPlayersMessage = (PickNumberOfPlayersMessage) m;

        try {
            state = (ClientState) oldClientState.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }

        state.setCurrentPhase(ClientPhasesEnum.PICK_PLAYERS);


        return state;
    }

}
