package fr.aallouv.swingy.util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class AssetLoader {

    private static final Map<String, BufferedImage> cache = new HashMap<>();

    public static BufferedImage load(String path) {
        if (cache.containsKey(path)) return cache.get(path);
        try (InputStream is = AssetLoader.class.getResourceAsStream(path)) {
            if (is == null) {
                System.err.println("Asset non trouvé : " + path);
                return null;
            }
            BufferedImage img = ImageIO.read(is);
            cache.put(path, img);
            return img;
        } catch (IOException e) {
            System.err.println("Erreur chargement asset : " + path);
            return null;
        }
    }
}
