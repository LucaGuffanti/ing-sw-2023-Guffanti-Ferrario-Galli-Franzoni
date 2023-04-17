package it.polimi.ingsw.client.controller.messagesHandlers;

import it.polimi.ingsw.network.messages.Message;

public class MessageHandlerSwitch {
    public static MessagesHandler getMessageHandler(Message m){
        // @TODO: IMPLEMENTING MESSAGE TYPE RECOGNITION
        return new LoginHandler(); //EXAMPLE
    }
}
