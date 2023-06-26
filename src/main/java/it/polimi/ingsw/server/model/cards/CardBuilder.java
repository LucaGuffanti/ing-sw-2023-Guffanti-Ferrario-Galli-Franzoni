package it.polimi.ingsw.server.model.cards;

import it.polimi.ingsw.server.model.cards.goalCards.CommonGoalCard;
import it.polimi.ingsw.server.model.utils.JsonFixedPatternGoalCardsParser;
import it.polimi.ingsw.server.model.utils.exceptions.WrongNumberOfPlayersException;
import it.polimi.ingsw.server.model.utils.exceptions.WrongPointCardsValueGivenException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * This class contains static methods that can be used to mass
 * produce different types of cards.
 *
 * @author Luca Guffanti
 */
public class CardBuilder {

    /**
     * This method generates objects card of a given type.
     * @param toGenerate the number of object cards to be generated.
     * @param type the type of object cards to be generated.
     * @return the list of object cards that are generated.
     */
    public static ArrayList<ObjectCard> generateObjectCards(int toGenerate, ObjectTypeEnum type) {
        ArrayList<ObjectCard> list = new ArrayList<>();

        for(int i = 0; i < toGenerate; i++) {
            list.add(new ObjectCard(type));
        }

        return list;
    }

    /**
     * This method generates point cards that will be contained in the common goal cards based
     * on the number of players
     * @param playersNumber the number of players in the current game
     * @return a list of PointCards based on the players number.
     */
    public static ArrayList<PointCard> generatePointsCards(int playersNumber) throws WrongNumberOfPlayersException {

        if (playersNumber < 2 || playersNumber > 4)
            throw new WrongNumberOfPlayersException(playersNumber);

        ArrayList<PointCard> list = new ArrayList<>();

        int [][] pointsByPlayersNumber = new int[][]{
                new int[]{4, 8},        // For 2 players
                new int[]{4, 6, 8},     // For 3 players
                new int[]{2, 4, 6, 8},  // For 4 players
        };


        int tmp = playersNumber - 2;

        for(int i = 0; i < pointsByPlayersNumber[tmp].length; i++) {
            list.add(generatePointCardFromPointsGiven(pointsByPlayersNumber[tmp][i]));
        }

        return list;
    }

    /**
     * Based on the number of points this method generates a specific point card
     * @param pointsGiven the points that characterize the point card
     * @return a point card
     */
    public static PointCard generatePointCardFromPointsGiven(int pointsGiven){
        switch (pointsGiven){
            case 2:
                return new PointCard(PointEnumeration.TWO_POINTS, 2);
            case 4:
                return new PointCard(PointEnumeration.FOUR_POINTS, 4);
            case 6:
                return new PointCard(PointEnumeration.SIX_POINTS, 6);
            case 8:
                return new PointCard(PointEnumeration.EIGHT_POINTS, 8);
            default:
                return new PointCard(PointEnumeration.ZERO_POINTS, 0);
        }

    }

    /**
     * this method generates common goal cards reading a json file
     * @param path path to the json file
     * @return list of generated common goal cards
     * @throws IOException thrown when there are problems in accessing the file
     */
    public static ArrayList<CommonGoalCard> loadCommonGoalCardsFromJson(String path) throws IOException {
        return JsonFixedPatternGoalCardsParser.parseFixedPatternCommonGoals(path);
    }
}
