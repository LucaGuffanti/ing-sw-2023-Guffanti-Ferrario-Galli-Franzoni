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
 */
public class GameInfo {

    // TODO port admin from player to string: the nickname of the player
    private Player admin;
    private int nPlayers;
    private Player winner;
    private int gameID;
    private ArrayList<CommonGoalCard> commonGoals = new ArrayList<>();
    private ArrayList<PersonalGoalCard> personalGoals = new ArrayList<>();


    public Player getAdmin() {
        return admin;
    }

    public void setAdmin(Player admin) {
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

    public int getGameID() {
        return gameID;
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

    public void setCommonGoals(ArrayList<CommonGoalCard> commonGoals) {
        this.commonGoals = commonGoals;
    }

    public ArrayList<PersonalGoalCard> getPersonalGoals() {
        return personalGoals;
    }

    public void setPersonalGoals(ArrayList<PersonalGoalCard> personalGoals) {
        this.personalGoals = personalGoals;
    }
}
