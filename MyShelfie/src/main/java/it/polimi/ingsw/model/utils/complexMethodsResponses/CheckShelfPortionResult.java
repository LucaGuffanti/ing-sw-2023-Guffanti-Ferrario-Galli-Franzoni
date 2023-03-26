package it.polimi.ingsw.model.utils.complexMethodsResponses;

import it.polimi.ingsw.model.cards.ObjectTypeEnum;

import java.util.Optional;

public class CheckShelfPortionResult {
    private boolean valid;
    private Optional<ObjectTypeEnum> commonType;

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
