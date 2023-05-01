package it.polimi.ingsw.server.model.cells;

import java.io.Serializable;

public class Coordinates implements Serializable {
    protected int x;
    protected int y;

    public Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Coordinates(String x, String y) {
        this.x = Integer.valueOf(x);
        this.y = Integer.valueOf(y);
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
}
