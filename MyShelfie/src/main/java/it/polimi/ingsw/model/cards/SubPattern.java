package it.polimi.ingsw.model.cards;

import java.util.Set;

public class SubPattern {
    private int height;
    private int length;
    private Set<SubPatternCell> coveredCells;
    private int maxDifferentTypes;
    private int minDifferentTypes;

    private boolean isRadiallySymmetric;

    public SubPattern(int height, int length, Set<SubPatternCell> coveredCells, int minDifferentTypes, int maxDifferentTypes, boolean isRadiallySymmetric) {
        this.height = height;
        this.length = length;
        this.coveredCells = coveredCells;
        this.maxDifferentTypes = maxDifferentTypes;
        this.minDifferentTypes = minDifferentTypes;
        this.isRadiallySymmetric = isRadiallySymmetric;
    }

    public int getMinDifferentTypes() {
        return minDifferentTypes;
    }

    public void setMinDifferentTypes(int minDifferentTypes) {
        this.minDifferentTypes = minDifferentTypes;
    }

    public boolean isRadiallySymmetric() {
        return isRadiallySymmetric;
    }

    public void setRadiallySymmetric(boolean radiallySymmetric) {
        isRadiallySymmetric = radiallySymmetric;
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
