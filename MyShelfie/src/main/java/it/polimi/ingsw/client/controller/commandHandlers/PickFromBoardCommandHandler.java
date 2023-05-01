package it.polimi.ingsw.client.controller.commandHandlers;

import it.polimi.ingsw.client.controller.ClientPhasesEnum;
import it.polimi.ingsw.client.controller.exceptions.BadlyFormattedParametersException;
import it.polimi.ingsw.client.controller.exceptions.CommandNotAvailableInThisPhaseException;
import it.polimi.ingsw.client.controller.messageHandling.messageHandlers.PickFromBoardMessageHandler;
import it.polimi.ingsw.client.controller.stateController.ClientState;
import it.polimi.ingsw.client.view.cli.Cli;
import it.polimi.ingsw.network.messages.PickFromBoardMessage;
import it.polimi.ingsw.server.model.cells.Coordinates;

import java.util.*;
import java.util.regex.Pattern;

public class PickFromBoardCommandHandler extends CliCommandHandler{
    private final HashSet<ClientPhasesEnum> availablePhases = new HashSet<>(Arrays.asList(
            ClientPhasesEnum.PICK_FORM_BOARD
    ));


    public final static String commandLabel = "/pb";
    public final static String commandDescription = "Pick Form Board Command\n\n" +
            "Usage: +"+commandLabel+" x1 y1 x2 y2 where x1, y1 represents the beginning of the row of tiles to pick and x2, y2 the end.";


    public PickFromBoardCommandHandler(Cli cli) {
        super(cli);
    }


    @Override
    public void execute(String commandInput, ClientState state) throws BadlyFormattedParametersException, CommandNotAvailableInThisPhaseException {

        List<String> parameters = super.splitAndTrimInput(commandInput);

        if(!checkParameters(parameters)){
            throw new BadlyFormattedParametersException();
        }
        if(!super.checkAvailability(availablePhases, state)){
            throw new CommandNotAvailableInThisPhaseException();
        }


        List<Coordinates> coordinates = new ArrayList<Coordinates>();

        coordinates.add(new Coordinates(parameters.get(1), parameters.get(2)));
        if (parameters.size() >= 5) {
            coordinates.add(new Coordinates(parameters.get(3), parameters.get(4)));
            if (parameters.size() == 7) {
                coordinates.add(new Coordinates(parameters.get(5), parameters.get(6)));
            }
        }

        PickFromBoardMessage msg = PickFromBoardMessageHandler.createMessage(state.getUsername(), coordinates);

        super.getCli().dispatchMessageToNetwork(msg);
    }

    @Override
    protected boolean checkParameters(List<String> parameters) {
        ArrayList<String> temp = new ArrayList<>(parameters);
        temp.remove(0);
        if (!(temp.size() == 2 || temp.size() == 4 || temp.size() == 6))
            return false;
        for(String param : temp) {
            if(!param.matches("[0-8]")) {
                return false;
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
