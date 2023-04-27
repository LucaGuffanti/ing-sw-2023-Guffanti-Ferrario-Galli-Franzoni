package it.polimi.ingsw.client.view.cli.commandHandlers;

import it.polimi.ingsw.client.controller.ClientPhasesEnum;
import it.polimi.ingsw.client.controller.messageHandling.messageHandlers.PickFromBoardHandler;
import it.polimi.ingsw.client.controller.stateController.ClientState;
import it.polimi.ingsw.client.view.cli.Cli;
import it.polimi.ingsw.network.messages.PickFromBoardMessage;
import it.polimi.ingsw.server.model.cells.Coordinates;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class PickFromBoardCommandHandler extends CliCommandHandler{
    private final HashSet<ClientPhasesEnum> availableStatues = new HashSet<>(Arrays.asList(
            ClientPhasesEnum.PICK_FORM_BOARD
    ));


    public final static String commandLabel = "/pb";
    public final static String commandDescription = "Pick Form Board Command\n\n" +
            "Usage: /pb x1 y1 x2 y2 where x1, y1 represents the beginning of the row of tiles to pick and x2, y2 the end.";


    public PickFromBoardCommandHandler(Cli cli) {
        super(cli);
    }


    @Override
    public void execute(String commandInput, ClientState state) {

        List<String> parameters = Arrays.asList(commandInput.split(" "));

        if(!checkParameters(parameters)){
            // Throw exception
        }

        // Remove command label
        parameters.remove(0);

        List<Coordinates> coordinates = new ArrayList<Coordinates>();
        coordinates.add(new Coordinates(parameters.get(0), parameters.get(1)));
        coordinates.add(new Coordinates(parameters.get(2), parameters.get(3)));

        PickFromBoardMessage msg = PickFromBoardHandler.createMessage(state.getUsername(), coordinates);

        super.getCli().handleCommandResponse(msg);
    }

    @Override
    protected boolean checkParameters(List<String> parameters) {

        //@Todo: implementation
        return true;
    }

    public static String getCommandLabel() {
        return commandLabel;
    }

    public String getCommandDescription() {
        return commandDescription;
    }

}
