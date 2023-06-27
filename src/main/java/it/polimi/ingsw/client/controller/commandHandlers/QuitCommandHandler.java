package it.polimi.ingsw.client.controller.commandHandlers;


import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.client.controller.ClientPhasesEnum;
import it.polimi.ingsw.client.controller.exceptions.BadlyFormattedParametersException;
import it.polimi.ingsw.client.controller.exceptions.CommandNotAvailableInThisPhaseException;
import it.polimi.ingsw.client.controller.stateController.ClientState;
import it.polimi.ingsw.client.view.cli.Cli;
import it.polimi.ingsw.network.rmi.RMIClient;

import java.util.List;

/**
 * This handler manages the exit of a player from the game
 * @author Luca Guffanti
 */
public class QuitCommandHandler extends CliCommandHandler{
    /**
     * The label of the command: the string that should be inserted to invoke the command
     */
    public final static String commandLabel = "/quit";
    /**
     * The description of the command
     */
    public final static String commandDescription = "Quit the server";

    public QuitCommandHandler(Cli cli) {
        super(cli);
    }

    /**
     * After the correct checks are made, this method makes the user quit the system
     * @param commandInput The user's input
     * @param state the state of the client
     * @throws CommandNotAvailableInThisPhaseException thrown if the command is not available in a given phase
     * @throws BadlyFormattedParametersException thrown if the command presents badly formatted parameters
     * */
    @Override
    public void execute(String commandInput, ClientState state) throws BadlyFormattedParametersException, CommandNotAvailableInThisPhaseException {
        List<String> params = splitAndTrimInput(commandInput);
        if (!checkParameters(params)) {
          throw new BadlyFormattedParametersException();
        }

        if (ClientManager.getInstance().getStateContainer().getCurrentState().getCurrentPhase().equals(ClientPhasesEnum.LOGIN)) {
            System.exit(0);
        }

        if (ClientManager.getInstance().getNetworkHandler() instanceof RMIClient) {
            ((RMIClient) ClientManager.getInstance().getNetworkHandler()).requireDisconnection(
                    ClientManager.getInstance().getStateContainer().getCurrentState().getUsername()
            );
        }
        System.exit(0);
    }

    /**
     * The quit method, independently of parameters, is always accepted
     * @param parameters the list of parameters after the command. ex: "/send "hi guys" --to Pippo Pluto
     *                   parameters = {"hi guys", "--to", "Pippo", "Pluto"}
     * @return true
     */
    @Override
    protected boolean checkParameters(List<String> parameters) {
        return true;
    }

    @Override
    public String getCommandDescription() {
        return null;
    }
}
