package it.polimi.ingsw.model;

import it.polimi.ingsw.model.cards.CommonGoalCard;
import it.polimi.ingsw.model.player.Player;

import java.util.ArrayList;

public class GameInfo {
    private Player admin;
    private int nPlayers;
    private Player winner;
    private int gameID;
    private ArrayList<CommonGoalCard> selectedCommonGoals;
    private GameStatusEnum gameStatus;

    // TODO implement methods
}
