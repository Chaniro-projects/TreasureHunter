package fr.bbaret.carbonit.treasurehunter.player;

public enum EOrientation {
    North,
    East,
    South,
    West;

    @Override
    public String toString() {
        switch (this) {
            case North:
                return "N";
            case East:
                return "E";
            case South:
                return "S";
            case West:
            default:
                return "W";
        }
    }
}
