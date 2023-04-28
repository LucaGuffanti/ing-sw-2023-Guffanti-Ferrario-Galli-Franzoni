package it.polimi.ingsw.client.view.cli;

import it.polimi.ingsw.client.controller.commandHandlers.CliCommandHandler;
import it.polimi.ingsw.client.controller.commandHandlers.PickFromBoardCommandHandler;
import it.polimi.ingsw.client.controller.commandHandlers.ShowViewCommandHandler;
import it.polimi.ingsw.client.controller.exceptions.BadlyFormattedParametersException;
import it.polimi.ingsw.client.controller.exceptions.CliCommandException;
import it.polimi.ingsw.client.controller.exceptions.CommandNotAvailableInThisPhaseException;
import it.polimi.ingsw.client.controller.exceptions.NoCommandFoundException;
import it.polimi.ingsw.client.controller.stateController.StateContainer;

import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * The command picker is a thread that constantly scans System.in waiting for a command.
 * As a command is inserted by the user, this object executes it.
 * @author Luca Guffanti
 */
public class CommandPicker implements Runnable{

    private Cli cli;
    private StateContainer stateContainer;
    private InputStream inputStream;
    private final HashMap<String, CliCommandHandler> inputCommandMap;
    public CommandPicker(Cli cli, InputStream inputStream) {
        this.cli = cli;
        this.inputStream = inputStream;
        this.stateContainer = cli.getStateContainer();

        inputCommandMap = new HashMap<>();
        // at the initialization of the command picker, the hashmap containing each command with each label is loaded
        loadCommandMap();
    }

    @Override
    public void run() {
        Scanner sc = new Scanner(inputStream);
        while (!Thread.currentThread().isInterrupted()) {
            // acquiring a new string
            String inserted = sc.nextLine();

            // trimming spaces
            inserted = inserted.trim();
            evaluateUserInput(inserted);
        }
    }

    /**
     * Evaluate the user input, finds the correct CommandHandler for it
     * and execute the command.
     * Ex. "/pb 3 4 3 8" --> PickFromBoardCommandHandler
     *
     * The parameters will be checked in the handler
     *
     * @see CliCommandHandler
     * @param userInput User inserted input representing the desired command.
     */
    private void evaluateUserInput(String userInput) {



        String[] commandSubStrings = userInput.split(" ");

        if (!inputCommandMap.containsKey(commandSubStrings[0])) {
            Printer.error(commandSubStrings[0] + " is an unknown command");
        } else {
            try {
                inputCommandMap.get(commandSubStrings[0]).execute(userInput, stateContainer.getCurrentState());
            } catch (BadlyFormattedParametersException e) {
                Printer.error(userInput + " has badly formatted parameters");
                Printer.error(inputCommandMap.get(commandSubStrings[0]).getCommandDescription());
            } catch (CommandNotAvailableInThisPhaseException e) {
                Printer.error(userInput+ " is a command that can't be used in this phase ["+cli.getStateContainer().getCurrentState().getCurrentPhase()+"]");
            }
        }

    }

    private void loadCommandMap() {
        inputCommandMap.put(PickFromBoardCommandHandler.commandLabel, new PickFromBoardCommandHandler(cli));
        inputCommandMap.put(ShowViewCommandHandler.commandLabel, new ShowViewCommandHandler(cli));
        // TODO put here all the commands
    }
}