package fr.aallouv.swingy.view.gui;

import fr.aallouv.swingy.model.game.GameState;
import fr.aallouv.swingy.model.map.GameMap;
import fr.aallouv.swingy.model.map.Room;

import javax.swing.*;
import java.awt.*;

public class MinimapPanel extends JPanel {

    private GameState state;

    private static final int CELL_SIZE = 8;
    private static final Color COLOR_UNVISITED = new Color(40, 40, 40);
    private static final Color COLOR_VISITED = new Color(80, 80, 80);
    private static final Color COLOR_CURRENT = new Color(255, 220, 0);
    private static final Color COLOR_BOSS = new Color(180, 0, 0);
    private static final Color COLOR_EXIT = new Color(0, 0, 180);
    private static final Color COLOR_START = new Color(0, 140, 0);

    public MinimapPanel() {
        setBackground(Color.BLACK);
        setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY), "Carte"
        ));
    }

    public void setState(GameState state) {
        this.state = state;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (state == null) return;

        GameMap map = state.getMap();
        int size = map.getSize();

        int totalW = size * CELL_SIZE;
        int totalH = size * CELL_SIZE;
        int offsetX = (getWidth() - totalW) / 2;
        int offsetY = (getHeight() - totalH) / 2;

        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                Room room = map.getRoom(x, y);
                if (room == null) continue;

                int px = offsetX + x * CELL_SIZE;
                int py = offsetY + y * CELL_SIZE;

                if (x == state.getHeroX() && y == state.getHeroY()) {
                    g.setColor(COLOR_CURRENT);
                } else if (!room.isVisited()) {
                    g.setColor(COLOR_UNVISITED);
                } else {
                    g.setColor(getRoomColor(room.getRoomType()));
                }

                g.fillRect(px + 1, py + 1, CELL_SIZE - 2, CELL_SIZE - 2);
            }
        }
    }

    private Color getRoomColor(String type) {
        return switch (type) {
            case "START" -> COLOR_START;
            case "EXIT" -> COLOR_EXIT;
            case "BOSS" -> COLOR_BOSS;
            case "ELITE" -> new Color(120, 0, 120);
            case "COMBAT" -> new Color(140, 60, 0);
            case "REST" -> new Color(0, 100, 100);
            case "TRAP" -> new Color(100, 100, 0);
            case "CHOICE" -> new Color(60, 60, 120);
            case "DISTORTION" -> new Color(60, 0, 100);
            default -> COLOR_VISITED;
        };
    }
}
