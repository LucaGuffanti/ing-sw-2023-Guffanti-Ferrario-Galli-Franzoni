package it.polimi.ingsw.server.model;

import java.util.HashMap;

/**
 * This class contains the points made by every player and the winner of the game
 * @author Luca Guffanti
 */
public class GameCheckout {
    /**
     * The username of the winner player
     */
    private String winner;

    /**
     * The points mapped to the players
     */
    private HashMap<String, Integer> nickToPoints;

    public GameCheckout(String winner, HashMap<String, Integer> nickToPoints) {
        this.winner = winner;
        this.nickToPoints = nickToPoints;
    }

    /**
     * This method returns the winner's nickname of the game
     * @return the winner's nickname
     */
    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public HashMap<String, Integer> getNickToPoints() {
        return nickToPoints;
    }
}
