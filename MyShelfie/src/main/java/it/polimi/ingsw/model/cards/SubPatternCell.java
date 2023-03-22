package it.polimi.ingsw.model.cards;

import java.util.Optional;

public class SubPatternCell {
    private int x;
    private int y;
    private Optional<ObjectTypeEnum> admittedType;

    public SubPatternCell(int x, int y, Optional<ObjectTypeEnum> admittedType){
        this.x = x;
        this.y = y;
        this.admittedType = admittedType;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Optional<ObjectTypeEnum> getAdmittedType() {
        return admittedType;
    }

    public void setAdmittedType(Optional<ObjectTypeEnum> admittedType) {
        this.admittedType = admittedType;
    }
}
