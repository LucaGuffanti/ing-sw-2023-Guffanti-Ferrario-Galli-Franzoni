package it.polimi.ingsw.model.cards;

import java.util.Set;

public class SubPattern {
    private int height;
    private int lenght;
    private Set<SubPatternCell> coveredCells;
    private int maxDifferentTypes;

    public SubPattern(int height, int lenght, Set<SubPatternCell> coveredCells , int maxDifferentTypes){
        this.height = height;
        this.lenght = lenght;
        this.maxDifferentTypes = maxDifferentTypes;
        this.coveredCells = coveredCells;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getLenght() {
        return lenght;
    }

    public void setLenght(int lenght) {
        this.lenght = lenght;
    }

    public Set<SubPatternCell> getCoveredCells() {
        return coveredCells;
    }

    public void setCoveredCells(Set<SubPatternCell> coveredCells) {
        this.coveredCells = coveredCells;
    }

    public int getMaxDifferentTypes() {
        return maxDifferentTypes;
    }

    public void setMaxDifferentTypes(int maxDifferentTypes) {
        this.maxDifferentTypes = maxDifferentTypes;
    }
}
