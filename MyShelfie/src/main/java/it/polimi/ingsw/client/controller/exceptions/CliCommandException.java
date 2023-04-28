package it.polimi.ingsw.client.controller.exceptions;

public abstract class CliCommandException extends Exception{
    public CliCommandException(String message) {
        super(message);
    }
}
