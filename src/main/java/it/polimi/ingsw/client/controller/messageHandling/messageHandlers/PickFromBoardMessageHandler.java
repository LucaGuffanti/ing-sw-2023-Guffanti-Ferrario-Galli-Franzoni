package it.polimi.ingsw.client.controller.messageHandling.messageHandlers;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.client.controller.ClientPhasesEnum;
import it.polimi.ingsw.client.controller.messageHandling.Creator;
import it.polimi.ingsw.client.controller.messageHandling.Reducer;
import it.polimi.ingsw.client.controller.messageHandling.MessageHandlersUtils;
import it.polimi.ingsw.client.controller.stateController.ClientState;
import it.polimi.ingsw.client.view.gui.Gui;
import it.polimi.ingsw.network.messages.*;
import it.polimi.ingsw.server.model.cells.Coordinates;

import java.util.List;

/**
 * Handles the creation and the reception of messages used in the
 * picking from board phase during the user's turn.
 *
 * @see PickFromBoardMessage // From client to server
 * @see PickFromBoardResultMessage // From server to client
 * @author Daniele Ferrario
 */
public class PickFromBoardMessageHandler extends Reducer implements Creator {


    public static PickFromBoardMessage createMessage(String username, List<Coordinates> cardsCoordinates){
        return new PickFromBoardMessage(username, cardsCoordinates);
    }

    /**
     * This method sets the phase to SELECT_COLUMN if the pick from the board was successful.
     * Otherwise, an error message is shown.
     * @param oldClientState The old state
     * @param m The received message
     * @return the new state of the client
     */
    @Override
    public ClientState reduce(ClientState oldClientState, Message m){
        ClientState state = null;
        PickFromBoardResultMessage resultMessage = (PickFromBoardResultMessage) m;

        try {
            state = (ClientState) oldClientState.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }

        if(MessageHandlersUtils.isSuccessful(resultMessage)){
            state.setCurrentPhase(ClientPhasesEnum.SELECT_COLUMN);
        }else {
            // See the error message
            String previousErrorBody = state.getServerErrorMessage();
            if (previousErrorBody != null && previousErrorBody.equals(resultMessage.getDescription())) {
                if (Gui.instance != null) {
                    Gui.instance.printErrorMessage(resultMessage.getDescription());
                } else {
                    ClientManager.getInstance().getUserInterface().printErrorMessage(resultMessage.getDescription());
                }
            } else {
                state.setServerErrorMessage(resultMessage.getDescription());
            }
        }
        return state;
    }

}
