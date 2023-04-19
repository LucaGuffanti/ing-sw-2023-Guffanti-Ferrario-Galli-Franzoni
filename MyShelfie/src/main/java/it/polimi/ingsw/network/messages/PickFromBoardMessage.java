package it.polimi.ingsw.network.messages;


import it.polimi.ingsw.server.model.cells.Coordinates;
import it.polimi.ingsw.network.messages.enums.MessageType;

import java.util.List;

/**
 * This message is sent by the player that's active during a given turn, and contains
 * a list of coordinates that correspond to object cards in the game board. The client expects
 * a PickFromBoardResultMessage as a result (either SUCCESS or FAILURE) of the picking, which can
 * be valid or invalid.
 * @author Luca Guffanti
 */
public class PickFromBoardMessage extends Message {

    private final List<Coordinates> cardsCoordinates;
    public PickFromBoardMessage(String senderUsername, List<Coordinates> cardsCoordinates) {
        super(MessageType.PICK_FROM_BOARD, senderUsername);
        this.cardsCoordinates = cardsCoordinates;
    }

    public PickFromBoardMessage(String senderUsername, String description, List<Coordinates> cardsCoordinates) {
        super(MessageType.PICK_FROM_BOARD, senderUsername, description);
        this.cardsCoordinates = cardsCoordinates;
    }

    public List<Coordinates> getCardsCoordinates() {
        return cardsCoordinates;
    }
}
