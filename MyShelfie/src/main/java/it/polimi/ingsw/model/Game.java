package it.polimi.ingsw.model;

import it.polimi.ingsw.model.cards.*;
import it.polimi.ingsw.model.cards.goalCards.CommonGoalCard;
import it.polimi.ingsw.model.cards.goalCards.PersonalGoalCard;
import it.polimi.ingsw.model.cells.Cell;
import it.polimi.ingsw.model.cells.Coordinates;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Shelf;
import it.polimi.ingsw.model.utils.MatrixUtils;
import it.polimi.ingsw.model.utils.exceptions.MaxPlayersException;
import it.polimi.ingsw.model.utils.exceptions.WrongNumberOfPlayersException;
import it.polimi.ingsw.model.utils.exceptions.WrongPointCardsValueGivenException;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;

import static it.polimi.ingsw.model.utils.Constants.BOARD_DIMENSION;

/**
 * The main game class. <br>
 * This class is basically the software transposition of My Shelfie.<br>
 * The game class contains every information about a game:
 * <table>
 *     <tr>
 *         <td><b>board</b></td> <td>the board of the game</td>
 *     </tr>
 *     <tr>
 *         <td><b>sack</b></td> <td>the sack containing cards</td>
 *     </tr>
 *     <tr>
 *         <td><b>endOfGameCard</b></td> <td>the card assigned to the first player completing the shelf</td>
 *     </tr>
 *     <tr>
 *         <td><b>players</b></td> <td>the list of players. (Each player has a reference to its own shelf)</td>
 *     </tr>
 *     <tr>
 *         <td><b>gameInfo</b></td> <td>the object containing infos about the game</td>
 *     </tr>
 *     <tr>
 *         <td><b>goalCardsDeck</b></td> <td>the deck containing the different goal cards</td>
 *     </tr>
 * </table>
 *
 * @author Luca Guffanti
 * @see Board
 * @see Sack
 * @see EndOfGameCard
 * @see Player
 * @see GameInfo
 * @see GoalCardsDeckSingleton
 */


public class Game {

    private Board board;
    private Sack sack;
    private EndOfGameCard endOfGameCard;
    private ArrayList<Player> players;
    private GameInfo gameInfo;
    private GoalCardsDeckSingleton goalCardsDeck;

    /**
     * The constructor gets called when a player wants to create a game.
     * It's important to notice that creating a game doesn't mean that the game is started:
     * a game starts when the number of registered players matches the number of players set by the admin at
     * the moment of the creation. This means that some of the more game-related objects will be instantiated when
     * the game actually starts.
     * @param admin the player who wants to create the game
     * @param nPlayers the number of players that the admin selected
     *
     * @throws WrongNumberOfPlayersException if the number of players isn't between 2 and 4
     */
    public Game(Player admin, int nPlayers, int gameID) throws WrongNumberOfPlayersException{
        if (nPlayers < 2 || nPlayers > 4) {
            throw new WrongNumberOfPlayersException(nPlayers);
        }

        players = new ArrayList<>();
        players.add(admin);

        gameInfo = new GameInfo();
        gameInfo.setGameID(gameID);
        gameInfo.setAdmin(admin);
        gameInfo.setNPlayers(nPlayers);
        gameInfo.setGameStatus(GameStatusEnum.ACCEPTING_PLAYERS);
    }

    /**
     * This method is called when the number of wanted players is reached.
     * It's important to notice that this method isn't directly called by this class, as it's called
     * by <b>GameManager</b>: a class that is an intermediary between the model (Game) and the controller, that can
     * access information from the specific instance of the game, and notifies the game class when something happens.
     * <br>
     * In this method all the game related stuff is initialized.
     * @see GameManager
     */
    public void initGame() {

        // firstly, the game related objects are instantiated:
        // the sack gets filled
        sack = new Sack();
        // the board gets populated
        board = new Board(gameInfo.getNPlayers(), sack);
        // the end of game card gets built
        endOfGameCard = new EndOfGameCard();
        // the instance of the deck is retrieved (or if the deck isn't instantiated it gets created)
        goalCardsDeck = GoalCardsDeckSingleton.getInstance();

        // then the commonGoalCards are taken from the deck and safely stored in GameInfo
        ArrayList<CommonGoalCard> commonGoalCards = goalCardsDeck.pickTwoCommonGoals();

        // create the stack of pointCards based on the number of players
        ArrayList<PointCard> pointCards;
        try {
            pointCards = CardBuilder.generatePointsCards(gameInfo.getNPlayers());
            for (CommonGoalCard card : commonGoalCards) {
                card.setPointsCards(pointCards);
            }
        } catch (WrongNumberOfPlayersException e) {
            e.printStackTrace();
        } catch (WrongPointCardsValueGivenException e) {
            e.printStackTrace();
        }


        gameInfo.setSelectedCommonGoals(commonGoalCards);

        // then the personalGoalCards are taken from the deck and given to every player
        ArrayList<PersonalGoalCard> personalGoalCards;
        try{

            personalGoalCards = goalCardsDeck.pickPersonalGoals(gameInfo.getNPlayers());
            int nPlayers = gameInfo.getNPlayers();
            for(int i = 0; i < nPlayers; i++) {
                givePersonalGoalCardToPlayer(players.get(i), personalGoalCards.get(i));
            }

        } catch (WrongNumberOfPlayersException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method picks a personalGoalCard from the sack and gives it to the player
     * @param player the player who should receive the personalGoalCard
     * @param goalCard the card that should be given to the player
     */
    private void givePersonalGoalCardToPlayer(Player player, PersonalGoalCard goalCard) {
        player.setGoal(goalCard);
    }

    /**
     * At the end of a turn, the current player should receive points based on the
     * completion of the commonGoalCard. If the player is the first one completing the shelf, the
     * game should enter the last stage, and the player should be awarded the EndOfGameCard.
     * @param currentPlayer the currentPlayer
     */
    public void awardTurnWisePoints(Player currentPlayer) {
        boolean completedFirst = currentPlayer.getAchievements().isCompletedFirstCommonGoal();
        boolean completedSecond = currentPlayer.getAchievements().isCompletedSecondCommonGoal();
        CommonGoalCard toBeChecked;
        int pointsToBeAwarded;
        PointCard pointCardToBeAwarded;

        // the player can complete the common goal card only if it hasn't been completed yet by the same player.
        if (!completedFirst) {
            toBeChecked = gameInfo.getSelectedCommonGoals().get(0);

            try {
                pointsToBeAwarded = toBeChecked.calculatePoints(currentPlayer);
                pointCardToBeAwarded = CardBuilder.generatePointCardFromPointsGiven(pointsToBeAwarded);
                awardPointCard(currentPlayer, pointCardToBeAwarded, 1);
            }
            catch(WrongPointCardsValueGivenException ex){
                ex.printStackTrace();
            }
        }
        if(!completedSecond) {
            toBeChecked = gameInfo.getSelectedCommonGoals().get(1);

            try {
                pointsToBeAwarded = toBeChecked.calculatePoints(currentPlayer);
                pointCardToBeAwarded = CardBuilder.generatePointCardFromPointsGiven(pointsToBeAwarded);
                awardPointCard(currentPlayer, pointCardToBeAwarded, 2);
            }
            catch(WrongPointCardsValueGivenException ex){
                ex.printStackTrace();
            }
        }

        // if the player has completed the shelf, award the end of game card
        if (currentPlayer.getShelf().isFull()) {
            gameInfo.setGameStatus(GameStatusEnum.LAST_TURN);
            awardEndOfGameCard(currentPlayer);
        }
    }

    /**
     * @param currPlayer the player who will be awarded the pointCard
     * @param pointCard the pointCard that will be awarded to the player
     * @param fromWhichCommonGoal the id of the common goal card (either 1 or 2) the points are coming from
     */
    private void awardPointCard(Player currPlayer, PointCard pointCard, int fromWhichCommonGoal) {
        // the point card should be awarded only if its points are not 0
        if (pointCard.getPointsGiven() != 0) {
            // if the point card is meaningful the player gets it, and the common goal is marked as completed.
            currPlayer.getAchievements().getPointCardsEarned().add(pointCard);
            if (fromWhichCommonGoal == 1) {
                currPlayer.getAchievements().setCompletedFirstCommonGoal(true);
            } else {
                currPlayer.getAchievements().setCompletedSecondCommonGoal(true);
            }
        }
    }

    /**
     * This method simply awards the end of game card to the first player completing the shelf.
     * @param firstToComplete the first player who completes the shelf
     */
    private void awardEndOfGameCard(Player firstToComplete) {
        firstToComplete.getAchievements().setFirstToFinish(true);
        endOfGameCard.setAssigned(true);
    }

    /**
     * This method is called when the game ends. It commands
     * the award of the final points and declares the winner
     */
    public void endGame(List<Player> playersOrderedInTurns) {
        gameInfo.setGameStatus(GameStatusEnum.ENDED);
        gameCheckout(playersOrderedInTurns);

    }

    /**
     * This method calculates the points of each player in the game.
     * @return The nickname of the winner of the game
     * @param playersOrderedInTurns the list containing the players ordered in terms of turns, where the first
     *                              element of the list is the first player to play and the last element is the last
     *                              player to play
     */
    private String gameCheckout(List<Player> playersOrderedInTurns) {

        String winnerNickName;
        int winnerPoints;

        winnerNickName = players.get(0).getNickname();
        winnerPoints = calculateFinalPoints(players.get(0));

        // the player who has more points wins the game. if more players
        // have the same amount of points, the player that's the furthest (moving clockwise) from the starting
        // player wins the game, hence the <= in the check.

        for(int i = 1; i < players.size(); i++) {
            int currentPoints = calculateFinalPoints(players.get(i));
            if(winnerPoints <= currentPoints) {
                winnerPoints = currentPoints;
                winnerNickName = players.get(i).getNickname();
            }
        }
        return winnerNickName;
    }

    /**
     * This method calculates the total points of a given player, and is called when the game ends
     * @param player the player whose points will be calculated
     * @return the final points of the player
     */
    private int calculateFinalPoints(Player player) {
        int finalPoints = 0;

        // first calculate the total points given by completing common goal cards
        finalPoints += player.getAchievements().getPointCardsEarned().stream()
                .map(PointCard::getPointsGiven)
                .reduce(0, Integer::sum);
        // then calculate the total points given by completing the personal goal card
        PersonalGoalCard playerGoal = player.getGoal();
        finalPoints += playerGoal.calculatePoints(player);
        // then calculate the total points given by having adjacent cards in the shelf
        finalPoints += EndGameAdjacencyGoalChecker.calculateAdjacencyPoints(player);
        // then, if the player is the first to complete the shelf, add 1 point
        if (player.getAchievements().isFirstToFinish()) {
            finalPoints += 1;
        }

        // the verification of points is completed: return the total points.
        return finalPoints;
    }

    /**
     * This method adds a player to the game, given the nickname of the player. If the game is full,
     * so the number of maximum players is reached, an exception is thrown.
     * @param nickname the nickname of the player to be added
     * @throws MaxPlayersException when the number of players decided by the host is reached
     */
    public void addPlayer(String nickname) throws MaxPlayersException {
        if (players.size() == gameInfo.getNPlayers()) throw new MaxPlayersException("The game is full");

        Player player = new Player(nickname);
        players.add(player);
    }

    /**
     * This method moves cards to a player's shelf. More specifically, it commands the execution of the equivalent
     * method in the player class.
     * @return true or false if the addition is completed or not.
     */
    public boolean moveCardsToPlayerShelf(Player currentPlayer, List<ObjectCard> objectCards, int wantedColumn) {
        boolean success =  currentPlayer.addCardsToShelf(objectCards, wantedColumn);
        return success;
    }

    /**
     * Check if player can pick a BoardCell from the board given its coordinates
     * @param x
     * @param y
     */
    private boolean playerCanPickSingleCellFromBoard(int x, int y){


        // If target cell is covered
        if(this.board.getCell(x,y).isCovered()){

            // Check if exists an adjacent cell which is not covered
            boolean expr = (
                    ((x-1 >= 0) && !this.board.getCell(x-1, y).isCovered()) ||
                    ((y-1 >= 0) && !this.board.getCell(x, y-1).isCovered()) ||
                    ((x+1 < BOARD_DIMENSION) && !this.board.getCell(x+1, y).isCovered()) ||
                    ((y+1 < BOARD_DIMENSION) && !this.board.getCell(x, y+1).isCovered())

            );



            if (expr)
                return true;
        }

        return false;

    }

    /**
     * Check if player can pick a set of BoardCells given their coordinates from the board
     * @param cellsCoordinates the cells to pick
     * @author Daniele Ferrario
     */
    public boolean playerCanPickFromCellsBoard(Set<Coordinates> cellsCoordinates){


        // Check if coordinates belong to the same row or to the same column
        if(cellsCoordinates.stream().map(Coordinates::getX).distinct().count() > 1 &&
                cellsCoordinates.stream().map(Coordinates::getY).distinct().count() > 1)
            return false;


        // Checking adjacent ones
        for (Coordinates cell: cellsCoordinates) {
            if(!playerCanPickSingleCellFromBoard(cell.getX(), cell.getY()))
                return false;
        }

        return true;
    }

}
