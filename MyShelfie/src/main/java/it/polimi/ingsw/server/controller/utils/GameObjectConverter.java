package it.polimi.ingsw.server.controller.utils;

import it.polimi.ingsw.server.model.Board;
import it.polimi.ingsw.server.model.cards.ObjectTypeEnum;
import it.polimi.ingsw.server.model.cards.goalCards.CommonGoalCard;
import it.polimi.ingsw.server.model.cards.goalCards.PersonalGoalCard;
import it.polimi.ingsw.server.model.cells.BoardCell;
import it.polimi.ingsw.server.model.cells.ShelfCell;
import it.polimi.ingsw.server.model.player.Shelf;
import it.polimi.ingsw.server.model.utils.Constants;
import it.polimi.ingsw.server.model.cards.goalCards.SimplifiedCommonGoalCard;

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
    public static ObjectTypeEnum[][] simplifyBoardIntoMatrix(Board board) {
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
    public static ObjectTypeEnum[][] simplifyShelfIntoMatrix(Shelf shelf) {
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
    public static List<ObjectTypeEnum[][]> simplifyShelvesIntoMatrix(List<Shelf> shelves) {
        return shelves.stream().map(GameObjectConverter::simplifyShelfIntoMatrix).collect(Collectors.toList());
    }

    public static String simplifyPersonalGoalIntoString(PersonalGoalCard goal) {
        return goal.getId();
    }

    public static SimplifiedCommonGoalCard simplifyCommonGoalIntoCard(CommonGoalCard goal) {
        return new SimplifiedCommonGoalCard(goal.getId(), goal.getPointsCards());
    }
}
