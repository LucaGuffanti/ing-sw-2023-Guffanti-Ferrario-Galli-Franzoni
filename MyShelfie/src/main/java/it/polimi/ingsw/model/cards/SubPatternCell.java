package it.polimi.ingsw.model.cards;

import java.util.Optional;

/**
 * A subPatternCell represents a covered cell in a SubPattern.
 *
 * @author Daniele Ferrario
 */
public class SubPatternCell {
    /**
     * X coordinate relative to the subPattern frame.
     */
    private int x;

    /**
     * Y coordinate relative to the subPattern frame.
     */
    private int y;

    /**
     * The optional constraint which indicates
     * a unique admitted type of ObjectTypeEnum
     */
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
