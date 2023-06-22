package it.polimi.ingsw.client.controller.messageHandling.messageHandlers;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.client.controller.ClientPhasesEnum;
import it.polimi.ingsw.client.controller.messageHandling.Creator;
import it.polimi.ingsw.client.controller.messageHandling.Reducer;
import it.polimi.ingsw.client.controller.messageHandling.MessageHandlersUtils;
import it.polimi.ingsw.client.controller.stateController.ClientState;
import it.polimi.ingsw.client.view.cli.Cli;
import it.polimi.ingsw.client.view.gui.Gui;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.SelectColumnMessage;
import it.polimi.ingsw.network.messages.SelectColumnResultMessage;

/**
 * Handles the creation and the reception of messages used in the
 * selection of a column during the user's turn.
 *
 * @see SelectColumnMessage
 * @see SelectColumnResultMessage
 * @author Daniele Ferrario
 */
public class SelectColumnMessageHandler extends Reducer implements Creator {


    public static SelectColumnMessage createMessage(String username, int columnIndex){
        return new SelectColumnMessage(username, columnIndex);

    }

    /**
     * Check if the column selection request has been validated by the server.
     * @param oldClientState
     * @param m
     * @return
     */
    @Override
    public ClientState reduce(ClientState oldClientState, Message m){
        ClientState state = null;
        SelectColumnResultMessage selectColumnResultMessage = (SelectColumnResultMessage) m;

        try {
            state = (ClientState) oldClientState.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }

        if(MessageHandlersUtils.isSuccessful(selectColumnResultMessage)){
            state.setCurrentPhase(ClientPhasesEnum.WAITING_FOR_TURN);
        }else {
            String previousErrorBody = state.getServerErrorMessage();
            if (previousErrorBody != null && previousErrorBody.equals(selectColumnResultMessage.getDescription())) {
                if (Gui.instance != null) {
                    Gui.instance.printErrorMessage(selectColumnResultMessage.getDescription());
                } else {
                    ClientManager.getInstance().getUserInterface().printErrorMessage(selectColumnResultMessage.getDescription());
                }
            } else {
                state.setServerErrorMessage(selectColumnResultMessage.getDescription());
            }
        }


        return state;
    }

}
