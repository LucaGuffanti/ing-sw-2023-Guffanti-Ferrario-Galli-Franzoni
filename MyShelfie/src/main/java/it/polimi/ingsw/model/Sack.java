package it.polimi.ingsw.model;

import it.polimi.ingsw.model.cards.CardBuilder;
import it.polimi.ingsw.model.cards.ObjectCard;
import it.polimi.ingsw.model.cards.ObjectTypeEnum;
import it.polimi.ingsw.model.utils.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * The sack class contains all the object cards and is populated when initSack() is called.
 * Cards are held in an ArrayList and there exists methods to generate cards and to shuffle them
 *
 * @see it.polimi.ingsw.model.cards.ObjectCard
 * @author Luca Guffanti
 * */
public class Sack {
    private ArrayList<ObjectCard> cards;

    public Sack() {
        cards = new ArrayList<>();
        initSack();
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
     * @param cards the arraylist of cards to be shuffled
     * @return an arrayList containing the shuffled cards picked from the parameter
     */
    private ArrayList<ObjectCard> randomShuffle(ArrayList<ObjectCard> cards) {
        // TODO
        return null;
    }

    public void pickFromSack() {
        // TODO
    }

}
