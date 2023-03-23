package it.polimi.ingsw.model.player;

public class Player {
    private Shelf shelf;

    public Shelf getShelf() {
        return shelf;
    }

    public void setShelf(Shelf shelf) {
        this.shelf = shelf;
    }

    public Player(Shelf shelf) {
        this.shelf = shelf;
    }
}
