package it.polimi.ingsw.client.view.cli.commandHandlers;

import it.polimi.ingsw.client.view.cli.Cli;

import java.util.List;
import java.util.Set;

public abstract class CliCommandHandler {

    private Cli cli;
    private String username;

    /**
     * This class describes the handlers for the available commands for the cli.
     * They will check the syntax and the current ClientStatus for the command
     * to be valid, and then the cli will be notified with the payload generated by the command.
     *
     * @apiNote The Cli and the method CliCommandHandler.execute(), together, implement the "Visitor pattern",
     * picking the right cli method to handle the appropriate command handler response ( A CliView to change or a Message to be sent to server )
     *
     * @param username
     * @param cli
     */
    public CliCommandHandler(Cli cli, String username) {
        this.cli = cli;
        this.username = username;
    }

    /**
     * This method is invoked to evaluate the user's command.
     * If the command is parsed successfully, the listeners in the cli will be
     * notified with the result related to the command.
     *
     * @param commandInput The user's input
     * @return
     */
    public abstract void execute(String commandInput);

    /**
     * Checks if the inserted parameters are valid for this command.
     * @param parameters the list of parameters after the command. ex: "/sendMessage "hi guys" --to Pippo Pluto
     *                   parameters = {"hi guys", "--to", "Pippo", "Pluto"}
     * @return if command is valid.
     */
    protected abstract boolean checkParameters(List<String> parameters);
    protected boolean areParametersValid(String userInput){
        return true;
    }


    public Cli getCli() {
        return cli;
    }

    public String getUsername() {
        return username;
    }
}
