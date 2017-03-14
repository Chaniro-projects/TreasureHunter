package fr.bbaret.carbonit.treasurehunter.player.command;

import fr.bbaret.carbonit.treasurehunter.map.Map;
import fr.bbaret.carbonit.treasurehunter.player.Player;

import java.awt.*;
import java.util.List;

public class ForwardCommand implements ICommand {
    private Player player;

    public ForwardCommand(Player player) {

        this.player = player;
    }

    @Override
    public boolean execute(Map map, List<Player> players) {

        //Compute the new position
        Point newPosition = null;
        switch (player.getOrientation()) {
            case North:
                if (player.getPosition().getY() != 0)
                    newPosition = new Point((int) player.getPosition().getX(), (int) player.getPosition().getY() - 1);
                break;
            case East:
                if (player.getPosition().getX() < map.getWidth() - 1)
                    newPosition = new Point((int) player.getPosition().getX() + 1, (int) player.getPosition().getY());
                break;
            case South:
                if (player.getPosition().getY() < map.getHeight() - 1)
                    newPosition = new Point((int) player.getPosition().getX(), (int) player.getPosition().getY() + 1);
                break;
            case West:
                if (player.getPosition().getX() != 0)
                    newPosition = new Point((int) player.getPosition().getX() - 1, (int) player.getPosition().getY());
                break;
        }

        //Out of the map, try the next command
        if (newPosition == null)
            return false;

        //Trying to move into a mountain tile, try the next command
        if (!checkTileType(map, newPosition))
            return false;

        //Move to the new position only if there is no other players on it
        boolean moved;
        if (moved = checkcheckPlayer(players, newPosition)) {
            player.getPosition().setLocation(newPosition);
        }

        return moved;
    }

    private boolean checkTileType(Map map, Point newPosition) {
        return map.getTile(newPosition).isPracticable();
    }


    private boolean checkcheckPlayer(List<Player> players, Point newPosition) {
        for (Player otherPlayer : players)
            if (otherPlayer.getPosition().equals(newPosition))
                return false;
        return true;
    }
}
