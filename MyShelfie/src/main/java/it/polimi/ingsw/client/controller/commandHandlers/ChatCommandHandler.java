package it.polimi.ingsw.client.controller.commandHandlers;

import it.polimi.ingsw.client.controller.ClientPhasesEnum;
import it.polimi.ingsw.client.controller.exceptions.BadlyFormattedParametersException;
import it.polimi.ingsw.client.controller.exceptions.CommandNotAvailableInThisPhaseException;
import it.polimi.ingsw.client.controller.messageHandling.messageHandlers.LoginMessageHandler;
import it.polimi.ingsw.client.controller.stateController.ClientState;
import it.polimi.ingsw.client.view.cli.Cli;
import it.polimi.ingsw.client.view.cli.Printer;
import it.polimi.ingsw.network.messages.ChatMessage;
import it.polimi.ingsw.network.messages.LoginRequestMessage;
import it.polimi.ingsw.network.utils.Logger;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * The handler for the chat command
 * @author Luca Guffanti
 */
public class ChatCommandHandler extends CliCommandHandler{
    private final HashSet<ClientPhasesEnum> availablePhases = new HashSet<>(Arrays.asList(
            ClientPhasesEnum.LOBBY,
            ClientPhasesEnum.DECIDING_FOR_RELOAD,
            ClientPhasesEnum.WAITING_FOR_TURN,
            ClientPhasesEnum.PICK_FORM_BOARD,
            ClientPhasesEnum.SELECT_COLUMN,
            ClientPhasesEnum.FINAL_RESULTS_SHOW
    ));


    public final static String commandLabel = "/send";
    public final static String commandDescription = "Broadcast a message or send private messages.\n\n" +
            "Usage:\n"+
            "/send message_you_want_to_send                                 to broadcast a message\n" +
            "/send message_you_want_to_send --to user1 [user2 user3 user4]  to send a private message\n\n" +
            "Please remember that it's not permitted to send a message to a non existent user and to send\n"
            +"a message to yourself. Be sure not to send";

    public final static String receiversTag = "--to";
    private ClientState clientState = null;
    public ChatCommandHandler(Cli cli) {
        super(cli);
    }


    @Override
    public void execute(String commandInput, ClientState state) throws BadlyFormattedParametersException, CommandNotAvailableInThisPhaseException {
        this.clientState = state;
        List<String> parameters = super.splitAndTrimInput(commandInput);

        if(!checkParameters(parameters)){
            throw new BadlyFormattedParametersException();
        }

        if(!super.checkAvailability(availablePhases, state)){
            throw new CommandNotAvailableInThisPhaseException();
        }

        StringBuilder builder = new StringBuilder();
        int listIndex = 0;
        for(String s : parameters) {
            if(!s.equals(ChatCommandHandler.receiversTag)) {
                builder.append(s+" ");
                listIndex++;
            } else {
                break;
            }
        }
        String body = builder.toString().trim();

        List<String> receivers = new ArrayList<>();

        if (listIndex != parameters.size()) {
            for (listIndex = listIndex+1; listIndex < parameters.size(); listIndex++) {
                receivers.add(parameters.get(listIndex));
            }
        }

        ChatMessage msg = new ChatMessage(body, clientState.getUsername(), LocalDateTime.now(ZoneId.systemDefault()), receivers);
        super.getCli().dispatchMessageToNetwork(msg);
    }

    /**
     * This method checks whether the parameters for the chat command are correct:
     *  the command must be at least two tokens long (i.e. /send hello) and all the specified
     *  private recipients of the message must be present in the game.
     */
    @Override
    protected boolean checkParameters(List<String> parameters) {
        if (parameters.size()<1) {
            return false;
        } else {
            if (parameters.contains(receiversTag)) {
                int tagIndex = parameters.indexOf(receiversTag);
                for (int i = tagIndex+1; i < parameters.size(); i++) {
                    if (!clientState.getOrderedPlayersNames().contains(parameters.get(i))) {
                        return false;
                    }
                    if (parameters.get(i).equals(clientState.getUsername())) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public static String getCommandLabel() {
        return commandLabel;
    }

    public String getCommandDescription() {
        return commandDescription;
    }

}
