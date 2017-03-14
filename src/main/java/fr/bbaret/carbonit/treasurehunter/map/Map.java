package fr.bbaret.carbonit.treasurehunter.map;

import fr.bbaret.carbonit.treasurehunter.ihm.AssetsManager;
import fr.bbaret.carbonit.treasurehunter.ihm.IDrawable;
import fr.bbaret.carbonit.treasurehunter.map.tile.ETileType;
import fr.bbaret.carbonit.treasurehunter.map.tile.Tile;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

public class Map implements IDrawable {
    public static final int TILE_WIDTH = 64;
    public static final int TILE_HEIGHT = 64;
    private static Image treasureImage;
    private static Font treasureFont;

    static {
        treasureImage = AssetsManager.getInstance().getImage("treasure.png");
        treasureFont = new Font("Arial", Font.BOLD, 15);
    }

    private List<Tile> tiles;
    private int width;
    private int height;
    private Rectangle2D textSize;

    public Map(int width, int height) {
        this.width = width;
        this.height = height;

        tiles = new ArrayList<>(width * height);
        for (int y = 0; y < height; y++)
            for (int x = 0; x < width; x++)
                tiles.add(TileFactory.getInstance().createTile(ETileType.Plain));
    }

    public Tile getTile(int x, int y) {
        return tiles.get(coordinatesToIndex(x, y));
    }

    public Tile getTile(Point p) {
        return getTile((int) p.getX(), (int) p.getY());
    }

    public void setTileType(int x, int y, ETileType newType) {
        tiles.set(coordinatesToIndex(x, y), TileFactory.getInstance().createTile(newType));
    }

    public void addTreasure(int x, int y, int number) {
        getTile(x, y).addTreasure(number);
    }

    private int coordinatesToIndex(int x, int y) {
        return y * width + x;
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        for (int y = 0; y < height; y++)
            for (int x = 0; x < width; x++) {
                g.drawImage(tiles.get(coordinatesToIndex(x, y)).getImage(), x * TILE_WIDTH, y * TILE_HEIGHT, null);

                if (tiles.get(coordinatesToIndex(x, y)).hasTreasure()) {
                    g.drawImage(treasureImage, x * TILE_WIDTH + 16, y * TILE_HEIGHT + 16, null);

                    g2.setFont(treasureFont);
                    g2.setColor(Color.white);
                    String treasures = tiles.get(coordinatesToIndex(x, y)).getTreasures() + "";
                    textSize = g2.getFontMetrics().getStringBounds(treasures, g2);
                    g2.drawString(treasures, (int) (x * TILE_WIDTH + TILE_WIDTH / 2 - (textSize.getWidth() / 2)), (float) (y * TILE_HEIGHT + Map.TILE_HEIGHT / 2 - textSize.getHeight() / 2 + 16));
                }
            }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
