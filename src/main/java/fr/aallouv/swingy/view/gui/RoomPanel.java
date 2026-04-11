package fr.aallouv.swingy.view.gui;

import fr.aallouv.swingy.model.entity.HeroClass;
import fr.aallouv.swingy.model.game.GameState;
import fr.aallouv.swingy.model.map.Direction;
import fr.aallouv.swingy.model.map.ExitRoom;
import fr.aallouv.swingy.model.map.Room;
import fr.aallouv.swingy.util.AssetLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;

public class RoomPanel extends JPanel {

    private Room currentRoom;
    private List<Direction> availableDirections;
    private GameState gameState;

    private static final Map<String, String> DIRECTION_KEY_TO_FILE = new HashMap<>();

    static {
        DIRECTION_KEY_TO_FILE.put("E",    "E");
        DIRECTION_KEY_TO_FILE.put("N",    "N");
        DIRECTION_KEY_TO_FILE.put("O",    "O");
        DIRECTION_KEY_TO_FILE.put("S",    "S");
        DIRECTION_KEY_TO_FILE.put("EN",   "EN");
        DIRECTION_KEY_TO_FILE.put("NO",   "NO");
        DIRECTION_KEY_TO_FILE.put("NS",   "NS");
        DIRECTION_KEY_TO_FILE.put("EO",   "OE");
        DIRECTION_KEY_TO_FILE.put("ES",   "SE");
        DIRECTION_KEY_TO_FILE.put("OS",   "SO");
        DIRECTION_KEY_TO_FILE.put("ENO",  "NEO");
        DIRECTION_KEY_TO_FILE.put("ENS",  "ENS");
        DIRECTION_KEY_TO_FILE.put("NOS",  "NSO");
        DIRECTION_KEY_TO_FILE.put("EOS",  "SOE");
        DIRECTION_KEY_TO_FILE.put("ENOS", "NSOE");
    }

    public RoomPanel() {
        setBackground(Color.BLACK);
    }

    public void setRoom(Room room, List<Direction> directions, GameState state) {
        this.currentRoom = room;
        this.availableDirections = directions;
        this.gameState = state;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (currentRoom == null) return;

        int w = getWidth();
        int h = getHeight();

        drawRoomTile(g, w, h);
        drawTheme(g, w, h);
        drawCharacter(g, w, h);
    }

    private void drawRoomTile(Graphics g, int w, int h) {
        String key = buildDirectionKey();
        String fileKey = DIRECTION_KEY_TO_FILE.getOrDefault(key, "N");
        BufferedImage tile = AssetLoader.load("/map/rooms/room_" + fileKey + ".png");
        if (tile != null) {
            g.drawImage(tile, 0, 0, w, h, null);
        } else {
            g.setColor(Color.DARK_GRAY);
            g.fillRect(0, 0, w, h);
        }
    }

    private void drawTheme(Graphics g, int w, int h) {
        String folder = getThemeFolder();
        if (folder == null) return;

        boolean activated = isRoomActivated();
        String suffix = activated ? "_2" : "_1";
        BufferedImage theme = AssetLoader.load("/map/themes/" + folder + "/" + folder + suffix + ".png");
        if (theme == null) return;

        int size = Math.min(w, h) / 2;
        int x = (w - size) / 2;
        int y = (h - size) / 2 - size / 6;
        g.drawImage(theme, x, y, size, size, null);
    }

    private void drawCharacter(Graphics g, int w, int h) {
        if (gameState == null) return;
        HeroClass heroClass = gameState.getHero().getHeroClass();
        Direction lastDir = gameState.getLastMoveDirection();

        String spriteSuffix = switch (lastDir) {
            case NORTH -> "back";
            case SOUTH -> "front";
            case EAST  -> "right";
            case WEST  -> "left";
        };

        String folder = heroClass.name().toLowerCase();
        BufferedImage sprite = AssetLoader.load("/map/characters/" + folder + "/" + folder + "_" + spriteSuffix + ".png");
        if (sprite == null) return;

        int size = Math.min(w, h) / 4;

        int x = switch (lastDir) {
            case NORTH -> (w - size) / 2;
            case SOUTH -> (w - size) / 2;
            case EAST  -> w / 6;
            case WEST  -> w - w / 6 - size;
        };

        int y = switch (lastDir) {
            case NORTH -> h - h / 6 - size;
            case SOUTH -> h / 6;
            case EAST  -> (h - size) / 2;
            case WEST  -> (h - size) / 2;
        };

        g.drawImage(sprite, x, y, size, size, null);
    }

    private String buildDirectionKey() {
        if (availableDirections == null) return "N";
        List<String> letters = new ArrayList<>();
        for (Direction d : availableDirections) {
            letters.add(switch (d) {
                case NORTH -> "N";
                case SOUTH -> "S";
                case EAST  -> "E";
                case WEST  -> "O";
            });
        }
        Collections.sort(letters);
        StringBuilder sb = new StringBuilder();
        for (String l : letters) sb.append(l);
        return sb.toString();
    }

    private String getThemeFolder() {
        if (currentRoom == null) return null;
        return switch (currentRoom.getRoomType()) {
            case "COMBAT"     -> "combat";
            case "ELITE"      -> "elite";
            case "BOSS"       -> "boss";
            case "TRAP"       -> "piege";
            case "REST"       -> "repos";
            case "CHOICE"     -> "choice";
            case "DISTORTION" -> "distortion";
            case "COFFRE"     -> "coffre";
            case "EXIT"       -> "exit";
            default           -> null;
        };
    }

    private boolean isRoomActivated() {
        if (currentRoom instanceof ExitRoom) {
            return gameState != null && gameState.isBossDefeated();
        }
        return currentRoom.isActivated();
    }
}
