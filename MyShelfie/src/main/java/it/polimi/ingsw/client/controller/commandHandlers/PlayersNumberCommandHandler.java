package it.polimi.ingsw.client.controller.commandHandlers;

import it.polimi.ingsw.client.controller.ClientPhasesEnum;
import it.polimi.ingsw.client.controller.exceptions.BadlyFormattedParametersException;
import it.polimi.ingsw.client.controller.exceptions.CommandNotAvailableInThisPhaseException;
import it.polimi.ingsw.client.controller.messageHandling.messageHandlers.PlayersNumberSelectionMessageHandler;
import it.polimi.ingsw.client.controller.stateController.ClientState;
import it.polimi.ingsw.client.view.cli.Cli;
import it.polimi.ingsw.network.messages.NumberOfPlayersSelectionMessage;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class PlayersNumberCommandHandler extends CliCommandHandler{
    private final HashSet<ClientPhasesEnum> availablePhases = new HashSet<>(Arrays.asList(
            ClientPhasesEnum.NOT_JOINED
    ));


    public final static String commandLabel = "/n";
    public final static String commandDescription = "Select the number of players for the new game when user is admin.\n\n" +
            "Usage: "+commandLabel+" x    with 2 <= x <=4";


    public PlayersNumberCommandHandler(Cli cli) {
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

        NumberOfPlayersSelectionMessage msg = PlayersNumberSelectionMessageHandler.createMessage(state.getUsername(), Integer.parseInt(parameters.get(1)));
        super.getCli().dispatchMessageToNetwork(msg);
    }

    @Override
    protected boolean checkParameters(List<String> parameters) {
        if(parameters.size() == 2){

            int num;
            try{
                num = Integer.parseInt(parameters.get(1));
                if(num < 2 || num > 4)
                    return false;
            }catch (NumberFormatException ex){
                return false;
            }
        }else{
            return false;
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