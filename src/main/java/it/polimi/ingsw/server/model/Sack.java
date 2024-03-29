package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.cards.CardBuilder;
import it.polimi.ingsw.server.model.cards.ObjectCard;
import it.polimi.ingsw.server.model.cards.ObjectTypeEnum;
import it.polimi.ingsw.server.model.utils.Constants;
import it.polimi.ingsw.server.model.utils.exceptions.EmptySackException;

import java.util.ArrayList;
import java.util.Collections;

/**
 * The sack class contains all the object cards and is populated when initSack() is called.
 * Cards are held in an ArrayList and there exists methods to generate cards and to shuffle them
 *
 * @see ObjectCard
 * @author Luca Guffanti
 * */
public class Sack {
    /**
     * The tiles that will be put on the board
     */
    private ArrayList<ObjectCard> cards;

    public Sack() {
        cards = new ArrayList<>();
        initSack();
    }

    public Sack(Sack sack) {
        this.cards = sack.getCards();
    }

    /**
     * Initializes the card sack by generating the object cards and by shuffling them
     */
    public void initSack() {
        cards = generateCards();
        cards = randomShuffle(cards);
    }

    /**
     * @return an arrayList containing the generated cards
     */
    private ArrayList<ObjectCard> generateCards() {
        ArrayList<ObjectCard> objectCards = new ArrayList<>();

        objectCards.addAll(CardBuilder.generateObjectCards(Constants.OBJECT_CARDS_PER_TYPE, ObjectTypeEnum.CAT));
        objectCards.addAll(CardBuilder.generateObjectCards(Constants.OBJECT_CARDS_PER_TYPE, ObjectTypeEnum.BOOK));
        objectCards.addAll(CardBuilder.generateObjectCards(Constants.OBJECT_CARDS_PER_TYPE, ObjectTypeEnum.TOY));
        objectCards.addAll(CardBuilder.generateObjectCards(Constants.OBJECT_CARDS_PER_TYPE, ObjectTypeEnum.FRAME));
        objectCards.addAll(CardBuilder.generateObjectCards(Constants.OBJECT_CARDS_PER_TYPE, ObjectTypeEnum.PLANT));
        objectCards.addAll(CardBuilder.generateObjectCards(Constants.OBJECT_CARDS_PER_TYPE, ObjectTypeEnum.TROPHY));

        return objectCards;
    }

    /**
     * This method randomly shuffles the cards. It's important as cards shouldn't be shuffled every time they're
     * requested. By pre-shuffling cards it is possible to simply access them sequentially. In order to shuffle the list
     * the method <b>Collections.shuffle()</b> is called.
     * @param cards the arraylist of cards to be shuffled
     * @return an arrayList containing the shuffled cards picked from the parameter
     */
    private ArrayList<ObjectCard> randomShuffle(ArrayList<ObjectCard> cards) {

        ArrayList<ObjectCard> toBeRandomized = new ArrayList<>(cards);

        // the number of times the shuffling is repeated
        int numOfRandomizationIterations = 4;

        for(int i = 0; i < numOfRandomizationIterations; i++) {
            Collections.shuffle(toBeRandomized);
        }

        return toBeRandomized;
    }

    /**
     * @return the first card in the list of cards, ArrayList<ObjectCard> cards.
     * */
    public ObjectCard pickFromSack() throws EmptySackException {

        if(cards.size() == 0) {
            throw new EmptySackException();
        }

        // TODO: understand if it's better to remove the card from the tail of the arraylist.
        return cards.remove(0);
    }

    public ArrayList<ObjectCard> getCards() {
        return cards;
    }

    public void setCards(ArrayList<ObjectCard> cards) {
        this.cards = cards;
    }
}
