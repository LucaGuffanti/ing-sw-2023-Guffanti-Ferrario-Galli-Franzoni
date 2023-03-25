package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.cells.ShelfCell;

import java.util.List;

public class Shelf {
    private int heightInCells;
    private int lengthInCells;
    private ShelfCell[][] cells;
    private boolean isFull;
    private List<Integer> highestOccupiedCell;

    public int getHeightInCells() {
        return heightInCells;
    }

    public void setHeightInCells(int heightInCells) {
        this.heightInCells = heightInCells;
    }

    public int getLengthInCells() {
        return lengthInCells;
    }

    public void setLengthInCells(int lengthInCells) {
        this.lengthInCells = lengthInCells;
    }

    public ShelfCell[][] getCells() {
        return cells;
    }

    public void setCells(ShelfCell[][] cells) {
        this.cells = cells;
    }

    public boolean isFull() {
        return isFull;
    }

    public void setFull(boolean full) {
        isFull = full;
    }

    public List<Integer> getHighestOccupiedCell() {
        return highestOccupiedCell;
    }

    public void setHighestOccupiedCell(List<Integer> highestOccupiedCell) {
        this.highestOccupiedCell = highestOccupiedCell;
    }

    public Shelf(int heightInCells, int lengthInCells, ShelfCell[][] cells) {
        this.heightInCells = heightInCells;
        this.lengthInCells = lengthInCells;
        this.cells = cells;
    }

    public ShelfCell getCell(int x, int y){
        return cells[x][y];
    }

}
