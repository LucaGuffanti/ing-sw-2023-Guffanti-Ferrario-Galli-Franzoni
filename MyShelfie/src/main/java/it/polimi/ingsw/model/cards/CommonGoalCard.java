package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.player.Player;

import java.util.ArrayList;
import java.util.List;

public class CommonGoalCard {
    private List<PointCard>  pointsCard = new ArrayList<>();

    public CommonGoalCard(List<PointCard> pointsCard) {
        this.pointsCard = pointsCard;
    }

    public PointCard calculatePoints(Player player){
        return (PointCard) pointsCard;
    }
}
