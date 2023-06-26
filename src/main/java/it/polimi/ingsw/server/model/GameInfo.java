package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.cards.goalCards.CommonGoalCard;
import it.polimi.ingsw.server.model.cards.goalCards.PersonalGoalCard;
import it.polimi.ingsw.server.model.player.Player;

import java.util.ArrayList;

/**
 * This class contains useful information about the game it can be accessed from.
 * <table>
 *     <tr><td><b>admin</b></td><td> is the player that starts the game</td></tr>
 *     <tr><td><b>nPlayers</b></td><td> is the number of players set by "admin" when creating the game</td></tr>
 *     <tr><td><b>winner</b></td><td> is the player who won the game</td></tr>
 *     <tr><td><b>gameID</b></td><td> is a unique identifier given to the game by the server</td></tr>
 *     <tr><td><b>commonGoals</b></td><td> is the list of commonGoals extracted at the beginning of the game</td></tr>
 *     <tr><td><b>gameStatus</b></td><td> is the current status of the game</td></tr>
 * </table>
 * @author Luca Guffanti
 * @see Player
 * @see CommonGoalCard
 * @see PersonalGoalCard
 */
public class GameInfo {
    /**
     * The username of the player who created the lobby
     */
    private String admin;

    /**
     * The number of players
     */
    private int nPlayers;

    /**
     * The winner of the game
     */
    private Player winner;

    /**
     * The nickname of the first player who completes the shelf
     */
    private String firstToCompleteTheShelf;

    /**
     * The game ID
     */
    private int gameID;

    /**
     * The common goal cards
     */
    private ArrayList<CommonGoalCard> commonGoals = new ArrayList<>();

    /**
     * The personal goal cards
     */
    private ArrayList<PersonalGoalCard> personalGoals = new ArrayList<>();


    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public int getNPlayers() {
        return nPlayers;
    }

    public void setNPlayers(int nPlayers) {
        this.nPlayers = nPlayers;
    }

    public Player getWinner() {
        return winner;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }

    public void setCommonGoals(ArrayList<CommonGoalCard> commonGoals) {
        this.commonGoals = commonGoals;
    }

    public int getGameID() {
        return gameID;
    }

    public String getFirstToCompleteTheShelf() {
        return firstToCompleteTheShelf;
    }

    public void setFirstToCompleteTheShelf(String firstToCompleteTheShelf) {
        this.firstToCompleteTheShelf = firstToCompleteTheShelf;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    public ArrayList<CommonGoalCard> getSelectedCommonGoals() {
        return commonGoals;
    }

    public void setSelectedCommonGoals(ArrayList<CommonGoalCard> selectedCommonGoals) {
        this.commonGoals = selectedCommonGoals;
    }

    public ArrayList<CommonGoalCard> getCommonGoals() {
        return commonGoals;
    }

    public ArrayList<PersonalGoalCard> getPersonalGoals() {
        return personalGoals;
    }

    public void setPersonalGoals(ArrayList<PersonalGoalCard> personalGoals) {
        this.personalGoals = personalGoals;
    }
}
