package it.polimi.ingsw.client.controller.messageHandling.messageHandlers;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.client.controller.ClientPhasesEnum;
import it.polimi.ingsw.client.controller.messageHandling.Creator;
import it.polimi.ingsw.client.controller.messageHandling.Reducer;
import it.polimi.ingsw.client.controller.messageHandling.MessageHandlersUtils;
import it.polimi.ingsw.client.controller.stateController.ClientState;
import it.polimi.ingsw.client.view.gui.Gui;
import it.polimi.ingsw.network.messages.*;

import java.util.ArrayList;


/**
 * Handles the creation and the reception of the messages relative to the
 * login requests and responses.
 *
 * @see LoginRequestMessage
 * @see LoginResponseMessage
 * @author Daniele Ferrario
 */
public class LoginMessageHandler extends Reducer implements Creator {

    public static LoginRequestMessage createMessage(String username){
        return new LoginRequestMessage(username);

    }

    /**
     * This method handles the receiving of the result of a login
     * @param oldClientState The old state
     * @param m The received message
     * @return the new state of the client
     */
    @Override
    public ClientState reduce(ClientState oldClientState, Message m){
        ClientState state;
        LoginResponseMessage loginMessage = (LoginResponseMessage) m;

        try {
            state = (ClientState) oldClientState.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }

        if(MessageHandlersUtils.isSuccessful(loginMessage)){
            //update the name of the client with the username if the login is successful
            state.setUsername(loginMessage.getRecipient());
            state.setCurrentPhase(ClientPhasesEnum.NOT_JOINED);
            state.setChatHistory(new ArrayList<>());
        }else{
            String previousErrorBody = state.getServerErrorMessage();
            if (previousErrorBody != null && previousErrorBody.equals(loginMessage.getDescription())) {
                if (Gui.instance != null) {
                    Gui.instance.printErrorMessage(loginMessage.getDescription());
                } else {
                    ClientManager.getInstance().getUserInterface().printErrorMessage(loginMessage.getDescription());
                }
            } else {
                state.setServerErrorMessage(loginMessage.getDescription());
            }
        }
        state.setServerLastMessage(loginMessage.getDescription());
        return state;
    }

}
