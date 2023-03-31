package it.polimi.ingsw.model.cards;

import java.util.Optional;

/**
 * A subPatternCell represents a covered cell in a Pattern.
 *
 * @author Daniele Ferrario
 */
public class PatternCell {
    /**
     * X coordinate relative to the pattern frame.
     */
    private int x;

    /**
     * Y coordinate relative to the pattern frame.
     */
    private int y;

    /**
     * The optional constraint which indicates
     * a unique admitted type of ObjectTypeEnum
     */
    private Optional<ObjectTypeEnum> admittedType;

    public PatternCell(int x, int y, Optional<ObjectTypeEnum> admittedType){
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PatternCell that = (PatternCell) o;

        if (x != that.x) return false;
        if (y != that.y) return false;
        return admittedType.equals(that.admittedType);
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        result = 31 * result + admittedType.hashCode();
        return result;
    }
}
