package it.polimi.ingsw.server.controller.utils;

import it.polimi.ingsw.server.model.*;
import it.polimi.ingsw.server.model.cards.ObjectCard;
import it.polimi.ingsw.server.model.cards.ObjectTypeEnum;
import it.polimi.ingsw.server.model.cards.PointCard;
import it.polimi.ingsw.server.model.cards.goalCards.CommonGoalCard;
import it.polimi.ingsw.server.model.cards.goalCards.PersonalGoalCard;
import it.polimi.ingsw.server.model.cells.BoardCell;
import it.polimi.ingsw.server.model.cells.BoardCellEnum;
import it.polimi.ingsw.server.model.cells.ShelfCell;
import it.polimi.ingsw.server.model.player.Player;
import it.polimi.ingsw.server.model.player.Shelf;
import it.polimi.ingsw.server.model.player.SimplifiedPlayer;
import it.polimi.ingsw.server.model.utils.Constants;
import it.polimi.ingsw.server.model.cards.goalCards.SimplifiedCommonGoalCard;
import it.polimi.ingsw.server.model.utils.CsvToBoardParser;

import java.util.*;
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

    /**
     * This method partially generates a common goal
     * @param goal the common goal card
     * @return a simplified common goal card that does not contain the map between players and points
     */
    public static SimplifiedCommonGoalCard fromCommonGoalToSimplifiedPartialCommonGoal(CommonGoalCard goal) {
        return new SimplifiedCommonGoalCard(goal.getId(), goal.getPointsCards(), null);
    }

    /**
     * This method generates an array of simplified players starting from a list of players
     * @param players the list of players
     * @return an array of simplified players
     */
    public static SimplifiedPlayer[] fromPlayersToSimplifiedPlayers(ArrayList<Player> players) {
        return players.stream()
                .map(p -> new SimplifiedPlayer(
                        p.getNickname(),
                        fromShelfToMatrix(p.getShelf()),
                        p.getAchievements(),
                        fromPersonalGoalToString(p.getGoal())))
                .toList().toArray(new SimplifiedPlayer[0]);
    }

    /**
     * This method generates a simplified game information starting from the game info
     * @param gameInfo the game info that will be simplified
     * @return the simplified game information
     */
    public static SimplifiedGameInfo fromGameInfoToSimplifiedGameInfo(GameInfo gameInfo) {

        List<CommonGoalCard> commonGoalCards = gameInfo.getCommonGoals();
        List<SimplifiedCommonGoalCard> simpleCommonGoalCards = commonGoalCards
                .stream()
                .map(c->new SimplifiedCommonGoalCard(c.getId(), c.getPointsCards(), null))
                .toList();

        return new SimplifiedGameInfo(
                gameInfo.getAdmin(),
                gameInfo.getNPlayers(),
                "NULL",
                gameInfo.getGameID(),
                new ArrayList<>(simpleCommonGoalCards),
                new ArrayList<>(gameInfo.getPersonalGoals().stream().map(p->p.getId()).toList()),
                gameInfo.getFirstToCompleteTheShelf()
        );
    }

    /**
     * This method generates a list of common goal cards starting from a list of common goal cards
     * @param simplified a list of simplified common goal cards
     * @return a list of common goal cards
     */
    public static ArrayList<CommonGoalCard> fromSimplifiedCommonGoalsToCommonGoals(ArrayList<SimplifiedCommonGoalCard> simplified) {
        ArrayList<CommonGoalCard> commonGoals = new ArrayList<>(simplified.stream()
                .map(s-> GoalCardsDeckSingleton.getInstance().getCommonGoalCardById(s.getId()))
                .toList());
        
        for (int i = 0; i < commonGoals.size(); i++) {
            commonGoals.get(i).setPointsCards(simplified.get(i).getPointCards());
        }
        return commonGoals;
    }

    /**
     * This method generates a list of personal goal cards starting from a list of personal card ids
     * @param personalGoals a list of personal goals card ids
     * @return a list of personal goal cards
     */
    public static ArrayList<PersonalGoalCard> fromIdToPersonalGoal(ArrayList<String> personalGoals) {
        return new ArrayList<>(
                personalGoals.stream().map(p->GoalCardsDeckSingleton.getInstance().getPersonalGoalCardById(p)).toList()
        );
    }

    /**
     *
     * @param savedBoard the matrix representing the saved board
     * @param nPlayers the number of players
     * @param path the path to the csv file containing the board configuration in terms of active and inactive cells
     *             based on the number of players
     * @return the board cell matrix
     */
    public static BoardCell[][] fromMatrixToBoard(ObjectTypeEnum[][] savedBoard, int nPlayers, String path) {
        BoardCell[][] cells = CsvToBoardParser.parseBoardCellTypeConfiguration(path, nPlayers);
        for (int i = 0; i < Constants.BOARD_DIMENSION; i++) {
            for (int j = 0; j < Constants.BOARD_DIMENSION; j++) {
                if (savedBoard[i][j] == null) {
                    cells[i][j].setCellCard(Optional.empty());
                } else {
                    cells[i][j].setCellCard(Optional.of(new ObjectCard(savedBoard[i][j])));
                }
            }
        }
        return cells;
    }

    /**
     * This method loads a list of players starting from simplified players
     * @param savedPlayers the list of saved players
     * @return the list of players
     */
    public static ArrayList<Player> fromSimplifiedPlayerToPlayer(SimplifiedPlayer[] savedPlayers) {
        ArrayList<Player> players = new ArrayList<>();

        for (int i = 0; i< savedPlayers.length; i++) {
            Player p = new Player(savedPlayers[i].getName());
            p.setGoal(GoalCardsDeckSingleton.getInstance().getPersonalGoalCardById(savedPlayers[i].getPersonalGoalId()));
            p.setAchievements(savedPlayers[i].getAchievements());
            p.setShelf(fromMatrixToShelf(savedPlayers[i].getShelf()));
            players.add(p);
        }
        return players;
    }

    /**
     * This method generates a shelf starting from a matrix describing a shelf
     * @param shelf the shelf to be converted
     * @return shelf generated starting from the shelf matrix
     */
    public static Shelf fromMatrixToShelf(ObjectTypeEnum[][] shelf) {
        ShelfCell[][] cells = new ShelfCell[Constants.SHELF_HEIGHT][Constants.SHELF_LENGTH];
        for (int i = 0; i < Constants.SHELF_HEIGHT; i++) {
            for (int j = 0; j < Constants.SHELF_LENGTH; j++) {
                if (shelf[i][j] == null) {
                    cells[i][j] = new ShelfCell(Optional.empty());
                } else {
                    cells[i][j] = new ShelfCell(Optional.of(new ObjectCard(shelf[i][j])));
                }
            }
        }
        Shelf s = new Shelf();
        s.setCells(cells);
        return s;
    }
}
