package fr.bbaret.carbonit.treasurehunter.map.tile;

import java.awt.*;

public abstract class Tile {
    protected int treasures;
    protected Image image;

    public Tile(Image image) {
        treasures = 0;
        this.image = image;
    }

    public void addTreasure(int number) {
        treasures += number;
    }

    public Image getImage() {
        return image;
    }

    @Override
    public String toString() {
        return "?" + treasures;
    }

    public abstract boolean isPracticable();

    public boolean hasTreasure() {
        return treasures != 0;
    }

    public void pickTreasure() {
        treasures--;
    }

    public int getTreasures() {
        return treasures;
    }
}
