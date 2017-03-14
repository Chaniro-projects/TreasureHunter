package fr.bbaret.carbonit.treasurehunter.map.tile;

import java.awt.*;

public class MountainTile extends Tile {

    public MountainTile(Image image) {
        super(image);
    }

    @Override
    public String toString() {
        return "M" + treasures;
    }

    @Override
    public boolean isPracticable() {
        return false;
    }
}
