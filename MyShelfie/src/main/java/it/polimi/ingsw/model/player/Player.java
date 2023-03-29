package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.cards.ObjectCard;
import it.polimi.ingsw.model.cards.PersonalGoalCard;
import it.polimi.ingsw.model.cards.PointCard;

import java.util.List;

/**
 * This class contains main player info and methods which manipulate points and shelf.
 * @see Shelf
 * @see PlayerAchievements
 * @see PersonalGoalCard
 */
public class Player {
    private String nickname;
    private boolean isActive;
    private Shelf shelf;
    private PlayerAchievements achievements;
    private PersonalGoalCard goal;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Shelf getShelf() {
        return shelf;
    }

    public void setShelf(Shelf shelf) {
        this.shelf = shelf;
    }

    public PlayerAchievements getAchievements() {
        return achievements;
    }

    public void setAchievements(PlayerAchievements achievements) {
        this.achievements = achievements;
    }

    public PersonalGoalCard getGoal() {
        return goal;
    }

    public void setGoal(PersonalGoalCard goal) {
        this.goal = goal;
    }

    public Player(String nickname) {
        this.nickname = nickname;
        isActive = verifyActive();
        shelf = new Shelf();
    }

    /**
     * This method verifies if a player is connected to the game.
     * @return the player status
     */
    public boolean verifyActive() {
        // verify the player's connection
        return isActive;
    }

    /**
     * This method adds cards which a player picks on his turn to the player's shelf.
     * @param column column where a player wants to insert his object cards into the shelf
     * @param cards the cards you pick in your turn
     */
    public boolean addCardsToShelf(int column, List<ObjectCard> cards) {
        boolean success = shelf.addCardsToColumn(column, cards);
        return success;
    }
}
