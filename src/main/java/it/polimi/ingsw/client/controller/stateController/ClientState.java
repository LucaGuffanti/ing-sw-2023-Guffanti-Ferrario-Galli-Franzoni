package it.polimi.ingsw.client.controller.stateController;

import it.polimi.ingsw.client.controller.ClientPhasesEnum;
import it.polimi.ingsw.client.view.UserInterface;
import it.polimi.ingsw.network.messages.ChatMessage;
import it.polimi.ingsw.server.model.cards.ObjectTypeEnum;
import it.polimi.ingsw.server.model.cards.goalCards.SimplifiedCommonGoalCard;
import it.polimi.ingsw.server.model.player.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A class which represents a state of the client.
 *
 * @see StateContainer
 * @author Daniele Ferrario
 */
public class ClientState implements Cloneable {

    /**
     * Username of the player
     */
    private String username;
    /**
     * Active user interface ( GUI / CLI )
     */
    private UserInterface userInterface;
    /**
     * Username of the winner
     */
    private String winnerUserName;

    /**
     * The map which associates the players' nicknames to their points.
     */
    private HashMap <String, Integer> nameToPointMap;

    /**
     * Username of the active player
     */
    private String activePlayer;
    /**
     * The list of players' nicknames ordered by their turn.
     */
    private List<String> orderedPlayersNames;
    /**
     * Flag which indicates if the player has completed
     * The first common goal.
     */
    private boolean hasCompletedFirstCommonGoal;

    /**
     * Flag which indicates if the player has completed
     * The second common goal.
     */
    private boolean hasCompletedSecondCommonGoal;

    /**
     * The list containing the chat messages history.
     */
    private List<ChatMessage> chatHistory;

    /**
     * The last chat message that has been sent.
     */
    private ChatMessage lastChatMessage;

    /**
     * The current state of the board.
     */
    private ObjectTypeEnum[][] board;

    /**
     * The active player's shelf.
     */
    private ObjectTypeEnum[][] activePlayerShelf;

    /**
     * Players' shelves.
     */
    private List<ObjectTypeEnum[][]> shelves;

    /**
     * The flag which represents that the player has completed the
     * shelf before everyone else.
     */
    private String firstToCompleteShelf;

    /**
     * The id of the personal goal card assigned to the player.
     */
    private String personalGoalCardId;

    /**
     * The current game common goals.
     */
    private List<SimplifiedCommonGoalCard> commonGoalCards;

    /**
     * The last server error message.
     */
    private String serverErrorMessage;

    /**
     * The current phase of the game.
     */
    private ClientPhasesEnum currentPhase;

    public void addToChatHistory(ChatMessage message){
        chatHistory.add(message);
    }

    public String getServerLastMessage() {
        return serverLastMessage;
    }

    public void setServerLastMessage(String serverLastMessage) {
        this.serverLastMessage = serverLastMessage;
    }
    private String serverLastMessage;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getServerErrorMessage() {
        return serverErrorMessage;
    }

    public String getActivePlayer() {
        return activePlayer;
    }

    public void setActivePlayer(String activePlayer) {
        this.activePlayer = activePlayer;
    }

    public void setServerErrorMessage(String serverErrorMessage) {
        this.serverErrorMessage = serverErrorMessage;
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



    public List<String> getOrderedPlayersNames() {
        return orderedPlayersNames;
    }

    public void setOrderedPlayersNames(List<String> orderedPlayersNames) {
        this.orderedPlayersNames = orderedPlayersNames;
    }

    public ChatMessage getLastChatMessage() {
        return lastChatMessage;
    }

    public void setLastChatMessage(ChatMessage lastChatMessage) {
        this.lastChatMessage = lastChatMessage;
    }

    public String getWinnerUserName() {
        return winnerUserName;
    }

    public void setWinnerUserName(String winnerUserName) {
        this.winnerUserName = winnerUserName;
    }

    public HashMap<String, Integer> getNameToPointMap() {
        return nameToPointMap;
    }

    public void setNameToPointMap(HashMap<String, Integer> nameToPointMap) {
        this.nameToPointMap = nameToPointMap;
    }

    public void addPlayerName(String playerName){
        this.orderedPlayersNames.add(playerName);
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

    public ObjectTypeEnum[][] getBoard() {
        return board;
    }

    public void setBoard(ObjectTypeEnum[][] board) {
        this.board = board;
    }

    public List<ObjectTypeEnum[][]> getShelves() {
        return shelves;
    }

    public void setShelves(List<ObjectTypeEnum[][]> shelves) {
        this.shelves = shelves;
    }

    public void setActivePlayerShelf(ObjectTypeEnum[][] shelf){
        activePlayerShelf = shelf;
        int activePlayerIndex = orderedPlayersNames.indexOf(activePlayer);
        this.shelves.set(activePlayerIndex, shelf);
    }

    public String getPersonalGoalCardId() {
        return personalGoalCardId;
    }

    public void setPersonalGoalCardId(String personalGoalCardId) {
        this.personalGoalCardId = personalGoalCardId;
    }

    public List<SimplifiedCommonGoalCard> getCommonGoalCards() {
        return commonGoalCards;
    }

    public void setCommonGoalCards(List<SimplifiedCommonGoalCard> commonGoalCards) {
        this.commonGoalCards = commonGoalCards;
    }

    public ObjectTypeEnum[][] getActivePlayerShelf() {
        return activePlayerShelf;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public String getFirstToCompleteShelf() {
        return firstToCompleteShelf;
    }

    public void setFirstToCompleteShelf(String firstToCompleteShelf) {
        this.firstToCompleteShelf = firstToCompleteShelf;
    }
}
