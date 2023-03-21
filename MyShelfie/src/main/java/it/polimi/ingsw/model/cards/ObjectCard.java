package it.polimi.ingsw.model.cards;

/**
 *
 * This class describes the object cards. Each card is characterized by a type
 * that can be found ObjectTypeEnum.
 * @author Luca Guffanti
 * @see ObjectTypeEnum
 */
public class ObjectCard {
    private ObjectTypeEnum type;
    public ObjectCard(ObjectTypeEnum type) {
        this.type = type;
    }

    public ObjectTypeEnum getType() {
        return type;
    }

}
