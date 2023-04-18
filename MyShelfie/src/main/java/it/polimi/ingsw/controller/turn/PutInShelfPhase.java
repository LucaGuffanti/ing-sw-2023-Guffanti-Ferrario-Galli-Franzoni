package it.polimi.ingsw.controller.turn;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.cells.Coordinates;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.utils.exceptions.IllegalBoardCellsPickException;
import it.polimi.ingsw.model.utils.exceptions.IllegalShelfActionException;
import it.polimi.ingsw.model.utils.exceptions.NoSpaceEnoughInShelfColumnException;
import it.polimi.ingsw.model.utils.exceptions.NoSpaceEnoughInShelfException;
import it.polimi.ingsw.network.NetworkHandler;
import it.polimi.ingsw.network.messages.*;
import it.polimi.ingsw.network.messages.enums.ResponseResultType;
import it.polimi.ingsw.network.utils.ResponsesDescriptions;

import java.util.ArrayList;
import java.util.function.Predicate;

/**
 * The phase in which the player chooses a column of his shelf in which to
 * put the previously chosen object cards (that were taken from the board)
 * @author Luca Guffanti
 */
public class PutInShelfPhase extends TurnPhase{
    public PutInShelfPhase(Game game, GameController controller) {
        super(game, controller);
    }

    @Override
    public boolean validate(Message message) {
        // the message must be an instance of selectColumnMessage
        if (!(message instanceof SelectColumnMessage)) {
            return false;
        }

        SelectColumnMessage castMessage = (SelectColumnMessage) message;
        int activeIndex = controller.getActivePlayerIndex();
        int shelfIndex = castMessage.getColumnIndex();

        // verifying that the shelf index is between 0 and the length of the shelf
        Predicate<SelectColumnMessage> validationCondition = msg ->
                (
                        // the sender must be the active player
                        msg.getSenderUsername().equals(controller.getOrderedPlayersNicks().get(activeIndex))
                        // the coordinates list must exist
                        && shelfIndex >= 0
                        && shelfIndex < 5
                );
        return validationCondition.test(castMessage);
    }
    /**
     * This method handles the message for {@link PutInShelfPhase}.
     * <h3>Behavior</h3>
     * <ul>
     *   <li>
     *     well structured message?
     *     <ul>
     *         <li>No -> notify the user with a failure and wait for the next message</li>
     *          <li>Yes -> does the chosen column have enough space?
     *              <ul>
     *                  <li>
     *                      Yes -> retrieve the coordinates of the cards from the state of the game controller,
     *                      move the cards into the shelf, notify the player with a success and wrap the turn
     *                  </li>
     *                  <li>
     *                      No -> notify the player with a failure and wait for the next message
     *                  </li>
     *              </ul>
     *          </li>
     *     </ul>
     *   </li>
     * </ul>
     * @param message the received message
     */
    @Override
    public void manageIncomingMessage(Message message) {
        if (!validate(message)) {
            controller.getNetworkHandler().sendToPlayer(new SelectColumnResultMessage(
                    NetworkHandler.HOSTNAME,
                    ResponsesDescriptions.BADLY_FORMATTED,
                    ResponseResultType.FAILURE,
                    message.getSenderUsername()
            ));
            return;
        }

        SelectColumnMessage selectColumnMessage = (SelectColumnMessage) message;

        Player activePlayer = game.getPlayers().stream().
                filter(p -> p.getNickname().equals(selectColumnMessage.getSenderUsername()))
                .findFirst().get();

        ArrayList<Coordinates> coords = controller.getChosenCoords();
        String responseDescription;
        ResponseResultType resultType;

        try {
            game.moveCardsToPlayerShelf(activePlayer, coords, selectColumnMessage.getColumnIndex());
            responseDescription = ResponsesDescriptions.SHELF_SUCCESS;
            resultType = ResponseResultType.SUCCESS;
        } catch (Exception e) {
            responseDescription = ResponsesDescriptions.SHELF_ERROR_WRONG_SELECTION;
            resultType = ResponseResultType.FAILURE;
        }

        controller.getNetworkHandler().sendToPlayer(new SelectColumnResultMessage(
                NetworkHandler.HOSTNAME,
                responseDescription,
                resultType,
                message.getSenderUsername()
        ));

        if (resultType.equals(ResponseResultType.SUCCESS)) {
            wrapPhaseAndAdvance(message);
        }

    }
    @Override
    public void wrapPhaseAndAdvance(Message message) {
        controller.endTurn();
    }


}
