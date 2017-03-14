package fr.bbaret.carbonit.treasurehunter.player;

import fr.bbaret.carbonit.treasurehunter.ihm.AssetsManager;
import fr.bbaret.carbonit.treasurehunter.ihm.IDrawable;
import fr.bbaret.carbonit.treasurehunter.map.Map;
import fr.bbaret.carbonit.treasurehunter.map.tile.Tile;
import fr.bbaret.carbonit.treasurehunter.player.command.ETurn;
import fr.bbaret.carbonit.treasurehunter.player.command.ForwardCommand;
import fr.bbaret.carbonit.treasurehunter.player.command.ICommand;
import fr.bbaret.carbonit.treasurehunter.player.command.TurnCommand;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Player implements IDrawable {
    private static java.util.Map<EOrientation, Image> images;
    private static Font nameFont;

    static {
        images = new HashMap<>(4);
        images.put(EOrientation.North, AssetsManager.getInstance().getImage("player_n.png"));
        images.put(EOrientation.East, AssetsManager.getInstance().getImage("player_e.png"));
        images.put(EOrientation.South, AssetsManager.getInstance().getImage("player_s.png"));
        images.put(EOrientation.West, AssetsManager.getInstance().getImage("player_w.png"));

        nameFont = new Font("Arial", Font.BOLD, 15);
    }

    private String name;
    private Point position;
    private EOrientation orientation;
    private List<ICommand> commands;
    private Thread thread;
    private Map map;
    private Rectangle2D textSize;
    private int treasures = 0;
    private IPlayerComplete playerCompleteListener;
    private boolean listenerCalled = false;

    public Player(String commands, Map map, IPlayerComplete playerCompleteListener) {
        this.map = map;
        this.playerCompleteListener = playerCompleteListener;
        String[] splittedCommands = commands.trim().split(" ");
        String[] splittedPosition = splittedCommands[1].split("-");
        this.name = splittedCommands[0];
        this.position = new Point(Integer.parseInt(splittedPosition[0]) - 1, Integer.parseInt(splittedPosition[1]) - 1);
        switch (splittedCommands[2]) {
            case "N":
                this.orientation = EOrientation.North;
                break;
            case "E":
                this.orientation = EOrientation.East;
                break;
            case "S":
                this.orientation = EOrientation.South;
                break;
            case "W":
                this.orientation = EOrientation.West;
                break;
        }

        this.commands = new ArrayList<>(splittedCommands[3].length());
        for (int i = 0; i < splittedCommands[3].length(); i++) {
            switch (splittedCommands[3].charAt(i)) {
                case 'A':
                    this.commands.add(new ForwardCommand(this));
                    break;
                case 'D':
                    this.commands.add(new TurnCommand(ETurn.Right, this));
                    break;
                case 'G':
                    this.commands.add(new TurnCommand(ETurn.Left, this));
                    break;
            }
        }

        this.thread = new Thread(() -> work());
        this.thread.start();
    }

    private void work() {
        try {
            while (true) {
                synchronized (PlayerManager.getInstance()) {
                    PlayerManager.getInstance().wait();
                    Tile tile = map.getTile(position);
                    if (map.getTile(position).hasTreasure()) {
                        tile.pickTreasure();
                        treasures++;
                    } else if (commands.size() == 0) {
                        if (!listenerCalled) {
                            listenerCalled = true;
                            playerCompleteListener.completed();
                        }
                    } else
                        while (commands.size() != 0 &&
                                !commands.remove(0).execute(map, PlayerManager.getInstance().getPlayers())) ;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public EOrientation getOrientation() {
        return orientation;
    }

    public String getName() {
        return name;
    }

    public Point getPosition() {
        return position;
    }

    public void setOrientation(EOrientation orientation) {
        this.orientation = orientation;
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        g.drawImage(images.get(orientation), (int) position.getX() * Map.TILE_WIDTH, (int) position.getY() * Map.TILE_HEIGHT, null);
        g2.setFont(nameFont);
        textSize = g2.getFontMetrics().getStringBounds(name, g2);
        g2.drawString(name, (int) ((int) position.getX() * Map.TILE_WIDTH + 32 - textSize.getWidth() / 2), (int) position.getY() * Map.TILE_HEIGHT);
    }

    @Override
    public String toString() {
        return name + " " + (int) (position.getX() + 1) + "-" + (int) (position.getY() + 1) + " " + orientation.toString() + " " + treasures;
    }

    public interface IPlayerComplete {
        void completed();
    }
}
