package it.polimi.ingsw.client.view.cli;

import it.polimi.ingsw.client.controller.commandHandlers.*;
import it.polimi.ingsw.client.controller.exceptions.BadlyFormattedParametersException;
import it.polimi.ingsw.client.controller.exceptions.CommandNotAvailableInThisPhaseException;
import it.polimi.ingsw.client.controller.stateController.StateContainer;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Scanner;

/**
 * The command picker is a thread that constantly scans System.in waiting for a command.
 * As a command is inserted by the user, this object executes it.
 * @author Luca Guffanti
 */
public class CommandPicker implements Runnable{

    /**
     * Instance of the cli
     */
    private Cli cli;
    /**
     * The state of the game
     */
    private StateContainer stateContainer;
    /**
     * Input stream used to take inputs
     */
    private InputStream inputStream;

    /**
     * Map between the input command and the appropriate command handler
     */
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
     * This method evaluates the user input, finds the correct CommandHandler for it
     * and execute the command.
     * Ex. "/pb 3 4 3 8" --> PickFromBoardCommandHandler
     * The parameters will be checked in the handler
     *
     * @see CliCommandHandler
     * @param userInput User inserted input representing the desired command.
     */
    private void evaluateUserInput(String userInput) {

        String[] commandSubStrings = userInput.split(" ");

        if (!inputCommandMap.containsKey(commandSubStrings[0])) {
            Printer.error(commandSubStrings[0] + " is an unknown command\n" +
                    "Write /help to learn about every command");
        } else {
            try {
                inputCommandMap.get(commandSubStrings[0]).execute(userInput.substring(commandSubStrings[0].length()).trim(), stateContainer.getCurrentState());
            } catch (BadlyFormattedParametersException e) {
                Printer.error(userInput + " is an available command but it has badly formatted parameters");
                Printer.error(inputCommandMap.get(commandSubStrings[0]).getCommandDescription());
            } catch (CommandNotAvailableInThisPhaseException e) {
                Printer.error(userInput+ " is a command that can't be used in this phase ["+cli.getStateContainer().getCurrentState().getCurrentPhase()+"]");
                Printer.error("Write /help to learn about every command");

            }
        }

    }

    /**
     * This method loads the command map with the correct handler. When a new command is added this method
     * must be updated.
     */
    private void loadCommandMap() {
        inputCommandMap.put(LoginCommandHandler.commandLabel, new LoginCommandHandler(cli));
        inputCommandMap.put(JoinGameCommandHandler.commandLabel, new JoinGameCommandHandler(cli));
        inputCommandMap.put(PlayersNumberCommandHandler.commandLabel, new PlayersNumberCommandHandler(cli));
        inputCommandMap.put(PickFromBoardCommandHandler.commandLabel, new PickFromBoardCommandHandler(cli));
        inputCommandMap.put(ShowViewCommandHandler.commandLabel, new ShowViewCommandHandler(cli));
        inputCommandMap.put(SelectColumnCommandHandler.commandLabel, new SelectColumnCommandHandler(cli));
        inputCommandMap.put(ChatCommandHandler.commandLabel, new ChatCommandHandler(cli));
        inputCommandMap.put(HelpCommandHandler.commandLabel, new HelpCommandHandler(cli));
        inputCommandMap.put(ReloadGameCommandHandler.commandLabel, new ReloadGameCommandHandler(cli));
        inputCommandMap.put(QuitCommandHandler.commandLabel, new QuitCommandHandler(cli));
    }
}
