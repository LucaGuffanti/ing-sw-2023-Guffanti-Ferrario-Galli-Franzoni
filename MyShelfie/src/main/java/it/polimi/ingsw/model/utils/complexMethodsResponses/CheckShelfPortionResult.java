package it.polimi.ingsw.model.utils.complexMethodsResponses;

import it.polimi.ingsw.model.cards.ObjectTypeEnum;

import java.util.Optional;

public class CheckShelfPortionResult {
    private boolean valid;
    private Optional<ObjectTypeEnum> commonType;

    public CheckShelfPortionResult(boolean valid, Optional<ObjectTypeEnum> commonType) {
        this.valid = valid;
        this.commonType = commonType;
    }

    public boolean isValid() {
        return valid;
    }

    public Optional<ObjectTypeEnum> getCommonType() {
        return commonType;
    }
}
