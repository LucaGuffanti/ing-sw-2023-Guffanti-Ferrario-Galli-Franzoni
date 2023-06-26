package it.polimi.ingsw.server.model.cards;

/**
 * This class describes the object cards. Each card is characterized by a type
 * that can be found in ObjectTypeEnum.
 * @author Luca Guffanti
 * @see ObjectTypeEnum
 */
public class ObjectCard {
    /**
     * The type of the object card
     */
    private final ObjectTypeEnum type;
    public ObjectCard(ObjectTypeEnum type) {
        this.type = type;
    }

    public ObjectTypeEnum getType() {
        return type;
    }

}
