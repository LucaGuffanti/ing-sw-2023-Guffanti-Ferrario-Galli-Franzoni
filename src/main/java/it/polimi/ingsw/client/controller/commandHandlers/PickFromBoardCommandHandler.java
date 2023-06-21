package it.polimi.ingsw.client.controller.commandHandlers;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.client.controller.ClientPhasesEnum;
import it.polimi.ingsw.client.controller.exceptions.BadlyFormattedParametersException;
import it.polimi.ingsw.client.controller.exceptions.CommandNotAvailableInThisPhaseException;
import it.polimi.ingsw.client.controller.messageHandling.messageHandlers.PickFromBoardMessageHandler;
import it.polimi.ingsw.client.controller.stateController.ClientState;
import it.polimi.ingsw.client.controller.utils.PickChecker;
import it.polimi.ingsw.client.view.cli.Cli;
import it.polimi.ingsw.client.view.cli.Printer;
import it.polimi.ingsw.network.messages.PickFromBoardMessage;
import it.polimi.ingsw.server.model.cards.ObjectTypeEnum;
import it.polimi.ingsw.server.model.cells.Coordinates;

import java.util.*;

/**
 * This object handles the pick from board command, by executing a check regarding the inserted coordinates
 * and by sending a message to the server if the check is successful
 * @author Daniele Ferrario, Luca Guffanti
 */
public class PickFromBoardCommandHandler extends CliCommandHandler{
    private final HashSet<ClientPhasesEnum> availablePhases = new HashSet<>(Arrays.asList(
            ClientPhasesEnum.PICK_FORM_BOARD
    ));


    public final static String commandLabel = "/pb";
    public final static String commandDescription = "Pick Form Board Command\n\n" +
            "Usage: "+commandLabel+" x1 y1 [x2 y2 x3 y3] with xi yi being the coordinates of the cards you want to pick.\n" +
            "Remember that the tiles you pick must be adjacent on either the horizontal or vertical axis, and that you can't pick\n" +
            "the same tiles. The tiles you pick can be in any order, and they'll be put in the shelf from left to right";


    public PickFromBoardCommandHandler(Cli cli) {
        super(cli);
    }


    @Override
    public void execute(String commandInput, ClientState state) throws BadlyFormattedParametersException, CommandNotAvailableInThisPhaseException {
        List<String> parameters = super.splitAndTrimInput(commandInput);

        if(!super.checkAvailability(availablePhases, state)){
            throw new CommandNotAvailableInThisPhaseException();
        }
        if(!checkParameters(parameters)){
            throw new BadlyFormattedParametersException();
        }
        // check if there's enough space in player's shelf
        String name = ClientManager.getInstance().getStateContainer().getCurrentState().getUsername();
        int currentPlayerIndex = ClientManager.getInstance().getStateContainer().getCurrentState().getOrderedPlayersNames().indexOf(name);
        ObjectTypeEnum[][] activePlayerShelf = ClientManager.getInstance().getStateContainer().getCurrentState().getShelves().get(currentPlayerIndex);
        if (PickChecker.shelfIsFull(activePlayerShelf, parameters.size() / 2)) {
            Printer.error("The selection is invalid, you don't have enough space in your shelf. Try again.");
            return;
        }

        List<Coordinates> coordinates = new ArrayList<Coordinates>();

        coordinates.add(new Coordinates(parameters.get(0), parameters.get(1)));
        if (parameters.size() >= 4) {
            coordinates.add(new Coordinates(parameters.get(2), parameters.get(3)));
            if (parameters.size() == 6) {
                coordinates.add(new Coordinates(parameters.get(4), parameters.get(5)));
            }
        }

        PickFromBoardMessage msg = PickFromBoardMessageHandler.createMessage(state.getUsername(), coordinates);

        super.getCli().dispatchMessageToNetwork(msg);
    }

    /**
     * This method checks that the coordinates follow the rules of the game.
     * @param parameters the list of parameters after the command.
     * @return whether parameters are correct
     */
    @Override
    protected boolean checkParameters(List<String> parameters) {
        ArrayList<String> temp = new ArrayList<>(parameters);
        if (!(temp.size() == 2 || temp.size() == 4 || temp.size() == 6))
            return false;
        for(String param : temp) {
            if(!param.matches("[0-8]")) {
                return false;
            }
        }

        int x1 = Integer.parseInt(parameters.get(0));
        int y1 = Integer.parseInt(parameters.get(1));

        int x2;
        int y2;

        int x3;
        int y3;

        if (parameters.size() >= 4) {
            x2 = Integer.parseInt(parameters.get(2));
            y2 = Integer.parseInt(parameters.get(3));

            if (parameters.size() == 6) {
                x3 = Integer.parseInt(parameters.get(4));
                y3 = Integer.parseInt(parameters.get(5));

                if(x1 == x2 && x2 == x3) {
                    return PickChecker.checkTripleAdjacency(y1, y2, y3);
                } else if (y1 == y2 && y2 == y3) {
                    return PickChecker.checkTripleAdjacency(x1, x2, x3);
                } else {
                    return false;
                }
            } else {
               if (x1 != x2) {
                   return x1+1 == x2 || x1-1 == x2;
               } else if (y1 != y2) {
                   return y1+1 == y2 || y1-1 == y2;
               } else {
                   return false;
               }
            }
        }
        return true;
    }

    public static String getCommandLabel() {
        return commandLabel;
    }

    public String getCommandDescription() {
        return commandDescription;
    }
}
