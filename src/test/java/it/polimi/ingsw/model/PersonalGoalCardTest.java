package it.polimi.ingsw.model;

import it.polimi.ingsw.server.model.GoalCardsDeckSingleton;
import it.polimi.ingsw.server.model.cards.ObjectCard;
import it.polimi.ingsw.server.model.cards.ObjectTypeEnum;
import it.polimi.ingsw.server.model.cards.PatternCell;
import it.polimi.ingsw.server.model.cards.goalCards.PersonalGoalCard;
import it.polimi.ingsw.server.model.player.Player;
import it.polimi.ingsw.server.model.player.Shelf;
import it.polimi.ingsw.server.model.utils.exceptions.WrongNumberOfPlayersException;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * This class contains methods that check the correct behavior of the
 * calculate points method in personalGoalCards
 * @author Luca Guffanti
 */
public class PersonalGoalCardTest {

    private static final Map<Integer, Integer> matchesToPointsMap = Map.of(
            0, 0,
            1, 1,
            2, 2,
            3, 4,
            4, 6,
            5, 9,
            6, 12
    );

    /**
     * This method verifies the correctness of the implementation of the calculate points method.

     *
     * @throws WrongNumberOfPlayersException
     */
    @Test
    public void calculatePoints() throws WrongNumberOfPlayersException {
        GoalCardsDeckSingleton g = GoalCardsDeckSingleton.getInstance();

        ArrayList<PersonalGoalCard> personalGoals;
        int testNumber = 1;
        Player player = new Player("test");
        Random rng = new Random();
        int upperBound = 1500;

        // the check is repeated many times: every time
        for (int repetitions = 0; repetitions < upperBound; repetitions++) {
            for(int nPlayers = 2; nPlayers <= 4; nPlayers++) {

                // cards are picked from the deck based on the number of players
                personalGoals = g.pickPersonalGoals(nPlayers);
                for (int i = 0; i < personalGoals.size(); i++) {

                    // for each card a random number between 0 and 6 is generated.
                    // it represents various degrees of completion of the board at the end of the game
                    int elementsToBeMatched = rng.nextInt(6);
                    int elementsMatched = 0;
                    Shelf playerShelf = new Shelf();

                    // a board is populated based on the number of elements to be matched with the correct tiles
                    for (PatternCell cell : personalGoals.get(i).getPattern().getCoveredCells()) {
                        if (elementsMatched == elementsToBeMatched) {
                            break;
                        }
                        ObjectTypeEnum typeOfCell = cell.getAdmittedType().get();
                        int cellX = cell.getX();
                        int cellY = cell.getY();

                        playerShelf.getCell(cellX, cellY).setCellCard(Optional.of(new ObjectCard(typeOfCell)));

                        elementsMatched++;
                    }
                    // and the check is done.
                    player.setShelf(playerShelf);
                    assertEquals(matchesToPointsMap.get(elementsToBeMatched), personalGoals.get(i).calculatePoints(player));
                }
            }
        }
    }
}
