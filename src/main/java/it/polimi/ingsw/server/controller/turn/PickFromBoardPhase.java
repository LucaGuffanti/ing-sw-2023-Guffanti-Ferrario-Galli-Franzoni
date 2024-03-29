package it.polimi.ingsw.server.controller.turn;

import it.polimi.ingsw.network.utils.Logger;
import it.polimi.ingsw.server.controller.GameController;
import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.cells.Coordinates;
import it.polimi.ingsw.server.model.player.Player;
import it.polimi.ingsw.server.model.utils.exceptions.IllegalBoardCellsPickException;
import it.polimi.ingsw.server.model.utils.exceptions.NoSpaceEnoughInShelfException;
import it.polimi.ingsw.network.ServerNetworkHandler;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.PickFromBoardMessage;
import it.polimi.ingsw.network.messages.PickFromBoardResultMessage;
import it.polimi.ingsw.network.utils.ResponsesDescriptions;
import it.polimi.ingsw.network.messages.enums.ResponseResultType;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * The phase in which the player chooses the coordinates of the object cards that he
 * wants to pick from the board.
 * @author Luca Guffanti
 */
public class PickFromBoardPhase extends TurnPhase{
    public PickFromBoardPhase(Game game, GameController controller) {
        super(game, controller);
    }

    /**
     * This method validates a message
     * @param message the message to validate
     * @return whether the message is valid
     */
    @Override
    public boolean validate(Message message) {

        // the message must be an instance of PickFromBoardMessage
        if (!(message instanceof PickFromBoardMessage)) {
            return false;
        }

        PickFromBoardMessage castMessage = (PickFromBoardMessage) message;
        int activeIndex = controller.getActivePlayerIndex();
                // verifying that there are one to three group of coordinates
        List<Coordinates> coords = castMessage.getCardsCoordinates();

        Predicate<PickFromBoardMessage> validationCondition = msg ->
            (
                // the sender must be the active player
                msg.getSenderUsername().equals(controller.getOrderedPlayersNicks().get(activeIndex))
                // the coordinates list must exist
                && coords != null
                // the dimension of the coordinates list must be between 1 and 3
                && coords.size() <= 3
                && coords.size() >= 1
            );
        return validationCondition.test(castMessage);
    }

    /**
     * This method handles the message for {@link PickFromBoardPhase}.
     * <h3>Behavior</h3>
     * <ul>
     *   <li>
     *     well structured message?
     *     <ul>
     *         <li>No -> notify the user with a failure and wait for the next message</li>
     *          <li>Yes -> is the move possible?
     *              <ul>
     *                  <li>
     *                      Yes -> notify the player with a success and save the submitted coordinates. Advance to the next state.
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

        /*
            The message is validated in terms of structure. if the validation fails a failure message
            is sent to the client
         */
        if (!validate(message)) {
            controller.getNetworkHandler().sendToPlayer(
                    message.getSenderUsername(),
                    new PickFromBoardResultMessage(
                    ServerNetworkHandler.HOSTNAME,
                    ResponsesDescriptions.BADLY_FORMATTED,
                    ResponseResultType.FAILURE,
                    message.getSenderUsername()
            ));
            Logger.controllerWarning(message.getSenderUsername() + " sent a badly structured PickFromBoardMessage" +
                    " or " + message.getSenderUsername() + " isn't the active player ("+controller.getOrderedPlayersNicks().get(controller.getActivePlayerIndex())+")");
            return;
        }


        PickFromBoardMessage pickMessage = (PickFromBoardMessage) message;


        Player activePlayer = game.getPlayers().stream().
                filter(p -> p.getNickname().equals(pickMessage.getSenderUsername()))
                .findFirst().get();

        String responseDescription;
        ResponseResultType resultType;


        /*
            Then the game back-end checks that the move is valid based on the rules of the game.
            Based on the validity of the move the response message gets built with a description
            that's specific to the context (type of error or acknowledge of success).
         */
        try {
            game.checkBoardPickValidity(activePlayer.getShelf(), pickMessage.getCardsCoordinates());
            responseDescription = ResponsesDescriptions.PICKING_SUCCESS;
            resultType = ResponseResultType.SUCCESS;
            Logger.controllerWarning(message.getSenderUsername()+ "'s cards selection is valid");
        } catch (IllegalBoardCellsPickException e) {
            responseDescription = ResponsesDescriptions.PICKING_ERROR_WRONG_PICK;
            resultType = ResponseResultType.FAILURE;
            Logger.controllerWarning(message.getSenderUsername()+ "'s move is against the rules");
        } catch (NoSpaceEnoughInShelfException e) {
            Logger.controllerWarning("Not enough space in " + message.getSenderUsername()+ "'s shelf");
            responseDescription = ResponsesDescriptions.PICKING_ERROR_NO_SPACE;
            resultType = ResponseResultType.FAILURE;
        }

        /*
            The message is sent to the player, and the phase advances if the move is valid.
         */
        controller.getNetworkHandler().sendToPlayer(
                message.getSenderUsername(),
                new PickFromBoardResultMessage(
                ServerNetworkHandler.HOSTNAME,
                responseDescription,
                resultType,
                message.getSenderUsername()
        ));

        if (resultType.equals(ResponseResultType.SUCCESS)) {
            wrapPhaseAndAdvance(message);
        }
    }

    /**
     * This method regards the advance of the phase of the turn: firstly the chosen coordinates are
     * saved in the state of the controller so that they can be retrieved later during the second phase of the turn, and
     * the game controller is notified.
     *
     * @param message the message received from the client
     */
    @Override
    public void wrapPhaseAndAdvance(Message message) {
        PickFromBoardMessage pickMessage = (PickFromBoardMessage) message;
        controller.setChosenCoords((ArrayList<Coordinates>) pickMessage.getCardsCoordinates());
        controller.setTurnPhase(new PutInShelfPhase(game, controller));
    }
}
