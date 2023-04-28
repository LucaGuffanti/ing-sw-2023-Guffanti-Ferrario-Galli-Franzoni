package it.polimi.ingsw.client.controller.commandHandlers;

import it.polimi.ingsw.client.controller.ClientPhasesEnum;
import it.polimi.ingsw.client.controller.exceptions.BadlyFormattedParametersException;
import it.polimi.ingsw.client.controller.exceptions.CommandNotAvailableInThisPhaseException;
import it.polimi.ingsw.client.controller.stateController.ClientState;
import it.polimi.ingsw.client.view.cli.Cli;
import it.polimi.ingsw.client.view.cli.cliviews.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.function.Consumer;

/**
 * A showViewCommandHandler parses a /show command and performs the modification of the view.
 * If the parameter(s) doesn't make sense an exception is raised (accepted commands are found in the map
 * in the class)<br>
 * Example <br>
 * /show board -> is perfomed<br>
 * /show dfoiaerjgse -> throws BadlyFormattedParameterException
 * @author Luca Guffanti
 */
public class ShowViewCommandHandler extends CliCommandHandler{

    public final static String commandLabel = "/show";
    public final static String commandDescription = "Change View Command\n\n" +
            "Usage: \n"+
            "/show board   : shows the board\n"+
            "/show pg      : shows your personal goal card\n"+
            "/show cg      : shows the common goal card\n"+
            "/show shelf   : shows your shelf\n"+
            "/show shelves : shows all the shelves of the players in the game\n"+
            "/show chat    : shows the chat";

    private final static HashMap<String, CliView> paramaterToAction = new HashMap<>();
    private final static HashMap<String, List<ClientPhasesEnum>> availableStatus = new HashMap<>();

    public ShowViewCommandHandler(Cli cli) {
        super(cli);
    }

    static {
        // ALL THE POSSIBLE TRANSITIONS IN THE VIEW GO HERE
        paramaterToAction.put("board", new BoardView());
        paramaterToAction.put("pg", new PersonalGoalCardView());
        paramaterToAction.put("cg", new CommonGoalCardsView());
        paramaterToAction.put("shelf", new ShelfView());
        paramaterToAction.put("shelves", new MultipleShelvesView());
        paramaterToAction.put("chat", new ChatView());

        availableStatus.put("board", List.of(ClientPhasesEnum.WAITING_FOR_TURN, ClientPhasesEnum.PICK_FORM_BOARD, ClientPhasesEnum.SELECT_COLUMN, ClientPhasesEnum.FINAL_RESULTS_SHOW));
        availableStatus.put("pg", List.of(ClientPhasesEnum.WAITING_FOR_TURN, ClientPhasesEnum.PICK_FORM_BOARD, ClientPhasesEnum.SELECT_COLUMN, ClientPhasesEnum.FINAL_RESULTS_SHOW));
        availableStatus.put("cg", List.of(ClientPhasesEnum.WAITING_FOR_TURN, ClientPhasesEnum.PICK_FORM_BOARD, ClientPhasesEnum.SELECT_COLUMN, ClientPhasesEnum.FINAL_RESULTS_SHOW));
        availableStatus.put("shelf", List.of(ClientPhasesEnum.WAITING_FOR_TURN, ClientPhasesEnum.PICK_FORM_BOARD, ClientPhasesEnum.SELECT_COLUMN, ClientPhasesEnum.FINAL_RESULTS_SHOW));
        availableStatus.put("shelves", List.of(ClientPhasesEnum.WAITING_FOR_TURN, ClientPhasesEnum.PICK_FORM_BOARD, ClientPhasesEnum.SELECT_COLUMN, ClientPhasesEnum.FINAL_RESULTS_SHOW));
        availableStatus.put("chat", List.of(ClientPhasesEnum.LOBBY, ClientPhasesEnum.WAITING_FOR_TURN, ClientPhasesEnum.PICK_FORM_BOARD, ClientPhasesEnum.SELECT_COLUMN, ClientPhasesEnum.FINAL_RESULTS_SHOW));

    }
    @Override
    public void execute(String commandInput, ClientState state) throws BadlyFormattedParametersException, CommandNotAvailableInThisPhaseException {
        String[] parameters = commandInput.split(" ");
        String parameter = parameters[1];
        if (!checkParameters(Arrays.stream(parameters).toList())) {
            throw new BadlyFormattedParametersException();
        }

        if(!availableStatus.get(parameter).contains(getCli().getStateContainer().getCurrentState().getCurrentPhase())) {
            throw new CommandNotAvailableInThisPhaseException();
        }

        // check to see if
        CliView view = paramaterToAction.get(parameter);
        getCli().renderCliView(view);
    }

    @Override
    protected boolean checkParameters(List<String> parameters) {
        return parameters.size() == 2 && paramaterToAction.containsKey(parameters.get(1));
    }

    @Override
    public String getCommandDescription() {
        return commandDescription;
    }


}
