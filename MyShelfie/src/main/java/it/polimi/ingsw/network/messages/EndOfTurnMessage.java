package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.client.controller.messageHandling.messageHandlers.BeginningOfTurnMessageHandler;
import it.polimi.ingsw.client.controller.messageHandling.messageHandlers.EndOfTurnMessageHandler;
import it.polimi.ingsw.client.controller.messageHandling.messageHandlers.MessagesHandler;
import it.polimi.ingsw.server.model.cards.ObjectTypeEnum;
import it.polimi.ingsw.server.model.cards.goalCards.SimplifiedCommonGoalCard;
import it.polimi.ingsw.network.messages.enums.MessageType;

import java.util.ArrayList;

/**
 * This message signals the end of a turn and contains information regarding the recently finished
 * turn.
 * <ol>
 *     <li>
 *         The updated board
 *     </li>
 *     <li>
 *         The Shelf of the player who was active, properly updated
 *     </li>
 *     <li>
 *         The two common goal cards, for which the point card stack may vary.
 *     </li>
 *     <li>
 *         The first and second common goal card
 *     </li>
 *     <li>
 *         Boolean flags that mark whether the user has completed the common goals
 *     </li>
 *     <li>
 *         Boolean flag that marks whether the user has completed the shelf
 *     </li>
 * </ol>
 *
 * This message is sent to every connected client.
 * @author Luca Guffanti
 */
public class EndOfTurnMessage extends Message{
    private final ObjectTypeEnum[][] currentBoard;
    private final ObjectTypeEnum[][] activePlayerShelf;
    private final ArrayList<SimplifiedCommonGoalCard> clientCommonGoalCards;
    private final boolean completedFirstCommonGoal;
    private final boolean completedSecondCommonGoal;
    private final boolean completedShelf;
    private final String activePlayer;

    public EndOfTurnMessage(String senderUsername,
                            ObjectTypeEnum[][] currentBoard,
                            ObjectTypeEnum[][] activePlayerShelf,
                            ArrayList<SimplifiedCommonGoalCard> clientCommonGoalCards,
                            boolean completedFirstCommonGoal,
                            boolean completedSecondCommonGoal,
                            boolean completedShelf,
                            String activePlayer) {
        super(MessageType.END_OF_TURN, senderUsername);
        this.currentBoard = currentBoard;
        this.activePlayerShelf = activePlayerShelf;
        this.clientCommonGoalCards = clientCommonGoalCards;
        this.completedFirstCommonGoal = completedFirstCommonGoal;
        this.completedSecondCommonGoal = completedSecondCommonGoal;
        this.completedShelf = completedShelf;
        this.activePlayer = activePlayer;
    }

    public EndOfTurnMessage(String senderUsername,
                            String description,
                            ObjectTypeEnum[][] currentBoard,
                            ObjectTypeEnum[][] activePlayerShelf,
                            ArrayList<SimplifiedCommonGoalCard> clientCommonGoalCards,
                            boolean completedFirstCommonGoal,
                            boolean completedSecondCommonGoal,
                            boolean completedShelf,
                            String activePlayer) {
        super(MessageType.END_OF_TURN, senderUsername, description);
        this.currentBoard = currentBoard;
        this.activePlayerShelf = activePlayerShelf;
        this.clientCommonGoalCards = clientCommonGoalCards;
        this.completedFirstCommonGoal = completedFirstCommonGoal;
        this.completedSecondCommonGoal = completedSecondCommonGoal;
        this.completedShelf = completedShelf;
        this.activePlayer = activePlayer;
    }
    @Override
    public MessagesHandler getHandlerForClient() {
        return new EndOfTurnMessageHandler();
    }
    public ObjectTypeEnum[][] getCurrentBoard() {
        return currentBoard;
    }

    public ObjectTypeEnum[][] getActivePlayerShelf() {
        return activePlayerShelf;
    }

    public ArrayList<SimplifiedCommonGoalCard> getClientCommonGoalCards() {
        return clientCommonGoalCards;
    }

    public boolean isCompletedFirstCommonGoal() {
        return completedFirstCommonGoal;
    }

    public boolean isCompletedSecondCommonGoal() {
        return completedSecondCommonGoal;
    }

    public boolean isCompletedShelf() {
        return completedShelf;
    }

    public String getActivePlayer() {
        return activePlayer;
    }
}
