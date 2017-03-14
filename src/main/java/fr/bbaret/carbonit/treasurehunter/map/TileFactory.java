package fr.bbaret.carbonit.treasurehunter.map;

import fr.bbaret.carbonit.treasurehunter.ihm.AssetsManager;
import fr.bbaret.carbonit.treasurehunter.map.tile.ETileType;
import fr.bbaret.carbonit.treasurehunter.map.tile.MountainTile;
import fr.bbaret.carbonit.treasurehunter.map.tile.PlainTile;
import fr.bbaret.carbonit.treasurehunter.map.tile.Tile;

import java.awt.*;
import java.util.HashMap;

public class TileFactory {

    private static TileFactory instance;

    public static synchronized TileFactory getInstance() {
        return instance == null ? instance = new TileFactory() : instance;
    }

    private java.util.Map<ETileType, Image> images;

    private TileFactory() {
        images = new HashMap<>();

        images.put(ETileType.Plain, AssetsManager.getInstance().getImage("plain.png"));
        images.put(ETileType.Mountain, AssetsManager.getInstance().getImage("mountain.png"));
    }

    public Tile createTile(ETileType tileType) {
        switch (tileType) {
            case Mountain:
                return new MountainTile(images.get(tileType));
            case Plain:
            default:
                return new PlainTile(images.get(tileType));
        }
    }
}
