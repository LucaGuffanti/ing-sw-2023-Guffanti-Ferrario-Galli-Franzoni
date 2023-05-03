package it.polimi.ingsw.client.controller.commandHandlers;

import it.polimi.ingsw.client.controller.ClientPhasesEnum;
import it.polimi.ingsw.client.controller.exceptions.BadlyFormattedParametersException;
import it.polimi.ingsw.client.controller.exceptions.CommandNotAvailableInThisPhaseException;
import it.polimi.ingsw.client.controller.stateController.ClientState;
import it.polimi.ingsw.client.view.cli.Cli;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * These type of classes handles the validation of
 * a command inserted in the cli by the user, based on the
 * parameters and the current game phase.
 *
 * @author Daniele Ferrario
 */
public abstract class CliCommandHandler {

    private Cli cli;
    /**
     * This class describes the handlers for the available commands for the cli.
     * They will check the syntax and the current ClientStatus for the command
     * to be valid, and then the cli will be notified with the payload generated by the command.
     *
     * @apiNote The Cli and the method CliCommandHandler.execute(), together, implement the "Visitor pattern",
     * picking the right cli method to handle the appropriate command handler response ( A CliView to change or a Message to be sent to server )

     * @param cli
     */
    public CliCommandHandler(Cli cli) {
        this.cli = cli;
    }

    /**
     * This method is invoked to evaluate the user's command.
     * If the command is parsed successfully, the listeners in the cli will be
     * notified with the result related to the command.
     *
     * @param commandInput The user's input
     * @throws BadlyFormattedParametersException The inserted parameters are not valid for this specific command.
     * @throws CommandNotAvailableInThisPhaseException The command cannot be invoked during the current game phase.
     * @return
     */
    public abstract void execute(String commandInput, ClientState state) throws BadlyFormattedParametersException, CommandNotAvailableInThisPhaseException;

    /**
     * Checks if the inserted parameters are valid for this command.
     * @param parameters the list of parameters after the command. ex: "/send "hi guys" --to Pippo Pluto
     *                   parameters = {"hi guys", "--to", "Pippo", "Pluto"}
     * @return if command is valid.
     */
    protected abstract boolean checkParameters(List<String> parameters);
    public Cli getCli() {
        return cli;
    }

    protected boolean checkAvailability(HashSet<ClientPhasesEnum> availablePhases, ClientState state){
        return availablePhases.contains(state.getCurrentPhase());
    }

    public abstract String getCommandDescription();

    // Trim starting and ending spaces, and return substrings
    public List<String> splitAndTrimInput(String input){
        return Stream.of(input.trim().split(" ")).collect(Collectors.toList());
    }
}
