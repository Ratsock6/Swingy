package fr.aallouv.manager.map;

import java.util.*;

public class MazeGenerator {

	private final Map<String, SlotMap> grid = new HashMap<>();
	private final Random random = new Random();
	private int roomCount = 0;

	public MazeGenerator(int maxRooms) {
		generate(maxRooms);
	}

	public void generate(int maxRooms) {
		Stack<SlotMap> stack = new Stack<>();
		SlotMap start = new SlotMap(EMapRoom.START, 0, 0);
		grid.put(getKey(0, 0), start);
		stack.push(start);
		roomCount++;

		while (!stack.isEmpty() && roomCount < maxRooms) {
			SlotMap current = stack.peek();
			List<CardinalPoint> availableDirections = getAvailableDirections(current);

			if (availableDirections.isEmpty()) {
				stack.pop();
			} else {
				CardinalPoint direction = availableDirections.get(random.nextInt(availableDirections.size()));
				int newX = current.getCoordX() + direction.getAddX();
				int newY = current.getCoordY() + direction.getAddY();

				SlotMap nextRoom = new SlotMap(EMapRoom.COMBAT, newX, newY); // Exemple : salle de combat
				grid.put(getKey(newX, newY), nextRoom);
				stack.push(nextRoom);
				roomCount++;
			}
		}
	}

	private List<CardinalPoint> getAvailableDirections(SlotMap current) {
		List<CardinalPoint> directions = new ArrayList<>(Arrays.asList(CardinalPoint.values()));
		directions.removeIf(direction -> {
			int newX = current.getCoordX() + direction.getAddX();
			int newY = current.getCoordY() + direction.getAddY();
			return grid.containsKey(getKey(newX, newY));
		});
		return directions;
	}

	private String getKey(int x, int y) {
		return x + "," + y;
	}

	public Map<String, SlotMap> getGrid() {
		return grid;
	}

	public void printMazeInTerminal() {
		int minX = grid.values().stream().mapToInt(SlotMap::getCoordX).min().orElse(0);
		int maxX = grid.values().stream().mapToInt(SlotMap::getCoordX).max().orElse(0);
		int minY = grid.values().stream().mapToInt(SlotMap::getCoordY).min().orElse(0);
		int maxY = grid.values().stream().mapToInt(SlotMap::getCoordY).max().orElse(0);

		for (int y = minY; y <= maxY; y++) {
			for (int x = minX; x <= maxX; x++) {
				SlotMap room = grid.get(getKey(x, y));
				if (room != null) {
					System.out.print("[" + room.geteMapRoom().name().charAt(0) + "]");
				} else {
					System.out.print("[ ]");
				}
			}
			System.out.println();
		}
	}
}