package fr.bbaret.carbonit.treasurehunter.player;

import fr.bbaret.carbonit.treasurehunter.map.Map;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PlayerManager implements Player.IPlayerComplete {
    private static PlayerManager instance;

    public static synchronized PlayerManager getInstance() {
        return instance == null ? instance = new PlayerManager() : instance;
    }

    private List<Player> players;
    private int playerCompleted = 0;
    private IGameComplete gameCompleteListener;

    private PlayerManager() {
        players = new ArrayList<>();
    }

    public void loadAllPlayers(String playersFile, Map map) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(playersFile));
        String line;
        while ((line = br.readLine()) != null) {
            if (!line.startsWith("#"))
                createPlayer(line, map);
        }
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void draw(Graphics g) {
        for (Player player : players)
            player.draw(g);
    }

    private Player createPlayer(String commands, Map map) {
        Player player = new Player(commands, map, this);
        players.add(player);
        return player;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Player player : players)
            sb.append(player.toString()).append("\n");

        return sb.toString();
    }

    @Override
    public void completed() {
        playerCompleted++;
        if (playerCompleted == players.size())
            this.gameCompleteListener.end();
    }

    public void setGameCompleteListener(IGameComplete gameCompleteListener) {
        this.gameCompleteListener = gameCompleteListener;
    }

    /**
     * Fired when all players got rid of there actions
     */
    public interface IGameComplete {
        void end();
    }
}
