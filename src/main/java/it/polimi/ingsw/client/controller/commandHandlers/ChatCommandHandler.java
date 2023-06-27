package it.polimi.ingsw.client.controller.commandHandlers;

import it.polimi.ingsw.client.controller.ClientPhasesEnum;
import it.polimi.ingsw.client.controller.exceptions.BadlyFormattedParametersException;
import it.polimi.ingsw.client.controller.exceptions.CommandNotAvailableInThisPhaseException;
import it.polimi.ingsw.client.controller.stateController.ClientState;
import it.polimi.ingsw.client.view.cli.Cli;
import it.polimi.ingsw.network.messages.ChatMessage;

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
    /**
     * Game phases in which the command is available
     */
    private final HashSet<ClientPhasesEnum> availablePhases = new HashSet<>(Arrays.asList(
            ClientPhasesEnum.LOBBY,
            ClientPhasesEnum.DECIDING_FOR_RELOAD,
            ClientPhasesEnum.WAITING_FOR_TURN,
            ClientPhasesEnum.PICK_FORM_BOARD,
            ClientPhasesEnum.SELECT_COLUMN,
            ClientPhasesEnum.FINAL_RESULTS_SHOW
    ));

    /**
     * The label of the command: the string that should be inserted to invoke the command
     */
    public final static String commandLabel = "/send";
    /**
     * The description of the command
     */
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

    /**
     * After the correct checks are made, this method generates a chat message and submits it to the server
     * @param commandInput The user's input
     * @param state the state of the client
     * @throws BadlyFormattedParametersException thrown if parameters are badly formatted
     * @throws CommandNotAvailableInThisPhaseException thrown if the command is not available in a given phase
     */
    @Override
    public void execute(String commandInput, ClientState state) throws BadlyFormattedParametersException, CommandNotAvailableInThisPhaseException {
        this.clientState = state;
        List<String> parameters = super.splitAndTrimInput(commandInput);

        if(!super.checkAvailability(availablePhases, state)){
            throw new CommandNotAvailableInThisPhaseException();
        }
        if(!checkParameters(parameters)){
            throw new BadlyFormattedParametersException();
        }


        StringBuilder builder = new StringBuilder();
        int listIndex = 0;
        for(String s : parameters) {
            if(!s.equals(ChatCommandHandler.receiversTag)) {
                builder.append(s).append(" ");
                listIndex++;
            } else {
                break;
            }
        }
        String body = builder.toString().trim();

        List<String> receivers = new ArrayList<>();

        StringBuilder rec = new StringBuilder();
        if (listIndex != parameters.size()) {
            for (listIndex = listIndex+1; listIndex < parameters.size(); listIndex++) {
                rec.append(parameters.get(listIndex)).append(" ");
            }
            String receiver = rec.toString().trim();
            receivers.add(receiver);
        } else {

            receivers = new ArrayList<>(clientState.getOrderedPlayersNames());
            receivers.remove(clientState.getUsername());
        }



        System.out.println("sending message to " + receivers);

        ChatMessage msg = new ChatMessage(body, clientState.getUsername(), LocalDateTime.now(ZoneId.systemDefault()), receivers);
        super.getCli().dispatchMessageToNetwork(msg);
    }

    /**
     * This method checks whether the parameters for the chat command are correct:
     *  the command must be at least two tokens long (i.e. /send hello) and the recipient must be a logged in
     *  player and must not be the sending player
     * @param parameters the parameters of the command
     */
    @Override
    protected boolean checkParameters(List<String> parameters) {
        StringBuilder recipient = new StringBuilder();
        if (parameters.size()<1 || parameters.get(0).equals("")) {
            return false;
        } else {
            if (parameters.contains(receiversTag)) {
                int tagIndex = parameters.indexOf(receiversTag);
                for (int i = tagIndex+1; i < parameters.size(); i++) {
                    recipient.append(parameters.get(i)).append(" ");
                }
                String rec = recipient.toString().trim();
                if (rec.equals(clientState.getUsername()) || !clientState.getOrderedPlayersNames().contains(rec)) {
                    return false;
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
