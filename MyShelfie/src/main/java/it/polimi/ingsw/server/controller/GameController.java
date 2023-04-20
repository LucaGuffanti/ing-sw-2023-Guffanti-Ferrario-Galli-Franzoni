package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.network.utils.Logger;
import it.polimi.ingsw.server.controller.turn.PutInShelfPhase;
import it.polimi.ingsw.server.controller.turn.PickFromBoardPhase;
import it.polimi.ingsw.server.controller.turn.TurnPhase;
import it.polimi.ingsw.server.controller.utils.GameObjectConverter;
import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.GameCheckout;
import it.polimi.ingsw.server.model.cards.goalCards.CommonGoalCard;
import it.polimi.ingsw.server.model.cards.goalCards.SimplifiedCommonGoalCard;
import it.polimi.ingsw.server.model.cells.Coordinates;
import it.polimi.ingsw.server.model.player.Player;
import it.polimi.ingsw.server.model.utils.exceptions.MaxPlayersException;
import it.polimi.ingsw.network.NetworkHandler;
import it.polimi.ingsw.network.messages.*;
import it.polimi.ingsw.network.messages.enums.ResponseResultType;
import it.polimi.ingsw.network.utils.ResponsesDescriptions;

import java.util.ArrayList;
import java.util.Collections;

/**
 * The GameController is the controller of the game: it keeps an instance of the game, interacting with it when messages are received
 * and receiving notifications when some situations are met.
 * @author Luca Guffanti
 */
public class GameController {
    public static final String NAME = "CONTROLLER";
    /**
     * The game instance
     */
    private Game game;
    /**
     * The turnPhase in which the turn is.
     */
    private TurnPhase turnPhase;
    /**
     * The list of players that log into the game. It will be shuffled when the game starts
     */
    private ArrayList<String> orderedPlayersNicks;
    /**
     * Index at which the list of players contains the player that's active for a given turn.
     */
    private int activePlayerIndex;
    /**
     * Whether a player has completed the first common goal in his turn
     */
    private boolean firstCommonGoalCompletedByActivePlayer;
    /**
     * Whether a player has completed the second common goal in his turn
     */
    private boolean secondCommonGoalCompletedByActivePlayer;
    /**
     * Whether a player has completed its board in his turn
     */
    private boolean completedShelf;
    /**
     * The coordinates chosen by a player during the turn
     */
    private ArrayList<Coordinates> chosenCoords;
    /**
     *  The status of the game.
     */
    private GameStatusEnum gameStatus;

    /**
     * The server-side network networkHandler
     */
    private final NetworkHandler networkHandler;

    /**
     * When a Game Controller is created, it gets passed a {@link NetworkHandler}.
     * The game controller is created
     */
    public GameController(NetworkHandler networkHandler) {
        this.networkHandler = networkHandler;
    }

    /**
     * This method manages the join of a new player. A player's request to join a game can
     * result in two possible outcomes
     * <ol>
     *     <li>The game is not full and the player is granted access with a notification</li>
     *     <li>The game is full and the game prohibits the access of the player, who is notified. The newly logged
     *     in player receives a list of the already joined players, and already logged in players receive
     *     a message containing the new player nickname</li>
     * </ol>
     *
     * @param nickname the nickname of the player that is trying to join
     */
    public synchronized void onPlayerJoin(String nickname){

        /*
            The game tries to accept the player only if the game hasn't already started.
            If the game has already started the client is notified with an adeguate message and the method
            ends.
         */
        if (!gameStatus.equals(GameStatusEnum.ACCEPTING_PLAYERS)) {
            Logger.controllerWarning(nickname + " tried to join the game but it's already started");

            networkHandler.sendToPlayer(
                    nickname,
                    new AccessResultMessage(
                            NetworkHandler.HOSTNAME,
                            ResponsesDescriptions.JOIN_FAILURE_ALREADY_STARTED,
                            ResponseResultType.FAILURE,
                            null,
                            nickname
                    )
            );
            return;
        }

        String responseDescription;
        ResponseResultType resultType;
        ArrayList<String> players = new ArrayList<>(game.getPlayers().stream().map(Player::getNickname).toList());
        try {
            game.addPlayer(new Player(nickname));
            Logger.controllerInfo(nickname+ " joined the game ("+game.getPlayers().size()+"/"+game.getGameInfo().getNPlayers()+")");
            responseDescription = ResponsesDescriptions.JOIN_SUCCESS;
            resultType = ResponseResultType.SUCCESS;

            /*
                Broadcast that the new player joined if the join was successful
             */
            networkHandler.broadcastToAllButSender(
                    nickname,
                    new NewPlayerMessage(
                            NetworkHandler.HOSTNAME,
                            nickname + ResponsesDescriptions.NEW_PLAYER_JOINED,
                            nickname
                    )
            );

        } catch (MaxPlayersException e) {
            /*
                The game throws a MaxPlayersException if the
             */
            Logger.controllerWarning(nickname + " tried to join but the game is full");
            responseDescription = ResponsesDescriptions.JOIN_FAILURE_MAX_PLAYERS;
            resultType = ResponseResultType.FAILURE;
            players = null;
        }

        /*
            Send the result of the join to the player that's trying to join the game
        */
        networkHandler.sendToPlayer(
                nickname,
                new AccessResultMessage(
                        NetworkHandler.HOSTNAME,
                        responseDescription,
                        resultType,
                        players,
                        nickname
                )
        );
        if (gameStatus.equals(GameStatusEnum.STARTED)) {
            startGame();
        }
    }

    /**
     * This method executes all the operations necessary to instantiate a new game:
     * <ol>
     *     <li>The game is created with info about the host and the number of player</li>
     *     <li>The game status attribute is set to ACCEPTING_PLAYERS</li>
     * </ol>
     * At the end of the execution of the method, the game is created but not yet initialized:
     * the initialization occurs when the selected number of players is reached.
     *
     * @param adminName the name of the player who's creating the game
     * @param selectedPlayers the number of players selected by the admin
     * @param gameId the id of the game assigned by the system
     */
    public synchronized void createGame(String adminName, int selectedPlayers, int gameId) {
        // TODO ADD CHECK FOR WHEN A GAME IS STARTED AND A CREATE GAME IS REQUESTED
        try {
            game = new Game(new Player(adminName), selectedPlayers, gameId, this);
        } catch (Exception e){
            e.printStackTrace();
        }
        gameStatus = GameStatusEnum.ACCEPTING_PLAYERS;
        // the player also automatically joins the game at its creation, so the client is notified.
        Logger.controllerInfo(adminName+ " created a new game for "+ selectedPlayers + " players (1/"+game.getGameInfo().getNPlayers()+")");
        networkHandler.sendToPlayer(
                adminName,
                new AccessResultMessage(
                        NetworkHandler.HOSTNAME,
                        ResponsesDescriptions.JOIN_SUCCESS,
                        ResponseResultType.SUCCESS,
                        new ArrayList<>(), // the list is empty as there isn't any other player in the game
                        adminName
                )
        );
    }

    /**
     * This method is called when the selected number of logged in players is reached.
     *
     * <ol>
     *     <li>The game is initialized (via {@code game.initGame()} in {@link Game})</li>
     *     <li>The list of players is shuffled and activeIndex is set to -1</li>
     *     <li>The status of the game is set to STARTED</li>
     *     <li>Every player is notified with a {@link GameStartMessage}</li>
     * </ol>
     * and, at last, {@code beginTurn()} is called (notice that activePlayerIndex is set to <b>-1</b> so
     * that the called method can operate correctly)
     */
    public synchronized void startGame() {
        // the game is initialized
        game.initGame();
        // the players are shuffled
        orderedPlayersNicks = new ArrayList<>(game.getPlayers().stream().map(Player::getNickname).toList());
        Collections.shuffle(orderedPlayersNicks);
        // the index is set to -1
        activePlayerIndex = -1;

        gameStatus = GameStatusEnum.STARTED;

        ArrayList<SimplifiedCommonGoalCard> simplifiedCommonGoalCards = new ArrayList<>(game.getGameInfo().getSelectedCommonGoals().stream()
                        .map(GameObjectConverter::simplifyCommonGoalIntoCard).toList());
        ArrayList<String> personalGoals = new ArrayList<>(game.getGameInfo().getPersonalGoals().stream().
                map(GameObjectConverter::simplifyPersonalGoalIntoString).toList());

        Logger.controllerInfo("the game started");
        Logger.controllerInfo("Players order: "+ orderedPlayersNicks.toString());

        networkHandler.broadcastToAll(
                new GameStartMessage(
                    NetworkHandler.HOSTNAME,
                    ResponsesDescriptions.GAME_STARTED,
                    GameObjectConverter.simplifyBoardIntoMatrix(game.getBoard()),
                    personalGoals,
                    orderedPlayersNicks,
                    simplifiedCommonGoalCards
                )
        );

        beginTurn();

    }

    /**
     * This method advances the index referring to the active player (it skips a player if the player is
     * disconnected), produces the
     * message describing the beginning of a new turn that will be broadcast to every player and
     * sets the turn phase to {@link PickFromBoardPhase}, starting to accept game messages from the players
     */
    public synchronized void beginTurn() {
        activePlayerIndex = (activePlayerIndex+1)%orderedPlayersNicks.size();

        Logger.controllerInfo("New turn: " + orderedPlayersNicks.get(activePlayerIndex));
        networkHandler.broadcastToAll(
                new BeginningOfTurnMessage(
                        NetworkHandler.HOSTNAME,
                        ResponsesDescriptions.NEW_TURN,
                        orderedPlayersNicks.get(activePlayerIndex)
                )
        );
        turnPhase = new PickFromBoardPhase(game, this);
    }

    /**
     * This method is called by {@link PutInShelfPhase} and executes the operations
     * related to the ending of a single turn:
     * <ol>
     *     <li>deletes the coordinates selection made by the active player</li>
     *     <li>
     *         calculates the turnwise points
     *         <ol>
     *             <li>if the player completes any of the two common goals, the method updates
     *             the corresponding attributes in the state of the controller</li>
     *             <li>if the player completes the shelf, the method updates the corresponding
     *             attribute in the state of the controller and updates the game status</li>
     *         </ol>
     *     </li>
     *     <li>if required, the board gets be refilled</li>
     *     <li>it builds the message describing the end of the turn
     *     <li>if the game has to finish, it sets the state to ended and calls the endGame() method</li>
     *     that will be broadcast to every player</li>
     * </ol>
     */
    public synchronized void endTurn() {

        Logger.controllerInfo("the current turn ended");

        String messageDescription = "";

        chosenCoords = null;
        // points are calculated
        game.awardTurnWisePoints(game.getPlayerByNick(orderedPlayersNicks.get(activePlayerIndex)));
        // the game board is refilled
        if (game.getBoard().shouldBeRefilled()) {
            Logger.controllerInfo("the board won't be refilled");
            game.getBoard().refillBoard(game.getSack());
            messageDescription += ResponsesDescriptions.END_OF_TURN_REFILL_BOARD + "\n";
        }

        if (completedShelf) {
            Logger.controllerInfo("the player completed the shelf");
            messageDescription += ResponsesDescriptions.END_OF_TURN_COMPLETED_SHELF + "\n";
        }
        if (firstCommonGoalCompletedByActivePlayer) {
            Logger.controllerInfo("the player completed the first common goal");
            messageDescription += ResponsesDescriptions.END_OF_TURN_CG1_COMPLETED + "\n";
        }
        if (secondCommonGoalCompletedByActivePlayer) {
            Logger.controllerInfo("the player completed the second common goal");
            messageDescription += ResponsesDescriptions.END_OF_TURN_CG2_COMPLETED + "\n";
        }
        Player currentPlayer = game.getPlayerByNick(orderedPlayersNicks.get(activePlayerIndex));
        ArrayList<SimplifiedCommonGoalCard> simplifiedCommonGoalCards = new ArrayList<>(game
                .getGameInfo()
                .getSelectedCommonGoals()
                .stream()
                .map(GameObjectConverter::simplifyCommonGoalIntoCard)
                .toList());

        // the message is built and sent
        networkHandler.broadcastToAll(
                new EndOfTurnMessage(
                        NetworkHandler.HOSTNAME,
                        messageDescription,
                        GameObjectConverter.simplifyBoardIntoMatrix(game.getBoard()),
                        GameObjectConverter.simplifyShelfIntoMatrix(currentPlayer.getShelf()),
                        simplifiedCommonGoalCards,
                        firstCommonGoalCompletedByActivePlayer,
                        secondCommonGoalCompletedByActivePlayer,
                        completedShelf
                )
        );

        firstCommonGoalCompletedByActivePlayer = false;
        secondCommonGoalCompletedByActivePlayer = false;
        completedShelf = false;

        if (gameStatus.equals(GameStatusEnum.FINAL_TURNS) && activePlayerIndex == 3) {
            gameStatus = GameStatusEnum.ENDED;
            endGame();
        } else {
            beginTurn();
        }
    }

    /**
     * The final points are calculated and an {@link EndOfGameMessage} is built and sent to every connected
     * player.
     */
    public synchronized void endGame() {
        Logger.controllerInfo("The game ended");
        GameCheckout gameCheckout = game.endGame(orderedPlayersNicks);

        networkHandler.broadcastToAll(
                new EndOfGameMessage(
                        NetworkHandler.HOSTNAME,
                        ResponsesDescriptions.GAME_ENDED,
                        gameCheckout.getNickToPoints(),
                        gameCheckout.getWinner()
                )
        );

    }

    /**
     * This method is called as the server realizes that a player has lost connection.
     * If the disconnected player is the player that's should be active during the turn, the turn is instantly
     * ended without calculating the points, and the next turn starts.
     *
     * @param nickname the player who lost connection
     */
    public synchronized void onPlayerDisconnection(String nickname) {
        //TODO IMPLEMENT
    }

    /**
     * This method manages the reconnection of a player who previously lost
     * connection
     * @param nickname the nickname of the player who is trying to reconnect
     */
    public synchronized void onPlayerReconnection(String nickname) {
        //TODO IMPLEMENT
    }

    /**
     * This method is called when, during the awarding of turnwise points, the
     * completion of a {@link CommonGoalCard} is registered.
     * <p>
     * Based on goalNumber, the corresponding attribute is set as true.
     * The attribute is used later when the message summing up the turn is
     * built
     * @param goalNumber either 1 or 2, is the ordinal number of the completed goal card
     */
    public synchronized void onCommonGoalCompletion(int goalNumber) {
        if (goalNumber == 1)
            firstCommonGoalCompletedByActivePlayer = true;
        else if (goalNumber == 2)
            secondCommonGoalCompletedByActivePlayer = true;
    }

    /**
     *  This method is called when, during the calculation of the turn-wise points, a completed shelf is detected.
     *  As a shelf is completed the game enters its last stage, FINAL_TURNS, and the corresponding attribute is set as
     *  true.
     */
    public synchronized void onShelfCompletion() {
        completedShelf = true;
        gameStatus = GameStatusEnum.FINAL_TURNS;
    }
    /**
     * This method sends a message (whose type is {@link PickFromBoardMessage} or {@link SelectColumnMessage}) to the
     * phase.
     * @param message the message submitted by the network networkHandler
     */
    public synchronized void onGameMessageReceived(Message message) {
        turnPhase.manageIncomingMessage(message);
    }

    // GETTERS AND SETTERS
    public synchronized Game getGame() {
        return game;
    }

    public synchronized TurnPhase getTurnPhase() {
        return turnPhase;
    }

    public synchronized void setTurnPhase(TurnPhase turnPhase) {
        this.turnPhase = turnPhase;
    }

    public synchronized void setActivePlayerIndex(int activePlayerIndex) {
        this.activePlayerIndex = activePlayerIndex;
    }

    public synchronized ArrayList<String> getOrderedPlayersNicks() {
        return orderedPlayersNicks;
    }

    public synchronized int getActivePlayerIndex() {
        return activePlayerIndex;
    }

    public synchronized GameStatusEnum getGameStatus() {
        return gameStatus;
    }

    public synchronized void setGameStatus(GameStatusEnum gameStatus) {
        this.gameStatus = gameStatus;
    }

    public synchronized void setChosenCoords(ArrayList<Coordinates> chosenCoords) {
        this.chosenCoords = chosenCoords;
    }

    public synchronized ArrayList<Coordinates> getChosenCoords() {
        return chosenCoords;
    }

    public synchronized NetworkHandler getNetworkHandler() {
        return networkHandler;
    }
}
