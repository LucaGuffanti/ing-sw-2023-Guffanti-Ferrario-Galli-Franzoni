package it.polimi.ingsw.server.controller.utils;

import it.polimi.ingsw.server.model.Board;
import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.GameInfo;
import it.polimi.ingsw.server.model.SimplifiedGameInfo;
import it.polimi.ingsw.server.model.cards.ObjectTypeEnum;
import it.polimi.ingsw.server.model.cards.PointCard;
import it.polimi.ingsw.server.model.cards.goalCards.CommonGoalCard;
import it.polimi.ingsw.server.model.cards.goalCards.PersonalGoalCard;
import it.polimi.ingsw.server.model.cells.BoardCell;
import it.polimi.ingsw.server.model.cells.ShelfCell;
import it.polimi.ingsw.server.model.player.Player;
import it.polimi.ingsw.server.model.player.Shelf;
import it.polimi.ingsw.server.model.player.SimplifiedPlayer;
import it.polimi.ingsw.server.model.utils.Constants;
import it.polimi.ingsw.server.model.cards.goalCards.SimplifiedCommonGoalCard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class implements methods that help converting complex objects into their simplified description.
 * Methods perform the subsequent operations
 * <ol>
 *     <li>ObjectCards are mapped to their type</li>
 *     <li>Boards are transformed into matrices of ObjectTypeEnum objects if a card is present in a position (null otherwise)</li>
 *     <li>Shelves are tranformed into matrices of ObjectTypeEnum objects if a card is present in a position (null otherwise)</li>
 *     <li>PersonalGoalCards are mapped to their id</li>
 *     <li>CommonGoalCards are mapped to their id and their pointCard list into a {@link SimplifiedCommonGoalCard}</li>
 * </ol>
 * These methods are useful for exchanging simple objects through the network, as a client only needs a simple description
 * of each game object.
 * @author Luca Guffanti
 */
public class GameObjectConverter {

    /**
     * This method uses a board to build its simplified equivalent
     * @param board the board to be converted into a matrix
     * @return a ObjectTypeEnum matrix representing the board
     */
    public static ObjectTypeEnum[][] fromBoardToMatrix(Board board) {
        ObjectTypeEnum[][] simplifiedBoard = new ObjectTypeEnum[Constants.BOARD_DIMENSION][Constants.BOARD_DIMENSION];
        BoardCell[][] cells = board.getCells();

        for (int y = 0; y < Constants.BOARD_DIMENSION; y++) {
            for (int x = 0; x < Constants.BOARD_DIMENSION; x++) {
                if (cells[y][x].getCellCard().isEmpty()) {
                    simplifiedBoard[y][x] = null;
                } else {
                    simplifiedBoard[y][x] = cells[y][x].getCellCard().get().getType();
                }
            }
        }
        return simplifiedBoard;
    }

    /**
     * This method builds a matrix representing a shelf starting from the shelf
     * @param shelf the shelf to be converted into a matrix
     * @return the matrix representation of the shelf
     */
    public static ObjectTypeEnum[][] fromShelfToMatrix(Shelf shelf) {
        ObjectTypeEnum[][] simplifiedShelf = new ObjectTypeEnum[Constants.SHELF_HEIGHT][Constants.SHELF_LENGTH];
        ShelfCell[][] cells = shelf.getCells();

        for (int y = 0; y < Constants.SHELF_HEIGHT; y++) {
            for (int x = 0; x < Constants.SHELF_LENGTH; x++) {
                if (cells[y][x].getCellCard().isEmpty()) {
                    simplifiedShelf[y][x] = null;
                } else {
                    simplifiedShelf[y][x] = cells[y][x].getCellCard().get().getType();
                }
            }
        }
        return simplifiedShelf;
    }

    /**
     * This method simplifies the list of shelves objects into a list of ObjectTypeEnum[][] matrixes
     * @param shelves the list of shelves to be converted into a matrix
     * @return the matrix representation of the shelf
     */
    public static List<ObjectTypeEnum[][]> fromShelvesToMatrices(List<Shelf> shelves) {
        return shelves.stream().map(GameObjectConverter::fromShelfToMatrix).collect(Collectors.toList());
    }

    /**
     * This method maps a personalGoal to its id
     * @param goal the personal goal
     * @return the id of the personalGoal
     */
    public static String fromPersonalGoalToString(PersonalGoalCard goal) {
        return goal.getId();
    }

    /**
     * This method maps a common goal card to a simplified common goal card
     * @param goal the common goal
     * @param game the game
     * @param numOfCard the ordinal number of the card (either 0 or 1)
     * @return the corresponding simplifiedCommonGoalCard
     */
    public static SimplifiedCommonGoalCard fromCommonGoalToSimplifiedCommonGoal(CommonGoalCard goal, Game game, int numOfCard) {
        HashMap<String, PointCard> nickToPoints = new HashMap<>();

        List<String> playerNicks = game.getPlayers().stream().map(Player::getNickname).toList();
        for (String player : playerNicks) {
            nickToPoints.put(player, (game.getPlayerByNick(player).getAchievements().getPointCardsEarned().get(numOfCard+1)));
        }

        return new SimplifiedCommonGoalCard(goal.getId(), goal.getPointsCards(), nickToPoints);
    }
    public static List<SimplifiedPlayer> fromPlayersToSimplifiedPlayers(ArrayList<Player> players) {
        return players.stream()
                .map(p-> new SimplifiedPlayer(
                        p.getNickname(),
                        fromShelfToMatrix(p.getShelf()),
                        p.getAchievements(),
                        fromPersonalGoalToString(p.getGoal())))
                .toList();
    }

    public static SimplifiedGameInfo fromGameInfoToSimplifiedGameInfo(GameInfo gameInfo) {

        List<CommonGoalCard> commonGoalCards = gameInfo.getCommonGoals();
        List<SimplifiedCommonGoalCard> simpleCommonGoalCards = commonGoalCards
                .stream()
                .map(c->new SimplifiedCommonGoalCard(c.getId(), c.getPointsCards(), null))
                .toList();

        return new SimplifiedGameInfo(
                gameInfo.getAdmin().getNickname(),
                gameInfo.getNPlayers(),
                "NULL",
                gameInfo.getGameID(),
                new ArrayList<>(simpleCommonGoalCards),
                new ArrayList<>(gameInfo.getPersonalGoals().stream().map(p->p.getId()).toList())
        );
    }
}
