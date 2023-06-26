package it.polimi.ingsw.server.model.player;

import it.polimi.ingsw.server.model.cards.ObjectCard;
import it.polimi.ingsw.server.model.cards.goalCards.PersonalGoalCard;
import it.polimi.ingsw.server.model.utils.exceptions.NoSpaceEnoughInShelfColumnException;

import java.util.List;

/**
 * This class contains main player info and methods which manipulate points and shelf.
 * @author Marco Galli, Luca Guffanti, Daniele Ferrario
 * @see Shelf
 * @see PlayerAchievements
 * @see PersonalGoalCard
 */
public class Player {
    /**
     * The nickname of the player
     */
    private final String nickname;

    /**
     * The status of player, active or not active
     */
    private boolean isActive;

    /**
     * The player shelf
     */
    private Shelf shelf;

    /**
     * The player achievements
     */
    private PlayerAchievements achievements;

    /**
     * The personal goal card of the player
     */
    private PersonalGoalCard goal;

    public String getNickname() {
        return nickname;
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

    public PersonalGoalCard getGoal() {
        return goal;
    }

    public void setGoal(PersonalGoalCard goal) {
        this.goal = goal;
    }

    public void setAchievements(PlayerAchievements achievements) {
        this.achievements = achievements;
    }

    public Player(String nickname) {
        this.nickname = nickname;
        isActive = true;
        shelf = new Shelf();
        achievements = new PlayerAchievements();
    }

    public Player(Shelf shelf, String nickname) {
        this.nickname = nickname;
        isActive = true;
        this.shelf = shelf;
        achievements = new PlayerAchievements();
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
     * @param cards the cards you pick in your turn
     * @param column column where a player wants to insert his object cards into the shelf
     * @return whether a card has been successfully added
     */
    public boolean addCardsToShelf(List<ObjectCard> cards, int column) {

        try {
            shelf.checkIfEnoughSpaceInColumn(cards.size(),column);
        }catch (NoSpaceEnoughInShelfColumnException ex){
            if (shelf.isFull()) {
                achievements.setCompletedShelf(true);
            }
            return false;
        }

        shelf.addCardsToColumn(cards, column);
        if (shelf.isFull()) {
            achievements.setCompletedShelf(true);
        }

        return true;
    }
}
