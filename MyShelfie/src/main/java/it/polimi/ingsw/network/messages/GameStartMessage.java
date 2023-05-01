package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.client.controller.messageHandling.messageHandlers.GameStartMessageHandler;
import it.polimi.ingsw.client.controller.messageHandling.messageHandlers.MessagesHandler;
import it.polimi.ingsw.server.model.cards.ObjectTypeEnum;
import it.polimi.ingsw.server.model.cards.goalCards.SimplifiedCommonGoalCard;
import it.polimi.ingsw.network.messages.enums.MessageType;

import java.util.ArrayList;
import java.util.List;

/**
 * This message is broadcast to every player and contains information regarding the various game
 * elements: the representation of the clientBoard, the shelves, the common and personal goal cards,
 * the ordered list of players.
 * @author Luca Guffanti
 */
public class GameStartMessage extends Message{

    private final ObjectTypeEnum[][] clientBoard;

    private final List<ObjectTypeEnum[][]> shelves;

    /**
     * The id of the personal goal in position <i>i</i> is the id of the
     * card associated to the username in position <i>i</i>
     */
    private final ArrayList<String> clientPersonalGoals;
    private final ArrayList<String> orderedPlayers;
    private final ArrayList<SimplifiedCommonGoalCard> clientCommonGoalCards; // @TODO: use an HashMap<username, simplifiedGoalCard>

    public GameStartMessage(String senderUsername,
                            ObjectTypeEnum[][] board,
                            List<ObjectTypeEnum[][]> shelves,
                            ArrayList<String> clientPersonalGoals,
                            ArrayList<String> orderedPlayers,
                            ArrayList<SimplifiedCommonGoalCard> clientCommonGoalCards) {
        super(MessageType.GAME_START, senderUsername);
        this.clientBoard = board;
        this.shelves = shelves;
        this.clientPersonalGoals = clientPersonalGoals;
        this.orderedPlayers = orderedPlayers;
        this.clientCommonGoalCards = clientCommonGoalCards;
    }

    public GameStartMessage(String senderUsername,
                            String description,
                            ObjectTypeEnum[][] board,
                            List<ObjectTypeEnum[][]> shelves,
                            ArrayList<String> clientPersonalGoals,
                            ArrayList<String> orderedPlayers,
                            ArrayList<SimplifiedCommonGoalCard> clientCommonGoalCards) {
        super(MessageType.GAME_START, senderUsername, description);
        this.clientBoard = board;
        this.shelves = shelves;
        this.clientPersonalGoals = clientPersonalGoals;
        this.orderedPlayers = orderedPlayers;
        this.clientCommonGoalCards = clientCommonGoalCards;
    }

    @Override
    public MessagesHandler getHandlerForClient() {
        return new GameStartMessageHandler();
    }

    public ObjectTypeEnum[][] getClientBoard() {
        return clientBoard;
    }

    public List<ObjectTypeEnum[][]> getShelves() {
        return shelves;
    }

    public ArrayList<String> getClientPersonalGoals() {
        return clientPersonalGoals;
    }

    public ArrayList<String> getOrderedPlayers() {
        return orderedPlayers;
    }

    public ArrayList<SimplifiedCommonGoalCard> getClientCommonGoalCards() {
        return clientCommonGoalCards;
    }
}
