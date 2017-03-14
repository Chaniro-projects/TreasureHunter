package fr.bbaret.carbonit.treasurehunter.ihm;

import fr.bbaret.carbonit.treasurehunter.map.Map;
import fr.bbaret.carbonit.treasurehunter.player.PlayerManager;

import javax.swing.*;
import java.awt.*;


public class GameDrawer extends JComponent {
    private Map map;

    public GameDrawer(Map map) {
        this.map = map;
        this.setPreferredSize(new Dimension(Map.TILE_WIDTH * map.getWidth(), Map.TILE_HEIGHT * map.getHeight()));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        map.draw(g);
        PlayerManager.getInstance().draw(g);
        Timer.getInstance().draw(g);
    }
}
