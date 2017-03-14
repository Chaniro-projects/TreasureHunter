package fr.bbaret.carbonit.treasurehunter.player.command;

import fr.bbaret.carbonit.treasurehunter.map.Map;
import fr.bbaret.carbonit.treasurehunter.player.EOrientation;
import fr.bbaret.carbonit.treasurehunter.player.Player;

import java.util.List;

public class TurnCommand implements ICommand {
    private final ETurn orientation;
    private final Player player;

    public TurnCommand(ETurn orientation, Player player) {

        this.orientation = orientation;
        this.player = player;
    }

    @Override
    public boolean execute(Map map, List<Player> players) {
        switch (player.getOrientation()) {
            case North:
                if (orientation == ETurn.Left)
                    player.setOrientation(EOrientation.West);
                else
                    player.setOrientation(EOrientation.East);
                break;
            case East:
                if (orientation == ETurn.Left)
                    player.setOrientation(EOrientation.North);
                else
                    player.setOrientation(EOrientation.South);
                break;
            case South:
                if (orientation == ETurn.Left)
                    player.setOrientation(EOrientation.East);
                else
                    player.setOrientation(EOrientation.West);
                break;
            case West:
                if (orientation == ETurn.Left)
                    player.setOrientation(EOrientation.South);
                else
                    player.setOrientation(EOrientation.North);
                break;
        }
        return true;
    }
}
