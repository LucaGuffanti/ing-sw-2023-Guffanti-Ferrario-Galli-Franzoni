package it.polimi.ingsw.model.cards;

import java.util.Set;

public class SubPattern {
    private int height;
    private int length;
    private Set<SubPatternCell> coveredCells;
    private int maxDifferentTypes;

    public SubPattern(int height, int length, Set<SubPatternCell> coveredCells , int maxDifferentTypes){
        this.height = height;
        this.length = length;
        this.maxDifferentTypes = maxDifferentTypes;
        this.coveredCells = coveredCells;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
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
