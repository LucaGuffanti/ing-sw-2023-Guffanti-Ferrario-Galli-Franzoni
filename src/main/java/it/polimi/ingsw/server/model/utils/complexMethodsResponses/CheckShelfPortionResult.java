package it.polimi.ingsw.server.model.utils.complexMethodsResponses;

import it.polimi.ingsw.server.model.cards.ObjectTypeEnum;

import java.util.Optional;

/**
 * Object representing the result of checking a portion of a player shelf for a common goal card pattern
 * @author Daniele Ferrario
 */
public class CheckShelfPortionResult {
    /**
     * Whether the portion of the shelf present cells arranged in a requested pattern
     */
    private boolean valid;
    /**
     * The type that may need to be shared by cells in the analyzed portion of the shelf
     */
    private Optional<ObjectTypeEnum> commonType;
    /**
     * The matrix of cells which match the patters
     */
    private boolean[][] updatedFoundCellsMatrix;
    public CheckShelfPortionResult(boolean valid, Optional<ObjectTypeEnum> commonType, boolean[][] updatedFoundCellsMatrix) {
        this.valid = valid;
        this.commonType = commonType;
        this.updatedFoundCellsMatrix = updatedFoundCellsMatrix;
    }

    public boolean[][] getUpdatedFoundCellsMatrix() {
        return updatedFoundCellsMatrix;
    }

    public boolean isValid() {
        return valid;
    }

    public Optional<ObjectTypeEnum> getCommonType() {
        return commonType;
    }
}
