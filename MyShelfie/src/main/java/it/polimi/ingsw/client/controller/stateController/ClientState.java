package it.polimi.ingsw.client.controller.stateController;

import it.polimi.ingsw.client.controller.ClientPhasesEnum;
import it.polimi.ingsw.client.view.UserInterface;
import it.polimi.ingsw.client.controller.chat.ChatMessage;
import it.polimi.ingsw.model.cards.ObjectTypeEnum;
import it.polimi.ingsw.model.cards.goalCards.SimplifiedCommonGoalCard;
import it.polimi.ingsw.model.player.Player;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A class which represents a state of the client.
 * A ClientState object should be updated ONLY with reducers methods
 * supplied by the StateContainer.
 * @see StateContainer
 * @author Daniele Ferrario
 */
public class ClientState implements Cloneable {
    private String username;
    private ClientPhasesEnum currentPhase;
    private UserInterface userInterface;
    private Player activePlayer;
    private List<Player> orderedPlayers;
    private boolean hasCompletedFirstCommonGoal;
    private boolean hasCompletedSecondCommonGoal;
    private List<ChatMessage> chatHistory;
    private Map<Player, Integer> scores; // @TODO: is player list redundant?

    private ObjectTypeEnum[][] board;
    private ObjectTypeEnum[][] shelf;

    private int personalGoalCardId;

    private Set<SimplifiedCommonGoalCard> commonGoalCards;

    public String getUser() {
        return username;
    }

    public void setUser(Player user) {
        this.username = username;
    }

    public ClientPhasesEnum getCurrentPhase() {
        return currentPhase;
    }

    public void setCurrentPhase(ClientPhasesEnum currentPhase) {
        this.currentPhase = currentPhase;
    }

    public UserInterface getUserInterface() {
        return userInterface;
    }

    public void setUserInterface(UserInterface userInterface) {
        this.userInterface = userInterface;
    }

    public Player getActivePlayer() {
        return activePlayer;
    }

    public void setActivePlayer(Player activePlayer) {
        this.activePlayer = activePlayer;
    }

    public List<Player> getOrderedPlayers() {
        return orderedPlayers;
    }

    public void setOrderedPlayers(List<Player> orderedPlayers) {
        this.orderedPlayers = orderedPlayers;
    }

    public boolean isHasCompletedFirstCommonGoal() {
        return hasCompletedFirstCommonGoal;
    }

    public void setHasCompletedFirstCommonGoal(boolean hasCompletedFirstCommonGoal) {
        this.hasCompletedFirstCommonGoal = hasCompletedFirstCommonGoal;
    }

    public boolean isHasCompletedSecondCommonGoal() {
        return hasCompletedSecondCommonGoal;
    }

    public void setHasCompletedSecondCommonGoal(boolean hasCompletedSecondCommonGoal) {
        this.hasCompletedSecondCommonGoal = hasCompletedSecondCommonGoal;
    }

    public List<ChatMessage> getChatHistory() {
        return chatHistory;
    }

    public void setChatHistory(List<ChatMessage> chatHistory) {
        this.chatHistory = chatHistory;
    }

    public Map<Player, Integer> getScores() {
        return scores;
    }

    public void setScores(Map<Player, Integer> scores) {
        this.scores = scores;
    }

    public ObjectTypeEnum[][] getBoard() {
        return board;
    }

    public void setBoard(ObjectTypeEnum[][] board) {
        this.board = board;
    }

    public ObjectTypeEnum[][] getShelf() {
        return shelf;
    }

    public void setShelf(ObjectTypeEnum[][] shelf) {
        this.shelf = shelf;
    }

    public int getPersonalGoalCardId() {
        return personalGoalCardId;
    }

    public void setPersonalGoalCardId(int personalGoalCardId) {
        this.personalGoalCardId = personalGoalCardId;
    }

    public Set<SimplifiedCommonGoalCard> getCommonGoalCards() {
        return commonGoalCards;
    }

    public void setCommonGoalCards(Set<SimplifiedCommonGoalCard> commonGoalCards) {
        this.commonGoalCards = commonGoalCards;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    // @TODO: TO ADD EVENTUAL OTHERS PROPS FOR THE VIEW
}
