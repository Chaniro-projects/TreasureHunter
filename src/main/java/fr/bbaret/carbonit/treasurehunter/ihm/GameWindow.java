package fr.bbaret.carbonit.treasurehunter.ihm;

import fr.bbaret.carbonit.treasurehunter.map.Map;

import javax.swing.*;

public class GameWindow extends JFrame {

    private GameDrawer gameDrawer;

    public GameWindow(String title, Map map) {
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setTitle(title);

        gameDrawer = new GameDrawer(map);
        this.add(gameDrawer);
        this.setResizable(false);
        this.pack();
        this.setLocationRelativeTo(null);

        Timer.getInstance().registerGameWindow(this);
    }
}
