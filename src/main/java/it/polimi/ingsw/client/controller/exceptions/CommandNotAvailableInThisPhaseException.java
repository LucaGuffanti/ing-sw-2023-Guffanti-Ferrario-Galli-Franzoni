package it.polimi.ingsw.client.controller.exceptions;

import it.polimi.ingsw.client.controller.ClientPhasesEnum;

/**
 * This exception is thrown when the requested command is not available in a particular phase
 * @see ClientPhasesEnum
 */
public class CommandNotAvailableInThisPhaseException extends CliCommandException{
    public CommandNotAvailableInThisPhaseException() {
        super("The requested command is not available in this game phase.");
    }
}
