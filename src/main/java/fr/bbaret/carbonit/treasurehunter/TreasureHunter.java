package fr.bbaret.carbonit.treasurehunter;

import fr.bbaret.carbonit.treasurehunter.ihm.GameWindow;
import fr.bbaret.carbonit.treasurehunter.ihm.Timer;
import fr.bbaret.carbonit.treasurehunter.map.Map;
import fr.bbaret.carbonit.treasurehunter.map.MapLoader;
import fr.bbaret.carbonit.treasurehunter.player.PlayerManager;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class TreasureHunter implements KeyListener, MouseListener, PlayerManager.IGameComplete {
    public static void main(String[] args) {
        if (args.length != 2)
            System.out.println("Usage: java -jar treasurehunter.jar [map_file] [players_file]");
        else
            SwingUtilities.invokeLater(() -> new TreasureHunter(args).start());
    }

    private GameWindow gameWindow;
    private Map map;

    public TreasureHunter(String[] args) {
        try {
            map = MapLoader.getInstance().loadMap(args[0]);
            PlayerManager.getInstance().loadAllPlayers(args[1], map);

            gameWindow = new GameWindow("Treasure Hunter", map);
            gameWindow.addKeyListener(this);
            gameWindow.addMouseListener(this);

            PlayerManager.getInstance().setGameCompleteListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void start() {
        gameWindow.setVisible(true);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        Timer.getInstance().start();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Timer.getInstance().start();
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void end() {
        String results = PlayerManager.getInstance().toString();

        System.out.println("------- Results -------");
        System.out.println(results);

        try (PrintWriter out = new PrintWriter("results.txt")) {
            out.println(results);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }
}
