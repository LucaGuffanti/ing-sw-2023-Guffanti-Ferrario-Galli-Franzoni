package it.polimi.ingsw.server.model.player;

import it.polimi.ingsw.server.model.cards.ObjectTypeEnum;

import java.io.Serializable;

/**
 * A simplified description of the player.
 * This object contains all the information needed to save the state of a player
 * on the disk. <br>
 *
 * A simplifiedPlayer instance contains a name, a shelf, an object summarizing the achievements
 * and the id of a personalGoalCard.
 * @author Luca Guffanti
 */
public class SimplifiedPlayer implements Serializable {
    /**
     * The player username
     */
    private String name;

    /**
     * The player shelf
     */
    private ObjectTypeEnum[][] shelf;

    /**
     * The player achievements
     */
    private PlayerAchievements achievements;

    /**
     * The personal goal ID
     */
    private String personalGoalId;

    public SimplifiedPlayer(String name, ObjectTypeEnum[][] shelf, PlayerAchievements achievements, String personalGoalId) {
        this.name = name;
        this.shelf = shelf;
        this.achievements = achievements;
        this.personalGoalId = personalGoalId;
    }

    public SimplifiedPlayer(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ObjectTypeEnum[][] getShelf() {
        return shelf;
    }

    public void setShelf(ObjectTypeEnum[][] shelf) {
        this.shelf = shelf;
    }

    public PlayerAchievements getAchievements() {
        return achievements;
    }

    public void setAchievements(PlayerAchievements achievements) {
        this.achievements = achievements;
    }

    public String getPersonalGoalId() {
        return personalGoalId;
    }

    public void setPersonalGoalId(String personalGoalId) {
        this.personalGoalId = personalGoalId;
    }
}
