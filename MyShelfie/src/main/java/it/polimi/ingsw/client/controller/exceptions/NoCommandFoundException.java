package it.polimi.ingsw.client.controller.exceptions;

public class NoCommandFoundException extends CliCommandException{
    public NoCommandFoundException() {
        super("The requested command does not exists");
    }
}
