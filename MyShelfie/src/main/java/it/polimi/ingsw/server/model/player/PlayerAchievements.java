package it.polimi.ingsw.server.model.player;

import java.util.Set;
import it.polimi.ingsw.server.model.cards.PointCard;

/**
 * This class contains a player's achievements
 * @author Marco Galli
 * @see PointCard
 */
public class PlayerAchievements {
    private boolean completedFirstCommonGoal;
    private boolean completedSecondCommonGoal;
    private Set<PointCard> pointCardsEarned;
    private boolean firstToFinish;

    private boolean completedShelf;

    public PlayerAchievements() {
        completedFirstCommonGoal = false;
        completedSecondCommonGoal = false;
        this.pointCardsEarned = pointCardsEarned;
        firstToFinish = false;
        completedShelf = false;
    }

    public boolean isCompletedShelf() {
        return completedShelf;
    }

    public void setCompletedShelf(boolean completedShelf) {
        this.completedShelf = completedShelf;
    }

    public boolean isCompletedFirstCommonGoal() {
        return completedFirstCommonGoal;
    }

    public void setCompletedFirstCommonGoal(boolean completedFirstCommonGoal) {
        this.completedFirstCommonGoal = completedFirstCommonGoal;
    }

    public boolean isCompletedSecondCommonGoal() {
        return completedSecondCommonGoal;
    }

    public void setCompletedSecondCommonGoal(boolean completedSecondCommonGoal) {
        this.completedSecondCommonGoal = completedSecondCommonGoal;
    }

    public Set<PointCard> getPointCardsEarned() {
        return pointCardsEarned;
    }

    public void setPointCardsEarned(Set<PointCard> pointCardsEarned) {
        this.pointCardsEarned = pointCardsEarned;
    }

    public boolean isFirstToFinish() {
        return firstToFinish;
    }

    public void setFirstToFinish(boolean firstToFinish) {
        this.firstToFinish = firstToFinish;
    }
}
