package fr.aallouv.swingy.model.map;

import fr.aallouv.swingy.model.entity.Hero;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GameMap {

    private final int size;
    private final Room[][] grid;
    private final Random random = new Random();

    public GameMap(Hero hero) {
        int level = hero.getLevel();
        this.size = (level - 1) * 5 + 10 - (level % 2);
        this.grid = new Room[size][size];
        generate();
    }

    private void generate() {
        int center = size / 2;

        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                grid[x][y] = randomRoom(x, y);
            }
        }

        grid[center][center] = new StartRoom(center, center);
        placeOnBorder(new ExitRoom(0, 0));
        placeRandom(new BossRoom(0, 0));

        linkNeighbors();
    }

    private Room randomRoom(int x, int y) {
        int roll = random.nextInt(100);
        if (roll < 40) return new CombatRoom(x, y);
        if (roll < 55) return new EliteRoom(x, y);
        if (roll < 65) return new RestRoom(x, y);
        if (roll < 75) return new TrapRoom(x, y);
        if (roll < 85) return new ChoiceRoom(x, y);
        return new DistortionRoom(x, y);
    }

    private void placeOnBorder(Room room) {
        List<int[]> borders = new ArrayList<>();
        for (int x = 0; x < size; x++) {
            borders.add(new int[]{x, 0});
            borders.add(new int[]{x, size - 1});
        }
        for (int y = 1; y < size - 1; y++) {
            borders.add(new int[]{0, y});
            borders.add(new int[]{size - 1, y});
        }
        Collections.shuffle(borders, random);
        int[] pos = borders.get(0);
        grid[pos[0]][pos[1]] = createRoomAt(room, pos[0], pos[1]);
    }

    private void placeRandom(Room room) {
        int center = size / 2;
        int x, y;
        do {
            x = random.nextInt(size);
            y = random.nextInt(size);
        } while (grid[x][y] instanceof StartRoom
                || grid[x][y] instanceof ExitRoom
                || (x == center && y == center));
        grid[x][y] = createRoomAt(room, x, y);
    }

    private Room createRoomAt(Room room, int x, int y) {
        if (room instanceof ExitRoom) return new ExitRoom(x, y);
        if (room instanceof BossRoom) return new BossRoom(x, y);
        return room;
    }

    private void linkNeighbors() {
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                Room room = grid[x][y];
                if (y > 0)        room.setNeighbor(Direction.NORTH, grid[x][y - 1]);
                if (y < size - 1) room.setNeighbor(Direction.SOUTH, grid[x][y + 1]);
                if (x < size - 1) room.setNeighbor(Direction.EAST,  grid[x + 1][y]);
                if (x > 0)        room.setNeighbor(Direction.WEST,  grid[x - 1][y]);
            }
        }
    }

    public Room getRoom(int x, int y) {
        if (x < 0 || x >= size || y < 0 || y >= size) return null;
        return grid[x][y];
    }

    public Room getStartRoom() {
        int center = size / 2;
        return grid[center][center];
    }

    public int getSize() {
        return size;
    }

    public boolean isOnBorder(int x, int y) {
        return x == 0 || y == 0 || x == size - 1 || y == size - 1;
    }
}
