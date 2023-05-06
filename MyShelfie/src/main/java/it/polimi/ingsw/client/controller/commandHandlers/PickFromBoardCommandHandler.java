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
            "Usage: +"+commandLabel+" x1 y1 [x2 y2 x3 y3] with xi yi being the coordinates of the cards you want to pick";


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
        return true;
    }

    public static String getCommandLabel() {
        return commandLabel;
    }

    public String getCommandDescription() {
        return commandDescription;
    }

}
