package it.polimi.ingsw.model;

import it.polimi.ingsw.model.cards.EndGameCard;
import it.polimi.ingsw.model.player.Player;

import java.util.ArrayList;

/**
 * The main game class. <br>
 * This class is basically the software transposition of My Shelfie.<br>
 * The game class contains every information about a game:
 * <table>
 *     <tr>
 *         <tc><b>board</b></tc> <tc>the board of the game</tc>
 *     </tr>
 *     <tr>
 *         <tc><b>sack</b></tc> <tc>the sack containing cards</tc>
 *     </tr>
 *     <tr>
 *         <tc><b>endGameCard</b></tc> <tc>the card assigned to the first player completing the shelf</tc>
 *     </tr>
 *     <tr>
 *         <tc><b>players</b></tc> <tc>the list of players. (Each player has a reference to its own shelf)</tc>
 *     </tr>
 *     <tr>
 *         <tc><b>gameInfo</b></tc> <tc>the object containing infos about the game</tc>
 *     </tr>
 *     <tr>
 *         <tc><b>goalCardsDeck</b></tc> <tc>the deck containing the different goal cards</tc>
 *     </tr>
 * </table>
 *
 * @author Luca Guffanti
 * @see Board
 * @see Sack
 * @see EndGameCard
 * @see Player
 * @see GameInfo
 * @see GoalCardsDeckSingleton
 */
public class Game {

    private Board board;
    private Sack sack;
    private EndGameCard endGameCard;
    private ArrayList<Player> players;
    private GameInfo gameInfo;
    private GoalCardsDeckSingleton goalCardsDeck;

    // TODO implement methods
}
