package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.client.controller.messageHandling.messageHandlers.MessagesHandler;
import it.polimi.ingsw.client.controller.messageHandling.messageHandlers.PlayersNumberSelectionMessageHandler;
import it.polimi.ingsw.network.messages.enums.MessageType;

/**
 * This message is sent by the client as a response to the PickNumberOfPlayers message. It contains
 * the number of players of the game that the client is creating.
 * @author Luca Guffanti
 */
public class NumberOfPlayersSelectionMessage extends Message{

    private final int numOfPlayers;
    public NumberOfPlayersSelectionMessage(String senderUsername, int numOfPlayers) {
        super(MessageType.NUMBER_OF_PLAYERS_SELECTION, senderUsername);
        this.numOfPlayers = numOfPlayers;
    }

    public NumberOfPlayersSelectionMessage(String senderUsername, String description, int numOfPlayers) {
        super(MessageType.NUMBER_OF_PLAYERS_SELECTION, senderUsername, description);
        this.numOfPlayers = numOfPlayers;
    }

    @Override
    public MessagesHandler getHandlerForClient() {
        return new PlayersNumberSelectionMessageHandler();
    }

    public int getNumOfPlayers() {
        return numOfPlayers;
    }
}
