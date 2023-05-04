package it.polimi.ingsw.controller;

import it.polimi.ingsw.server.controller.GameController;
import it.polimi.ingsw.server.controller.utils.GameObjectConverter;
import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.cards.PointCard;
import it.polimi.ingsw.server.model.cards.PointEnumeration;
import it.polimi.ingsw.server.model.cards.goalCards.SimplifiedCommonGoalCard;
import org.junit.Test;

import java.util.HashMap;

/**
 * @author Luca Guffanti
 * This Object tests the correctness of the conversion from game objects to their simplified description
 */
public class ObjectConverterTest {

    @Test
    public void testCommonGoalConversion() {
        FakeServerNetworkHandler f = new FakeServerNetworkHandler();
        GameController gameController = new GameController(f);

        gameController.createGame("PLAYER1", 3, 0);
        gameController.onPlayerJoin("PLAYER2");
        gameController.onPlayerJoin("PLAYER3");

        Game g = gameController.getGame();
        g.getPlayers().get(0).getAchievements().getPointCardsEarned().put(1, new PointCard(PointEnumeration.SIX_POINTS, 6));
        g.getPlayers().get(1).getAchievements().getPointCardsEarned().put(1, new PointCard(PointEnumeration.FOUR_POINTS, 4));
        g.getPlayers().get(2).getAchievements().getPointCardsEarned().put(1, new PointCard(PointEnumeration.TWO_POINTS, 2));
        g.getPlayers().get(0).getAchievements().getPointCardsEarned().put(2, new PointCard(PointEnumeration.SIX_POINTS, 6));
        g.getPlayers().get(1).getAchievements().getPointCardsEarned().put(2, new PointCard(PointEnumeration.FOUR_POINTS, 4));

        SimplifiedCommonGoalCard s = GameObjectConverter.simplifyCommonGoalIntoCard(g.getGameInfo().getCommonGoals().get(0),g,0 );
        SimplifiedCommonGoalCard s2 = GameObjectConverter.simplifyCommonGoalIntoCard(g.getGameInfo().getCommonGoals().get(1),g,1 );

        for (String nick : s.getNickToEarnedPoints().keySet()) {
            System.out.println(nick+" "+s.getNickToEarnedPoints().get(nick).getPointsGiven());
        }
        System.out.println("===========");
        for (String nick : s2.getNickToEarnedPoints().keySet()) {
            if (s2.getNickToEarnedPoints().get(nick) != null) {
                System.out.println(nick+" "+s2.getNickToEarnedPoints().get(nick).getPointsGiven());
            }
        }
    }
}
