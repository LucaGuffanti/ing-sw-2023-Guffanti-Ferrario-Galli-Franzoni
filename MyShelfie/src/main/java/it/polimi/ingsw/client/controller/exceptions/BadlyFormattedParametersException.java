package it.polimi.ingsw.client.controller.exceptions;

public class BadlyFormattedParametersException extends CliCommandException{
    public BadlyFormattedParametersException() {
        super("The requested parameters are not valid for this command.");
    }
}
