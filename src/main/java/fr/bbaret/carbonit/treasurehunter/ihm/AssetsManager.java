package fr.bbaret.carbonit.treasurehunter.ihm;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Manage all type of assets. Avoid loading files multiple times
 */
public class AssetsManager {

    private static AssetsManager instance;

    public static synchronized AssetsManager getInstance() {
        return instance == null ? instance = new AssetsManager() : instance;
    }

    private Map<String, Image> images;

    private AssetsManager() {
        images = new HashMap<>();
    }

    public Image getImage(String file) {
        if (!images.containsKey(file)) {
            try {
                images.put(file, ImageIO.read(new File("assets/" + file)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return images.get(file);
    }
}
