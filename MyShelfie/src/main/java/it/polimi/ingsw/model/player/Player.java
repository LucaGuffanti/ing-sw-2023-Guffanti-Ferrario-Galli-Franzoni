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

    public Player(Shelf shelf, String nickname) {
        this.nickname = nickname;
        isActive = verifyActive();
        this.shelf = shelf;
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
     * This method adds points to the Set of a player's PointCard.
     * @param pointCard the point card earned
     */
    public void addPoints(PointCard pointCard) {
        achievements.getPointCardsEarned().add(pointCard);
    }

    /**
     * This method updates achievements of the player.
     */
    public void updateAchievements() {
        // after a pattern discovered that you have achieved something, this turn to true that goal
    }

    /**
     * This method adds cards which a player picks on his turn to the player's shelf.
     * @param shelf the player's shelf
     * @param cards the cards you pick in your turn
     */
    public void addCardsToShelf(Shelf shelf, List<ObjectCard> cards) {
        // check where you can put the list of cards on your shelf and put them there
    }
}
