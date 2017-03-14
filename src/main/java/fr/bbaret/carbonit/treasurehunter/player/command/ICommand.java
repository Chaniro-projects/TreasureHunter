package fr.bbaret.carbonit.treasurehunter.player.command;

import fr.bbaret.carbonit.treasurehunter.map.Map;
import fr.bbaret.carbonit.treasurehunter.player.Player;

import java.util.List;

/**
 * Represent an action performed by a player
 */
public interface ICommand {

    /**
     * Execute the current action
     *
     * @param map
     * @param players
     * @return true if the action succeed
     */
    boolean execute(Map map, List<Player> players);
}
