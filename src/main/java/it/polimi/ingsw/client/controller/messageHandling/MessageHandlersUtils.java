package it.polimi.ingsw.client.controller.messageHandling;

import it.polimi.ingsw.network.messages.MessageWithResult;
import it.polimi.ingsw.network.messages.enums.ResponseResultType;

/**
 * Check if the result to a requested action message is "SUCCESS"
 * @author Daniele Ferrario
 */
public class MessageHandlersUtils {

    /**
     *
     * @param m a message to be checked
     * @return whether the message payload contains a positive result
     */
    public static boolean isSuccessful(MessageWithResult m) {
        return m.getResultType().equals(ResponseResultType.SUCCESS);
    }
}
