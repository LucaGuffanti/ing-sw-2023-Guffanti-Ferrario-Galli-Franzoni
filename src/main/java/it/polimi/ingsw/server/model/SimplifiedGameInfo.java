package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.cards.goalCards.SimplifiedCommonGoalCard;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * A SimplifiedGameInfo instance contains a copy of the game info object that can be found in game.
 *
 * In a simplifiedGameInfo there's the number of player, the name of the admin, the id of the game,
 * a list of simplifiedCommonGoals and a list of ids of personal goals
 * @author Luca Guffanti
 * @see SimplifiedCommonGoalCard
 */
public class SimplifiedGameInfo implements Serializable {
    /**
     * The nickname of the player who created the lobby
     */
    private String admin;

    /**
     * The number of the players
     */
    private int nPlayers;

    /**
     * The username of the winner player
     */
    private String winner;

    /**
     * The nickname of the fist player who completes the shelf
     */
    private String firstToCompleteTheShelf;

    /**
     * The ID of the game
     */
    private int gameID;

    /**
     * The list of simplified common goal cards
     */
    private ArrayList<SimplifiedCommonGoalCard> commonGoals = new ArrayList<>();

    /**
     * The list of personal goal cards ID
     */
    private ArrayList<String> personalGoals = new ArrayList<>();

    /**
     *
     * @param admin admin of the game
     * @param nPlayers number of players
     * @param winner the winner (useless but added for completeness)
     * @param gameID id of the game
     * @param commonGoals common goal list
     * @param personalGoals personal goal list
     * @param firstToCompleteTheShelf the first player who's completed the shelf
     */
    public SimplifiedGameInfo(String admin,
                              int nPlayers,
                              String winner,
                              int gameID,
                              ArrayList<SimplifiedCommonGoalCard> commonGoals,
                              ArrayList<String> personalGoals,
                              String firstToCompleteTheShelf) {
        this.admin = admin;
        this.nPlayers = nPlayers;
        this.winner = winner;
        this.gameID = gameID;
        this.commonGoals = commonGoals;
        this.personalGoals = personalGoals;
        this.firstToCompleteTheShelf = firstToCompleteTheShelf;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public int getnPlayers() {
        return nPlayers;
    }

    public void setnPlayers(int nPlayers) {
        this.nPlayers = nPlayers;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public int getGameID() {
        return gameID;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    public ArrayList<SimplifiedCommonGoalCard> getCommonGoals() {
        return commonGoals;
    }

    public ArrayList<String> getPersonalGoals() {
        return personalGoals;
    }

    public String getFirstToCompleteTheShelf() {
        return firstToCompleteTheShelf;
    }

    public void setCommonGoals(ArrayList<SimplifiedCommonGoalCard> commonGoals) {
        this.commonGoals = commonGoals;
    }

    public void setPersonalGoals(ArrayList<String> personalGoals) {
        this.personalGoals = personalGoals;
    }

    public void setFirstToCompleteTheShelf(String firstToCompleteTheShelf) {
        this.firstToCompleteTheShelf = firstToCompleteTheShelf;
    }
}
