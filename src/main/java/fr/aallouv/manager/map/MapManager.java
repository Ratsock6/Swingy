package fr.aallouv.manager.map;

import fr.aallouv.App;
import fr.aallouv.manager.map.room.Room;
import fr.aallouv.manager.map.room.StartRoom;

import java.util.ArrayList;
import java.util.Random;
import java.util.stream.Stream;

public class MapManager {

	private final int numberOfRoom;
	private final ArrayList<Room> maps;

	public MapManager() {
		this.numberOfRoom = App.getApp().getGameManager().getNumberOfRooms();
		this.maps = new ArrayList<>();
		App.getApp().getLogger().log("MapManager initialized to generate a map with " + numberOfRoom + " rooms.");
	}

	public MapManager(ArrayList<Room> maps) {
		this.numberOfRoom = maps.size();
		this.maps = maps;
		App.getApp().getLogger().log("MapManager initialized with existing map of " + maps.size() + " rooms.");
	}

	public void initMap() {
		new StartRoom(0, 0);

		for (int i = 1; i < numberOfRoom - 2; i++) {
			Room slotMap = getAvailableRoom().get(new Random().nextInt(getAvailableRoom().size()));
			App.getApp().getLogger().log("Selected SlotMap ID[" + slotMap.getId() + "] to add new room.");
			ArrayList<CardinalPoint> cardinalPointsAvailable = getCardinalPointsAvailable(slotMap.getX(), slotMap.getY());
			App.getApp().getLogger().log("Available cardinal points to add new room: " + cardinalPointsAvailable.toString());
			CardinalPoint selectedCardinalPoint = cardinalPointsAvailable.get(new Random().nextInt(cardinalPointsAvailable.size()));
			App.getApp().getLogger().log("Selected cardinal point: " + selectedCardinalPoint.name());
			EMapRoom[] eMapRoomArrayList = Stream.of(EMapRoom.values())
					.filter(room -> !room.isUnique())
					.toArray(EMapRoom[]::new);
			EMapRoom selectedRoom = eMapRoomArrayList[new Random().nextInt(eMapRoomArrayList.length)];
			int finalX = slotMap.getX() + selectedCardinalPoint.getAddX();
			int finalY = slotMap.getY() + selectedCardinalPoint.getAddY();
			Room.createRoom(finalX, finalY, selectedRoom);
			App.getApp().getLogger().log("Added new room of type " + selectedRoom.name() + " at (" + finalX + "," + finalY + ")");
			App.getApp().getLogger().space();
		}

		addUniqueRoom(EMapRoom.BOSS);
		addUniqueRoom(EMapRoom.EXIT);

		for (Room roomIter : getMaps()) {
			App.getApp().getLogger().log("Room[" + roomIter.getId() + "]: " + roomIter.getName() + " at (" + roomIter.getX() + "," + roomIter.getY() + ")");
		}

		printMapInLogFile();
		App.getApp().getLogger().log("Initial map generation complete. Total rooms generated: " + getMaps().size());
	}

	public void addUniqueRoom(EMapRoom eMapRoom) {
		Room room = getAvailableRoom().get(new Random().nextInt(getAvailableRoom().size()));
		App.getApp().getLogger().log("Selected SlotMap ID[" + room.getId() + "] to add new room.");
		ArrayList<CardinalPoint> cardinalPointsAvailable = getCardinalPointsAvailable(room.getX(), room.getY());
		App.getApp().getLogger().log("Available cardinal points to add new room: " + cardinalPointsAvailable.toString());
		CardinalPoint selectedCardinalPoint = cardinalPointsAvailable.get(new Random().nextInt(cardinalPointsAvailable.size()));
		App.getApp().getLogger().log("Selected cardinal point: " + selectedCardinalPoint.name());
		int finalX = room.getX() + selectedCardinalPoint.getAddX();
		int finalY = room.getY() + selectedCardinalPoint.getAddY();
		Room.createRoom(finalX, finalY, eMapRoom);
		App.getApp().getLogger().log("Added new room of type " + eMapRoom.getName() + " at (" + finalX + "," + finalY + ")");
	}

	public int getNumberOfRoom() {
		return numberOfRoom;
	}

	public ArrayList<Room> getMaps() {
		return maps;
	}

	public int getVisitedRoomsCount() {
		int count = 0;
		for (Room room : getMaps()) {
			if (room.isVisited()) {
				count++;
			}
		}
		return count;
	}

	public ArrayList<Room> getVisitedRooms() {
		ArrayList<Room> visitedRooms = new ArrayList<>();
		for (Room room : getMaps()) {
			if (room.isVisited()) {
				visitedRooms.add(room);
			}
		}
		return visitedRooms;
	}


	public ArrayList<CardinalPoint> getCardinalPointsAvailable(int coordX, int coordY) {
		ArrayList<CardinalPoint> cardinalPoints = new ArrayList<>();
		cardinalPoints.add(CardinalPoint.NORTH);
		cardinalPoints.add(CardinalPoint.SOUTH);
		cardinalPoints.add(CardinalPoint.EAST);
		cardinalPoints.add(CardinalPoint.WEST);

		for (Room slotsMap : getMaps()) {
			if (slotsMap.getX() == coordX && slotsMap.getY() == coordY + 1)
				cardinalPoints.remove(CardinalPoint.NORTH);
			if (slotsMap.getX() == coordX && slotsMap.getY() == coordY - 1)
				cardinalPoints.remove(CardinalPoint.SOUTH);
			if (slotsMap.getX() == coordX + 1 && slotsMap.getY() == coordY)
				cardinalPoints.remove(CardinalPoint.EAST);
			if (slotsMap.getX() == coordX - 1 && slotsMap.getY() == coordY)
				cardinalPoints.remove(CardinalPoint.WEST);
		}
		return cardinalPoints;
	}

	public ArrayList<CardinalPoint> getCardinalPointsHeroCanMove(int coordX, int coordY) {
		ArrayList<CardinalPoint> cardinalPoints = new ArrayList<>();
		cardinalPoints.add(CardinalPoint.NORTH);
		cardinalPoints.add(CardinalPoint.SOUTH);
		cardinalPoints.add(CardinalPoint.EAST);
		cardinalPoints.add(CardinalPoint.WEST);
		ArrayList<CardinalPoint> cardinalPointsEmpty = getCardinalPointsAvailable(coordX, coordY);
		for (CardinalPoint cardinalPoint : cardinalPointsEmpty) {
			cardinalPoints.remove(cardinalPoint);
		}
		return cardinalPoints;
	}

	public ArrayList<Room> getAvailableRoomToAddBossRoom() {
		ArrayList<Room> slotMapArrayList = new ArrayList<>();
		for (Room slots : getMaps()) {
			ArrayList<CardinalPoint> cardinalPointsAvailable = getCardinalPointsAvailable(slots.getX(), slots.getY());
			if (cardinalPointsAvailable.isEmpty())
				continue;
			for (CardinalPoint cardinalPointAvaialable :cardinalPointsAvailable) {
				if (getCardinalPointsAvailable(slots.getX() + cardinalPointAvaialable.getAddX(), slots.getY() + cardinalPointAvaialable.getAddY()).isEmpty())
					continue;
				slotMapArrayList.add(slots);
				break;
			}
		}
		return slotMapArrayList;
	}

	public ArrayList<Room> getAvailableRoom() {
		ArrayList<Room> lastGenerateSlotMap = new ArrayList<>();
		for (int i = getMaps().size() - 1; i >= 0 && i >= getMaps().size() - 5; i--) {
			if (!getCardinalPointsAvailable(getMaps().get(i).getX(), getMaps().get(i).getY()).isEmpty())
				lastGenerateSlotMap.add(getMaps().get(i));
		}
		while (lastGenerateSlotMap.isEmpty()) {
			Room slotMap = getMaps().get(new Random().nextInt(getMaps().size()));
			if (getCardinalPointsAvailable(slotMap.getX(), slotMap.getY()).isEmpty())
				continue;
			lastGenerateSlotMap.add(slotMap);
		}
		return lastGenerateSlotMap;
	}

	public void printMapInLogFile() {
		int absisMin = 0, abscisseMax = 0, ordonneMin = 0, ordonneMax = 0;
		for (Room slotMap : getMaps()) {
			if (slotMap.getX() < absisMin)
				absisMin = slotMap.getX();
			if (slotMap.getX() > abscisseMax)
				abscisseMax = slotMap.getX();
			if (slotMap.getY() < ordonneMin)
				ordonneMin = slotMap.getY();
			if (slotMap.getY() > ordonneMax)
				ordonneMax = slotMap.getY();
		}

		int width = abscisseMax - absisMin + 1;
		int height = ordonneMax - ordonneMin + 1;

		int max = Math.max(width, height);

		for (int y = 0; y < max; y++) {
			StringBuilder line = new StringBuilder();
			for (int x = 0; x < max; x++) {
				boolean found = false;
				for (Room slotMap : getMaps()) {
					if (slotMap.getX() == x + absisMin && slotMap.getY() == (ordonneMax - y)) {
						line.append("[").append(slotMap.getSymbol()).append("]");
						found = true;
						break;
					}
				}
				if (!found) {
					line.append("[ ]");
				}
			}
			App.getApp().getLogger().log(line.toString());
		}
	}

	public void viewVisitedRoom() {
		System.out.println("Map View (Visited Rooms):");
		int absisMin = 0, abscisseMax = 0, ordonneMin = 0, ordonneMax = 0;
		for (Room slotMap : getMaps()) {
			if (slotMap.getX() < absisMin)
				absisMin = slotMap.getX();
			if (slotMap.getX() > abscisseMax)
				abscisseMax = slotMap.getX();
			if (slotMap.getY() < ordonneMin)
				ordonneMin = slotMap.getY();
			if (slotMap.getY() > ordonneMax)
				ordonneMax = slotMap.getY();
		}

		int width = abscisseMax - absisMin + 1;
		int height = ordonneMax - ordonneMin + 1;

		int max = Math.max(width, height);

		for (int y = 0; y < max; y++) {
			StringBuilder line = new StringBuilder();
			for (int x = 0; x < max; x++) {
				boolean found = false;
				for (Room slotMap : getMaps()) {
					if (slotMap.getX() == x + absisMin && slotMap.getY() == (ordonneMax - y)) {
						if (!slotMap.isVisited()) {
							line.append("[ ]");
							found = true;
							break;
						}
						if (App.getApp().getGameManager().getHero().getX() == slotMap.getX() && App.getApp().getGameManager().getHero().getY() == slotMap.getY()) {
							line.append("[●]");
							found = true;
							break;
						}
						line.append("[").append(slotMap.getSymbol()).append("]");
						found = true;
						break;
					}
				}
				if (!found) {
					line.append("[ ]");
				}
			}
			App.getApp().getGameManager().printMessage(line.toString());
		}

	}

	public Room getRoomByCoord(int x, int y) {
		for (Room room : getMaps()) {
			if (room.getX() == x && room.getY() == y) {
				return room;
			}
		}
		return null;
	}

}
