package fr.bbaret.carbonit.treasurehunter.map.tile;

import java.awt.*;

public class PlainTile extends Tile {

    public PlainTile(Image image) {
        super(image);
    }

    @Override
    public String toString() {
        return "P" + treasures;
    }

    @Override
    public boolean isPracticable() {
        return true;
    }
}
