package it.polimi.ingsw.server.model;

import java.util.HashMap;

/**
 * This class contains the points made by every player and the winner of the game
 * @author Luca Guffanti
 */
public class GameCheckout {
    private String winner;
    private HashMap<String, Integer> nickToPoints;

    public GameCheckout(String winner, HashMap<String, Integer> nickToPoints) {
        this.winner = winner;
        this.nickToPoints = nickToPoints;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public HashMap<String, Integer> getNickToPoints() {
        return nickToPoints;
    }

    public void setNickToPoints(HashMap<String, Integer> nickToPoints) {
        this.nickToPoints = nickToPoints;
    }
}
