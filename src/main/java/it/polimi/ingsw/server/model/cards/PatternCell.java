package it.polimi.ingsw.server.model.cards;

import it.polimi.ingsw.server.model.cells.Coordinates;

import java.util.Optional;

/**
 * A Pattern Cell represents a covered cell in a Pattern,
 * with a set of coordinates relative to the Pattern frame
 * and the admittedType.
 *
 * @author Daniele Ferrario
 */
public class PatternCell extends Coordinates {


    /**
     * The optional constraint which indicates
     * a unique admitted type of ObjectTypeEnum
     */
    private Optional<ObjectTypeEnum> admittedType;

    public PatternCell(int x, int y, Optional<ObjectTypeEnum> admittedType){
        super(x,y);
        this.admittedType = admittedType;
    }

    public int getX() {
        return super.x;
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
