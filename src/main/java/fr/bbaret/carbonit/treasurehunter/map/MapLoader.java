package fr.bbaret.carbonit.treasurehunter.map;

import fr.bbaret.carbonit.treasurehunter.map.tile.ETileType;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class MapLoader {
    private static MapLoader instance;

    public static synchronized MapLoader getInstance() {
        return instance == null ? instance = new MapLoader() : instance;
    }

    private MapLoader() {
    }

    public Map loadMap(String mapFile) throws IOException, MapFileFormatException {
        Map map = null;

        BufferedReader br = new BufferedReader(new FileReader(mapFile));
        String line;
        String[] coordinates;
        while ((line = br.readLine()) != null) {
            String[] lineParts = line.trim().replace(" {2,}", " ").split(" ");
            switch (lineParts[0]) {
                case "C":
                    map = new Map(Integer.parseInt(lineParts[1]), Integer.parseInt(lineParts[2]));
                    break;
                case "T":
                    if (map == null)
                        throw new MapFileFormatException("Can't add treasure before fr.bbaret.carbonit.treasurehunter.map creation ('T' before 'C')");

                    coordinates = lineParts[1].split("-");
                    map.addTreasure(Integer.parseInt(coordinates[0]) - 1, Integer.parseInt(coordinates[1]) - 1, Integer.parseInt(lineParts[2]));
                    break;
                case "M":
                    if (map == null)
                        throw new MapFileFormatException("Can't add mountain before fr.bbaret.carbonit.treasurehunter.map creation ('M' before 'C')");

                    coordinates = lineParts[1].split("-");
                    map.setTileType(Integer.parseInt(coordinates[0]) - 1, Integer.parseInt(coordinates[1]) - 1, ETileType.Mountain);
                    break;
            }
        }

        return map;
    }
}
