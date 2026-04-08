package fr.aallouv.swingy.view.gui;

import fr.aallouv.swingy.model.map.Direction;
import fr.aallouv.swingy.model.map.Room;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class RoomPanel extends JPanel {

    private Room currentRoom;
    private List<Direction> availableDirections;

    public RoomPanel() {
        setBackground(Color.DARK_GRAY);
    }

    public void setRoom(Room room, List<Direction> directions) {
        this.currentRoom = room;
        this.availableDirections = directions;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (currentRoom == null) return;

        int w = getWidth();
        int h = getHeight();

        // Fond selon le type de salle
        g.setColor(getRoomColor(currentRoom.getRoomType()));
        g.fillRect(w / 4, h / 4, w / 2, h / 2);

        // Nom du type
        g.setColor(Color.WHITE);
        g.setFont(new Font("Monospaced", Font.BOLD, 14));
        FontMetrics fm = g.getFontMetrics();
        String label = currentRoom.getRoomType();
        g.drawString(label, (w - fm.stringWidth(label)) / 2, h / 2);

        // Portes
        g.setColor(Color.YELLOW);
        g.setFont(new Font("Monospaced", Font.PLAIN, 12));
        if (availableDirections != null) {
            for (Direction d : availableDirections) {
                switch (d) {
                    case NORTH -> g.drawString("▲", w / 2 - 6, h / 4 - 5);
                    case SOUTH -> g.drawString("▼", w / 2 - 6, h * 3 / 4 + 15);
                    case EAST  -> g.drawString("▶", w * 3 / 4 + 5, h / 2);
                    case WEST  -> g.drawString("◀", w / 4 - 15, h / 2);
                }
            }
        }
    }

    private Color getRoomColor(String type) {
        return switch (type) {
            case "START"      -> new Color(0, 100, 0);
            case "EXIT"       -> new Color(0, 0, 150);
            case "BOSS"       -> new Color(120, 0, 0);
            case "ELITE"      -> new Color(100, 0, 100);
            case "COMBAT"     -> new Color(140, 60, 0);
            case "REST"       -> new Color(0, 80, 80);
            case "TRAP"       -> new Color(80, 80, 0);
            case "CHOICE"     -> new Color(60, 60, 60);
            case "DISTORTION" -> new Color(40, 0, 80);
            default           -> Color.GRAY;
        };
    }
}
