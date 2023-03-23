package it.polimi.ingsw.model;

import it.polimi.ingsw.model.cards.CommonGoalCard;
import it.polimi.ingsw.model.cards.PersonalGoalCard;
import it.polimi.ingsw.model.utils.exceptions.WrongNumberOfPlayersException;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * This class is effectively a deck of cards. It's instantiated once in the entire program
 * (<b>Singleton Design Pattern</b>) and contains a copy of each personal and common goal cards.
 * <br>
 * This class contains two lists, one for each general type of goal card (either Common or Personal), and cards are generated
 * when the cards resources are loaded from the json config file.
 * <br>
 * When a random card is requested by the game class, a new instance of the already loaded card is returned.
 * <br>
 * The use of the singleton pattern ensures that the single instance of this class is shared by different game instance
 * throughout the program, and the json file in which the definition of the goal cards is found has to be parsed only once.
 *
 * @author Luca Guffanti
 * @see it.polimi.ingsw.model.cards.CommonGoalCard
 * @see it.polimi.ingsw.model.cards.PersonalGoalCard
 * @see Game
 */
public class GoalCardsDeckSingleton {
    public static GoalCardsDeckSingleton instance = null;
    private ArrayList<PersonalGoalCard> personalGoals;
    private ArrayList<CommonGoalCard> commonGoals;

    private GoalCardsDeckSingleton() {
        // TODO load resources from the json file
        personalGoals = new ArrayList<>();
        commonGoals = new ArrayList<>();

        // call CardBuilder.loadPersonalGoalCardsFromJson(path_to_file);
        // call CardBuilder.loadCommonGoalCardsFromJson(path_to_file);
    }

    public static synchronized GoalCardsDeckSingleton getInstance() {
        if (instance == null) {
            instance = new GoalCardsDeckSingleton();
        }
        return instance;
    }

    /**
     * This method shuffles the personalGoals list and returns the first nPlayers elements as an arrayList
     * @param nPlayers the number of players
     * @return an array list containing personal goal cards, whose size is nPlayers.
     * @throws WrongNumberOfPlayersException if the number of players passed as a parameter isn't between 2 and 4
     */
    public ArrayList<PersonalGoalCard> pickPersonalGoals(int nPlayers) throws WrongNumberOfPlayersException {

        if (nPlayers < 2 || nPlayers > 4) {
            throw new WrongNumberOfPlayersException(nPlayers);
        }

        ArrayList<PersonalGoalCard> temp;
        synchronized (this) {
             temp = new ArrayList<>(personalGoals);
        }
        Collections.shuffle(temp);

        ArrayList<PersonalGoalCard> toBeReturned = new ArrayList<>();
        for(int i = 0; i < nPlayers; i++) {
            // TODO will be implemented when the goalCards are implemented
        }

        return null;
    }

    /**
     * This method shuffles the commonGoals list and returns the first 2 elements as an arrayList
     * @return an array list containing 2 common goal cards
     */
    public ArrayList<CommonGoalCard> pickCommonGoals() {
        // TODO depends on the implementation of the commonGoalCard.
        // will be implemented when commonGoalCard is correctly implemented.
        return null;
    }
}