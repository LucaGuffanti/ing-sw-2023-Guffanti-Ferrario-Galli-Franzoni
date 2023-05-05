package it.polimi.ingsw.server.controller.save;

import it.polimi.ingsw.server.model.cards.ObjectTypeEnum;
import it.polimi.ingsw.server.model.player.SimplifiedPlayer;
import it.polimi.ingsw.server.model.SimplifiedGameInfo;
import it.polimi.ingsw.server.model.Sack;

import java.io.Serializable;
import java.util.List;

/**
 * This object contains all the information needed to completely store the state of a running game on
 * the disk.
 *
 * For a game state to be safely stored, the following elements are needed:
 * <h3>GAME MODEL INFORMATION</h3>
 * <ul>
 *     <li>
 *         A List of all the players, saved in a simplified way, as a list of {@link SimplifiedPlayer} objects. Each
 *         SimplifiedPlayer contains the achievements and a simplified description of the shelf (as a matrix) and
 *         of the associated personal goal card
 *     </li>
 *     <li>
 *         The board, saved as a matrix
 *     </li>
 *     <li>
 *         All the information about the game, saved as a {@link SimplifiedGameInfo} object.
 *     </li>
 *     <li>
 *         The game sack, saved as a {@link Sack} object.
 *     </li>
 * </ul>
 * <h3>GAME CONTROLLER INFORMATION</h3>
 * <ul>
 *     <li>The ordered list of nicknames</li>
 *     <li>The index in the list of players of the active player</li>
 * </ul>
 * @author Luca Guffanti
 */
public class SaveFileData implements Serializable {
    /*
    ==================FROM THE MODEL==================
     */
    private List<SimplifiedPlayer> players;
    private ObjectTypeEnum[][] board;
    private SimplifiedGameInfo gameInfo;
    private Sack sack;
    /*
    ================FROM THE CONTROLLER================
     */
    private List<String> orderedPlayers;
    private int activePlayerIndex;

    public SaveFileData(List<SimplifiedPlayer> players,
                        ObjectTypeEnum[][] board,
                        SimplifiedGameInfo gameInfo,
                        Sack sack,
                        List<String> orderedPlayers,
                        int activePlayerIndex) {
        this.players = players;
        this.board = board;
        this.gameInfo = gameInfo;
        this.sack = sack;
        this.orderedPlayers = orderedPlayers;
        this.activePlayerIndex = activePlayerIndex;
    }

    public List<SimplifiedPlayer> getPlayers() {
        return players;
    }

    public void setPlayers(List<SimplifiedPlayer> players) {
        this.players = players;
    }

    public ObjectTypeEnum[][] getBoard() {
        return board;
    }

    public void setBoard(ObjectTypeEnum[][] board) {
        this.board = board;
    }

    public SimplifiedGameInfo getGameInfo() {
        return gameInfo;
    }

    public void setGameInfo(SimplifiedGameInfo gameInfo) {
        this.gameInfo = gameInfo;
    }

    public Sack getSack() {
        return sack;
    }

    public void setSack(Sack sack) {
        this.sack = sack;
    }

    public List<String> getOrderedPlayers() {
        return orderedPlayers;
    }

    public void setOrderedPlayers(List<String> orderedPlayers) {
        this.orderedPlayers = orderedPlayers;
    }

    public int getActivePlayerIndex() {
        return activePlayerIndex;
    }

    public void setActivePlayerIndex(int activePlayerIndex) {
        this.activePlayerIndex = activePlayerIndex;
    }
}
