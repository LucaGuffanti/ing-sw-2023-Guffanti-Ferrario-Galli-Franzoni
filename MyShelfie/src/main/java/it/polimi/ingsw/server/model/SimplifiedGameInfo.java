package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.cards.goalCards.CommonGoalCard;
import it.polimi.ingsw.server.model.cards.goalCards.PersonalGoalCard;
import it.polimi.ingsw.server.model.cards.goalCards.SimplifiedCommonGoalCard;
import it.polimi.ingsw.server.model.player.Player;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * A SimplifiedGameInfo instance contains a copy of the game info object that can be found in game.
 *
 * In a simplifiedGameInfo there's the number of player, the name of the admin, the id of the game,
 * a list of simplifiedCommonGoals and a list of ids of personal goals
 * @author Luca Guffanti
 */
public class SimplifiedGameInfo implements Serializable {
    private String admin;
    private int nPlayers;
    private String winner;
    private int gameID;
    private ArrayList<SimplifiedCommonGoalCard> commonGoals = new ArrayList<>();
    private ArrayList<String> personalGoals = new ArrayList<>();

    /**
     *
     * @param admin admin of the game
     * @param nPlayers number of players
     * @param winner the winner (useless but added for completeness)
     * @param gameID id of the game
     * @param commonGoals common goal list
     * @param personalGoals personal goal list
     */
    public SimplifiedGameInfo(String admin,
                              int nPlayers,
                              String winner,
                              int gameID,
                              ArrayList<SimplifiedCommonGoalCard> commonGoals,
                              ArrayList<String> personalGoals) {
        this.admin = admin;
        this.nPlayers = nPlayers;
        this.winner = winner;
        this.gameID = gameID;
        this.commonGoals = commonGoals;
        this.personalGoals = personalGoals;
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

    public void setCommonGoals(ArrayList<SimplifiedCommonGoalCard> commonGoals) {
        this.commonGoals = commonGoals;
    }

    public ArrayList<String> getPersonalGoals() {
        return personalGoals;
    }

    public void setPersonalGoals(ArrayList<String> personalGoals) {
        this.personalGoals = personalGoals;
    }
}
