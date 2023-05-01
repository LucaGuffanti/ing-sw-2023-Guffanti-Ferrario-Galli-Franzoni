package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.client.controller.messageHandling.messageHandlers.JoinGameMessageHandler;
import it.polimi.ingsw.client.controller.messageHandling.messageHandlers.MessagesHandler;
import it.polimi.ingsw.network.messages.enums.MessageType;
import it.polimi.ingsw.network.messages.enums.ResponseResultType;

import java.util.ArrayList;

/**
 * This message is sent to a client after it successfully joins or creates a game(success), or
 * after the access is denied as the game is full(failure).
 * When the access is successful the message contains a list of the currently logged in players.
 * (An new player's join will be notified to the other clients via a NewPlayer message)
 * @author Luca Guffanti
 */
public class AccessResultMessage extends Message implements MessageWithResult{

    private final ResponseResultType resultType;
    private final ArrayList<String> playersUsernames;
    private final String recipient;
    public AccessResultMessage(String senderUsername, ResponseResultType resultType, ArrayList<String> playersUsernames, String recipient) {
        super(MessageType.ACCESS_RESULT, senderUsername);
        this.resultType = resultType;
        this.playersUsernames = playersUsernames;
        this.recipient = recipient;
    }

    public AccessResultMessage(String senderUsername, String description, ResponseResultType resultType, ArrayList<String> playersUsernames, String recipient) {
        super(MessageType.ACCESS_RESULT, senderUsername, description);
        this.resultType = resultType;
        this.playersUsernames = playersUsernames;
        this.recipient = recipient;
    }

    @Override
    public MessagesHandler getHandlerForClient() {
        return new JoinGameMessageHandler();
    }
    public ResponseResultType getResultType() {
        return resultType;
    }

    public ArrayList<String> getPlayersUsernames() {
        return playersUsernames;
    }

    public String getRecipient() {
        return recipient;
    }
}
