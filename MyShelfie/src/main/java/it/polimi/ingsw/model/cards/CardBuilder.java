package it.polimi.ingsw.model.cards;

import java.util.ArrayList;

/**
 * This class contains static methods that can be used to mass
 * produce different types of cards.
 *
 * @author Luca Guffanti
 */
public class CardBuilder {

    /**
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
}
