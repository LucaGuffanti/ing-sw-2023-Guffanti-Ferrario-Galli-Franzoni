package it.polimi.ingsw.model;

import it.polimi.ingsw.model.cards.CommonGoalCard;
import it.polimi.ingsw.model.cards.EndOfGameCard;
import it.polimi.ingsw.model.cards.PersonalGoalCard;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.utils.exceptions.WrongNumberOfPlayersException;

import java.util.ArrayList;

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
        // the board gets populated
        board = new Board(gameInfo.getNPlayers());
        // the sack gets filled
        sack = new Sack();
        // the end of game card gets built
        endOfGameCard = new EndOfGameCard();
        // the instance of the deck is retrieved (or if the deck isn't instantiated it gets created)
        goalCardsDeck = GoalCardsDeckSingleton.getInstance();

        // then the commonGoalCards are taken from the deck and safely stored in GameInfo
        ArrayList<CommonGoalCard> commonGoalCards = goalCardsDeck.pickCommonGoals();
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

    private void givePersonalGoalCardToPlayer(Player player, PersonalGoalCard goalCard) {
        // TODO implement method
    }
}
