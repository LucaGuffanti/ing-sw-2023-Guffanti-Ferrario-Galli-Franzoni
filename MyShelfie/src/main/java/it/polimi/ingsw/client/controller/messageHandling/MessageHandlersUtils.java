package it.polimi.ingsw.client.controller.messageHandling;

import it.polimi.ingsw.network.messages.MessageWithResult;
import it.polimi.ingsw.network.messages.enums.ResponseResultType;

public class MessageHandlersUtils {

    public static boolean isSuccessful(MessageWithResult m) {
        return m.getResultType().equals(ResponseResultType.SUCCESS);
    }
}