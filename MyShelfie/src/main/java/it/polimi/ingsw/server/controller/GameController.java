package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.client.view.cli.Printer;
import it.polimi.ingsw.network.ServerNetworkHandler;
import it.polimi.ingsw.network.messages.*;
import it.polimi.ingsw.network.messages.enums.ResponseResultType;
import it.polimi.ingsw.network.utils.Logger;
import it.polimi.ingsw.network.utils.ResponsesDescriptions;
import it.polimi.ingsw.server.controller.save.SaveFileData;
import it.polimi.ingsw.server.controller.save.SaveFileManager;
import it.polimi.ingsw.server.controller.turn.PickFromBoardPhase;
import it.polimi.ingsw.server.controller.turn.PutInShelfPhase;
import it.polimi.ingsw.server.controller.turn.TurnPhase;
import it.polimi.ingsw.server.controller.utils.GameObjectConverter;
import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.GameCheckout;
import it.polimi.ingsw.server.model.cards.goalCards.CommonGoalCard;
import it.polimi.ingsw.server.model.cards.goalCards.SimplifiedCommonGoalCard;
import it.polimi.ingsw.server.model.cells.Coordinates;
import it.polimi.ingsw.server.model.player.Player;
import it.polimi.ingsw.server.model.player.SimplifiedPlayer;
import it.polimi.ingsw.server.model.utils.exceptions.MaxPlayersException;

import java.io.File;
import java.util.*;

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
    private final ServerNetworkHandler serverNetworkHandler;

    /**
     * The save file data object, which is populated as the new program restarts.
     */
    private SaveFileData saveFileData = null;

    /**
     * When a Game Controller is created, it gets passed a {@link ServerNetworkHandler}.
     * The game controller is created
     */
    public GameController(ServerNetworkHandler serverNetworkHandler) {
        this.serverNetworkHandler = serverNetworkHandler;
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
            If the game has already started the client is notified with an adequate message and the method
            ends.
         */
        if (!gameStatus.equals(GameStatusEnum.ACCEPTING_PLAYERS)) {
            Logger.controllerWarning(nickname + " tried to join the game but it's already started");

            serverNetworkHandler.sendToPlayer(
                    nickname,
                    new AccessResultMessage(
                            ServerNetworkHandler.HOSTNAME,
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
        ArrayList<String> players;
        try {
            game.addPlayer(new Player(nickname));
            players = new ArrayList<>(game.getPlayers().stream().map(Player::getNickname).toList());
            Logger.controllerInfo(nickname+ " joined the game ("+game.getPlayers().size()+"/"+game.getGameInfo().getNPlayers()+")");
            responseDescription = ResponsesDescriptions.JOIN_SUCCESS;
            resultType = ResponseResultType.SUCCESS;

            /*
                Broadcast that the new player joined if the join was successful
             */
            for (String player: players) {
                if(!player.equals(nickname)) {

                    serverNetworkHandler.sendToPlayer(
                            player,
                            new NewPlayerMessage(
                                    ServerNetworkHandler.HOSTNAME,
                                    nickname + ResponsesDescriptions.NEW_PLAYER_JOINED,
                                    nickname
                            )
                    );
                }
            }

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
        serverNetworkHandler.sendToPlayer(
                nickname,
                new AccessResultMessage(
                        ServerNetworkHandler.HOSTNAME,
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
        try {
            game = new Game(new Player(adminName), selectedPlayers, gameId, this);
        } catch (Exception e){
            e.printStackTrace();
        }
        gameStatus = GameStatusEnum.ACCEPTING_PLAYERS;
        // the player also automatically joins the game at its creation, so the client is notified.
        Logger.controllerInfo(adminName+ " created a new game for "+ selectedPlayers + " players (1/"+game.getGameInfo().getNPlayers()+")");
        ArrayList<String> tmp = new ArrayList<>();
        tmp.add(adminName);
        serverNetworkHandler.sendToPlayer(
                adminName,
                new AccessResultMessage(
                        ServerNetworkHandler.HOSTNAME,
                        ResponsesDescriptions.JOIN_SUCCESS,
                        ResponseResultType.SUCCESS,
                        tmp, // the list is empty as there isn't any other player in the game
                        adminName
                )
        );
    }

    /**
     * This method is called when the selected number of logged in players is reached. <br>
     *     If there exists a saved game with the same number of players, with all the players having the same
     *     nickname as the players from the previously saved game, the admin has to decide whether he wants
     *     to reload the previously ended game.
     */
    public synchronized void startGame() {

        File saveFile = new File("src/main/assets/savedGames/savefile.json");

        boolean existingFile = saveFile.exists() && !saveFile.isDirectory();
        if (existingFile) {
            Logger.controllerInfo("A SAVED GAME EXISTS");
            managePossibleReload(saveFile);
        } else {
            newGameNoReload();
        }
    }

    /**
     * This method tries to load the game state from the save file, building the
     * saveFileData object. if the loading fails a new game is created from scratch.
     * @param saveFile the file containing the saved game state
     */
    private void managePossibleReload(File saveFile) {
        try {
            saveFileData = SaveFileManager.loadGameState(saveFile);
        } catch (Exception e) {
            Logger.controllerError("Couldn't find file. Starting a game from scratch");
            newGameNoReload();
            return;
        }

        assert saveFileData != null;

        int loadedNumOfPlayers = saveFileData.getGameInfo().getnPlayers();
        List<String> loadedPlayerNicks = Arrays.stream(saveFileData.getPlayers()).map(SimplifiedPlayer::getName).toList();

        int actualNumOfPlayers = game.getGameInfo().getNPlayers();
        String actualAdmin = game.getGameInfo().getAdmin();
        List<String> joinedPlayerNicks = game.getPlayers().stream().map(Player::getNickname).toList();

        Set<String> loaded = new HashSet<>(loadedPlayerNicks);
        Set<String> present = new HashSet<>(joinedPlayerNicks);

        if (loadedNumOfPlayers == actualNumOfPlayers && loaded.equals(present)) {
            this.gameStatus = GameStatusEnum.FOUND_SAVE_FILE;
            serverNetworkHandler.sendToPlayer(actualAdmin, new FoundSavedGameMessage(
                  ServerNetworkHandler.HOSTNAME,
                  ResponsesDescriptions.FOUND_COMPATIBLE_SAVED_GAME
            ));
        } else {
            Logger.controllerInfo("Mismatch in saved players and joined players found: starting game from the beginning");
            newGameNoReload();
        }
    }

    /**
     * This method reloads an existing game, by correctly setting useful data in the controller (
     * the ordered list of players and the index of the last playing player)
     */
    public void reloadExistingGame() {
        Logger.controllerInfo("RESUMING GAME");
        this.orderedPlayersNicks = new ArrayList<>(saveFileData.getOrderedPlayers());
        this.activePlayerIndex = saveFileData.getActivePlayerIndex();

        Logger.controllerInfo("The players will play in the following order");
        Logger.controllerInfo(orderedPlayersNicks.toString());
        Logger.controllerInfo(orderedPlayersNicks.get((activePlayerIndex+1)%game.getGameInfo().getNPlayers())+" will start playing");
        game.loadGame(saveFileData);

        gameStatus = GameStatusEnum.STARTED;
        Logger.controllerInfo("GAME IS READY");


        ArrayList<SimplifiedCommonGoalCard> common = new ArrayList<>(game.getGameInfo().getSelectedCommonGoals().stream()
                .map(s->GameObjectConverter.fromCommonGoalToSimplifiedCommonGoal(s, game, game.getGameInfo().getSelectedCommonGoals().indexOf(s))).toList());

        ArrayList<String> personalGoals = new ArrayList<>(orderedPlayersNicks.parallelStream()
                .map(o -> game.getPlayerByNick(o))
                .map(Player::getGoal)
                .map(GameObjectConverter::fromPersonalGoalToString)
                .toList());

        serverNetworkHandler.broadcastToAll(
                new GameStartMessage(
                        ServerNetworkHandler.HOSTNAME,
                        ResponsesDescriptions.GAME_STARTED,
                        GameObjectConverter.fromBoardToMatrix(game.getBoard()),
                        GameObjectConverter.fromShelvesToMatrices(game.getPlayersShelves()),
                        personalGoals,
                        orderedPlayersNicks,
                        common,
                        game.getGameInfo().getFirstToCompleteTheShelf() //as the game starts, no one is the first to complete the shelf
                )
        );

        beginTurn();
    }

    /**
     * <ol>
     *  <li>The game is initialized (via {@code game.initGame()} in {@link Game})</li>
     *  <li>The list of players is shuffled and activeIndex is set to -1</li>
     *  <li>The status of the game is set to STARTED</li>
     *  <li>Every player is notified with a {@link GameStartMessage}</li>
     * </ol>
     *   and, at last, {@code beginTurn()} is called (notice that activePlayerIndex is set to <b>-1</b> so
     *   that the called method can operate correctly)
     **/
    public synchronized void newGameNoReload() {
        // the game is initialized
        game.initGame();
        // the players are shuffled
        orderedPlayersNicks = new ArrayList<>(game.getPlayers().stream().map(Player::getNickname).toList());
        Collections.shuffle(orderedPlayersNicks);
        // the index is set to -1
        activePlayerIndex = -1;

        gameStatus = GameStatusEnum.STARTED;

        ArrayList<SimplifiedCommonGoalCard> common = new ArrayList<>(game.getGameInfo().getSelectedCommonGoals().stream()
                .map(s->GameObjectConverter.fromCommonGoalToSimplifiedCommonGoal(s, game, game.getGameInfo().getSelectedCommonGoals().indexOf(s))).toList());

        ArrayList<String> personalGoals = new ArrayList<>(orderedPlayersNicks.parallelStream()
                .map(o -> game.getPlayerByNick(o))
                .map(Player::getGoal)
                .map(GameObjectConverter::fromPersonalGoalToString)
                .toList());

        Logger.controllerInfo("the game started");
        Logger.controllerInfo("Players order: "+ orderedPlayersNicks.toString());

        serverNetworkHandler.broadcastToAll(
                new GameStartMessage(
                        ServerNetworkHandler.HOSTNAME,
                        ResponsesDescriptions.GAME_STARTED,
                        GameObjectConverter.fromBoardToMatrix(game.getBoard()),
                        GameObjectConverter.fromShelvesToMatrices(game.getPlayersShelves()),
                        personalGoals,
                        orderedPlayersNicks,
                        common,
                        null //as the game starts, no one is the first to complete the shelf
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

        Logger.controllerInfo("New turn: " + orderedPlayersNicks.get(activePlayerIndex) + " with player index " + activePlayerIndex);
        serverNetworkHandler.broadcastToAll(
                new BeginningOfTurnMessage(
                        ServerNetworkHandler.HOSTNAME,
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
     *         calculates the turn wise points
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

        Player currentPlayer = game.getPlayerByNick(orderedPlayersNicks.get(activePlayerIndex));

        Logger.controllerInfo("the current turn ended");
        for (Player p : game.getPlayers()) {
            Printer.printPlayerName(p.getNickname()+"'s shelf");
            Printer.printShelf(GameObjectConverter.fromShelfToMatrix(p.getShelf()));
        }
        Printer.printPlayerName("Board");
        Printer.printBoard(GameObjectConverter.fromBoardToMatrix(game.getBoard()));


        Printer.printPlayerName("Current player's Shelf");
        Printer.printShelf(GameObjectConverter.fromShelfToMatrix(currentPlayer.getShelf()));
        String messageDescription = "THE TURN ENDED\n";

        chosenCoords = null;
        // points are calculated
        game.awardTurnWisePoints(game.getPlayerByNick(orderedPlayersNicks.get(activePlayerIndex)));
        // the game board is refilled
        if (game.getBoard().shouldBeRefilled()) {
            Logger.controllerInfo("the board won't be refilled");
            game.getBoard().refillBoard(game.getSack());
            messageDescription += ResponsesDescriptions.END_OF_TURN_REFILL_BOARD + "\n";
        }

        // the state is saved after all the points have been assigned
        SaveFileManager.saveGameState(getGame(), this, "src/main/assets/savedGames/savefile.json");

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

        ArrayList<SimplifiedCommonGoalCard> simplifiedCommonGoalCards = new ArrayList<>(game
                .getGameInfo()
                .getSelectedCommonGoals()
                .stream()
                .map(c -> GameObjectConverter.fromCommonGoalToSimplifiedCommonGoal(c, game, game.getGameInfo().getCommonGoals().indexOf(c)))
                .toList());

        // the message is built and sent
        serverNetworkHandler.broadcastToAll(
                new EndOfTurnMessage(
                        ServerNetworkHandler.HOSTNAME,
                        messageDescription,
                        GameObjectConverter.fromBoardToMatrix(game.getBoard()),
                        GameObjectConverter.fromShelfToMatrix(currentPlayer.getShelf()),
                        simplifiedCommonGoalCards,
                        firstCommonGoalCompletedByActivePlayer,
                        secondCommonGoalCompletedByActivePlayer,
                        completedShelf,
                        orderedPlayersNicks.get(activePlayerIndex)
                )
        );

        // after game points have been assigned the information is reset
        firstCommonGoalCompletedByActivePlayer = false;
        secondCommonGoalCompletedByActivePlayer = false;
        completedShelf = false;

        // at last the game may end, or a new turn starts
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

        serverNetworkHandler.broadcastToAll(
                new EndOfGameMessage(
                        ServerNetworkHandler.HOSTNAME,
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
     */
    public synchronized void onPlayerDisconnection() {
        serverNetworkHandler.broadcastToAll(
                new AbortedGameMessage(
                        ServerNetworkHandler.HOSTNAME
                )
        );
        System.exit(1);
    }

    /**
     * This method is called when, during the awarding of turn wise points, the
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

    public synchronized ServerNetworkHandler getNetworkHandler() {
        return serverNetworkHandler;
    }
}
