package it.polimi.ingsw.client.controller.exceptions;

public class CommandNotAvailableInThisPhaseException extends CliCommandException{
    public CommandNotAvailableInThisPhaseException() {
        super("The requested command is not available in this game phase.");
    }
}
