package it.polimi.ingsw.client.controller.exceptions;

/**
 * This exception is thrown when a command has badly formatted parameters
 */
public class BadlyFormattedParametersException extends CliCommandException{
    public BadlyFormattedParametersException() {
        super("The requested parameters are not valid for this command.");
    }
}
