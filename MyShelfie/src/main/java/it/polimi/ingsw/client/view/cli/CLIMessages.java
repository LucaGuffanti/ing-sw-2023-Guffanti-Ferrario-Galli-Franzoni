package it.polimi.ingsw.client.view.cli;

import it.polimi.ingsw.client.controller.commandHandlers.JoinGameCommandHandler;
import it.polimi.ingsw.client.controller.commandHandlers.PlayersNumberCommandHandler;

/**
 * This class contains messages used to inform the player during the game (for cli).
 * @author Luca Guffanti
 */
public class CLIMessages {
    public static final String GREETING = """
            Hello, dear Player, welcome to our awesome game!
            
            Feel free to have a look around: try commands (there's not much in the terminal).
            If you need help, simply write "/help" and you'll get all the information you need!
            When you are ready, write:
                  
                    /login USERNAME
            
            with USERNAME being the name you want. You'll know if your name is ok... Have fun!""";

    public static final String NOT_JOINED = """
            You're almost there.
            To join a game write\n\n\t\t""" + JoinGameCommandHandler.commandLabel +"""
            
            \nIf the game hasn't been created yet, write\n\n\t\t""" + PlayersNumberCommandHandler.commandLabel + """ 
             numberOfPlayers
            """;
    public static final String END_OF_TURN = """
            THE TURN ENDED!
            """;
}
