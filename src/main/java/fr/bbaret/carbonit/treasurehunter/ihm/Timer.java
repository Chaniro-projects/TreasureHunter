package fr.bbaret.carbonit.treasurehunter.ihm;

import fr.bbaret.carbonit.treasurehunter.player.PlayerManager;

import java.awt.*;

/**
 * "Wake up" players every seconds and show time indicator.
 */
public class Timer implements IDrawable {

    private static Timer instance;

    public static synchronized Timer getInstance() {
        return instance == null ? instance = new Timer() : instance;
    }

    private Image bg;
    private Image inside;
    private Thread thread;
    private boolean started;
    private GameWindow gameWindow;
    private double lastUpdate;
    private double percent;

    public Timer() {
        bg = AssetsManager.getInstance().getImage("bar.png");
        inside = AssetsManager.getInstance().getImage("bar-inside.png");
        thread = new Thread(this::work);
    }

    public void registerGameWindow(GameWindow gw) {
        this.gameWindow = gw;
    }

    public void start() {
        if (!started) {
            started = true;
            thread.start();
        }
    }

    private void work() {
        while (true) {
            lastUpdate = System.currentTimeMillis();
            try {
                while (System.currentTimeMillis() < lastUpdate + 1000) {
                    gameWindow.repaint();
                    percent = 1 - (((lastUpdate + 1000) - System.currentTimeMillis()) / 1000);
                    Thread.sleep(1000 / 60);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            synchronized (PlayerManager.getInstance()) {
                PlayerManager.getInstance().notifyAll();
            }
        }
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(bg, 10, 10, null);
        g.drawImage(inside, 13, 13, (int) (109 * percent), 13, null);
    }
}
