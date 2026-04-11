package fr.aallouv.swingy.model.map;

import fr.aallouv.swingy.controller.GameController;

import java.util.EnumMap;
import java.util.Map;

public abstract class Room {

    private static int counter = 0;

    private final int id;
    private final int x;
    private final int y;
    private boolean visited;
    private final Map<Direction, Room> neighbors = new EnumMap<>(Direction.class);

    public Room(int x, int y) {
        this.id = counter++;
        this.x = x;
        this.y = y;
        this.visited = false;
    }

    public abstract void onEnter(GameController controller);

    public abstract String getRoomType();

    public void setNeighbor(Direction direction, Room room) {
        neighbors.put(direction, room);
    }

    public Room getNeighbor(Direction direction) {
        return neighbors.get(direction);
    }

    public boolean hasExit(Direction direction) {
        return neighbors.containsKey(direction);
    }

    public Map<Direction, Room> getNeighbors() {
        return neighbors;
    }

    public int getId() { return id; }
    public int getX() { return x; }
    public int getY() { return y; }
    public boolean isVisited() { return visited; }
    public void setVisited(boolean visited) { this.visited = visited; }

    public boolean isActivated() {
        return false;
    }
}
