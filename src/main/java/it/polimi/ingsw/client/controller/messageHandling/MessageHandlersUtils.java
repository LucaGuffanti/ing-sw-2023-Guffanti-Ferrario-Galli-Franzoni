package it.polimi.ingsw.client.controller.messageHandling;

import it.polimi.ingsw.network.messages.MessageWithResult;
import it.polimi.ingsw.network.messages.enums.ResponseResultType;

/**
 * Check if the result to a requested action message is "SUCCESS"
 * @author Daniele Ferrario
 */
public class MessageHandlersUtils {

    public static boolean isSuccessful(MessageWithResult m) {
        return m.getResultType().equals(ResponseResultType.SUCCESS);
    }
}
