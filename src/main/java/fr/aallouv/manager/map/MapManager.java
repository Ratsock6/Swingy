package fr.aallouv.manager.map;

import fr.aallouv.App;
import fr.aallouv.utils.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.stream.Stream;

public class MapManager {

	private final int numberOfRoom;
	private final ArrayList<SlotMap> maps;

	public MapManager() {
		this.numberOfRoom = App.getApp().getGameManager().getNumberOfRooms();
		this.maps = new ArrayList<>();
		App.getApp().getLogger().log("MapManager initialized to generate a map with " + numberOfRoom + " rooms.");
	}

	public MapManager(ArrayList<SlotMap> maps) {
		this.numberOfRoom = maps.size();
		this.maps = maps;
		App.getApp().getLogger().log("MapManager initialized with existing map of " + maps.size() + " rooms.");
	}

	public void initMap() {
		new SlotMap(EMapRoom.START, 0, 0);

		for (int i = 1; i < numberOfRoom - 2; i++) {
			SlotMap slotMap = getAvailableSlotMapToAddNewSlotMap().get(new Random().nextInt(getAvailableSlotMapToAddNewSlotMap().size()));
			App.getApp().getLogger().log("Selected SlotMap ID[" + slotMap.getId() + "] to add new room.");
			ArrayList<CardinalPoint> cardinalPointsAvailable = getCardinalPointsAvailable(slotMap.getCoordX(), slotMap.getCoordY());
			App.getApp().getLogger().log("Available cardinal points to add new room: " + cardinalPointsAvailable.toString());
			CardinalPoint selectedCardinalPoint = cardinalPointsAvailable.get(new Random().nextInt(cardinalPointsAvailable.size()));
			App.getApp().getLogger().log("Selected cardinal point: " + selectedCardinalPoint.name());
			EMapRoom[] eMapRoomArrayList = Stream.of(EMapRoom.values())
					.filter(room -> !room.isUnique())
					.toArray(EMapRoom[]::new);
			EMapRoom selectedRoom = eMapRoomArrayList[new Random().nextInt(eMapRoomArrayList.length)];
			int finalX = slotMap.getCoordX() + selectedCardinalPoint.getAddX();
			int finalY = slotMap.getCoordY() + selectedCardinalPoint.getAddY();
			new SlotMap(selectedRoom, finalX, finalY);
			App.getApp().getLogger().space();

		}
		for (SlotMap slotMapIter : getMaps()) {
			App.getApp().getLogger().log("Room[" + slotMapIter.getId() + "]: " + slotMapIter.geteMapRoom().name() + " at (" + slotMapIter.getCoordX() + "," + slotMapIter.getCoordY() + ")");
		}

		printMapInLogFile();
		App.getApp().getLogger().log("Initial map generation complete. Total rooms generated: " + getMaps().size());
	}

	public int getNumberOfRoom() {
		return numberOfRoom;
	}

	public ArrayList<SlotMap> getMaps() {
		return maps;
	}

	public int getVisitedRoomsCount() {
		int count = 0;
		for (SlotMap slotMap : getMaps()) {
			if (slotMap.isVisited()) {
				count++;
			}
		}
		return count;
	}

	public int getMonsterDefeatedRoomsCount() {
		int count = 0;
		for (SlotMap slotMap : getMaps()) {
			if (slotMap.getMonster() != null && !slotMap.getMonster().isAlive()) {
				count++;
			}
		}
		return count;
	}


	public ArrayList<CardinalPoint> getCardinalPointsAvailable(int coordX, int coordY) {
		ArrayList<CardinalPoint> cardinalPoints = new ArrayList<>();
		cardinalPoints.add(CardinalPoint.NORTH);
		cardinalPoints.add(CardinalPoint.SOUTH);
		cardinalPoints.add(CardinalPoint.EAST);
		cardinalPoints.add(CardinalPoint.WEST);

		for (SlotMap slotsMap : getMaps()) {
			if (slotsMap.getCoordX() == coordX && slotsMap.getCoordY() == coordY + 1)
				cardinalPoints.remove(CardinalPoint.NORTH);
			if (slotsMap.getCoordX() == coordX && slotsMap.getCoordY() == coordY - 1)
				cardinalPoints.remove(CardinalPoint.SOUTH);
			if (slotsMap.getCoordX() == coordX + 1 && slotsMap.getCoordY() == coordY)
				cardinalPoints.remove(CardinalPoint.EAST);
			if (slotsMap.getCoordX() == coordX - 1 && slotsMap.getCoordY() == coordY)
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

	public ArrayList<SlotMap> getAvailableSlotMapToAddBossRoom() {
		ArrayList<SlotMap> slotMapArrayList = new ArrayList<>();
		for (SlotMap slots : getMaps()) {
			ArrayList<CardinalPoint> cardinalPointsAvailable = getCardinalPointsAvailable(slots.getCoordX(), slots.getCoordY());
			if (cardinalPointsAvailable.isEmpty())
				continue;
			for (CardinalPoint cardinalPointAvaialable :cardinalPointsAvailable) {
				if (getCardinalPointsAvailable(slots.getCoordX() + cardinalPointAvaialable.getAddX(), slots.getCoordY() + cardinalPointAvaialable.getAddY()).isEmpty())
					continue;
				slotMapArrayList.add(slots);
				break;
			}
		}
		return slotMapArrayList;
	}

	public ArrayList<SlotMap> getAvailableSlotMapToAddNewSlotMap() {
		ArrayList<SlotMap> lastGenerateSlotMap = new ArrayList<>();
		for (int i = getMaps().size() - 1; i >= 0 && i >= getMaps().size() - 5; i--) {
			if (!getCardinalPointsAvailable(getMaps().get(i).getCoordX(), getMaps().get(i).getCoordY()).isEmpty())
				lastGenerateSlotMap.add(getMaps().get(i));
		}
		while (lastGenerateSlotMap.isEmpty()) {
			SlotMap slotMap = getMaps().get(new Random().nextInt(getMaps().size()));
			if (getCardinalPointsAvailable(slotMap.getCoordX(), slotMap.getCoordY()).isEmpty())
				continue;
			lastGenerateSlotMap.add(slotMap);
		}
		return lastGenerateSlotMap;
	}

	public void printMapInLogFile() {
		int absisMin = 0, abscisseMax = 0, ordonneMin = 0, ordonneMax = 0;
		for (SlotMap slotMap : getMaps()) {
			if (slotMap.getCoordX() < absisMin)
				absisMin = slotMap.getCoordX();
			if (slotMap.getCoordX() > abscisseMax)
				abscisseMax = slotMap.getCoordX();
			if (slotMap.getCoordY() < ordonneMin)
				ordonneMin = slotMap.getCoordY();
			if (slotMap.getCoordY() > ordonneMax)
				ordonneMax = slotMap.getCoordY();
		}

		int width = abscisseMax - absisMin + 1;
		int height = ordonneMax - ordonneMin + 1;

		int max = Math.max(width, height);

		for (int y = 0; y < max; y++) {
			StringBuilder line = new StringBuilder();
			for (int x = 0; x < max; x++) {
				boolean found = false;
				for (SlotMap slotMap : getMaps()) {
					if (slotMap.getCoordX() == x + absisMin && slotMap.getCoordY() == (ordonneMax - y)) {
						line.append("[").append(slotMap.geteMapRoom().getSymbol()).append("]");
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
		for (SlotMap slotMap : getMaps()) {
			if (slotMap.getCoordX() < absisMin)
				absisMin = slotMap.getCoordX();
			if (slotMap.getCoordX() > abscisseMax)
				abscisseMax = slotMap.getCoordX();
			if (slotMap.getCoordY() < ordonneMin)
				ordonneMin = slotMap.getCoordY();
			if (slotMap.getCoordY() > ordonneMax)
				ordonneMax = slotMap.getCoordY();
		}

		int width = abscisseMax - absisMin + 1;
		int height = ordonneMax - ordonneMin + 1;

		int max = Math.max(width, height);

		for (int y = 0; y < max; y++) {
			StringBuilder line = new StringBuilder();
			for (int x = 0; x < max; x++) {
				boolean found = false;
				for (SlotMap slotMap : getMaps()) {
					if (slotMap.getCoordX() == x + absisMin && slotMap.getCoordY() == (ordonneMax - y)) {
						if (!slotMap.isVisited()) {
							line.append("[ ]");
							found = true;
							break;
						}
						if (App.getApp().getGameManager().getHero().getX() == slotMap.getCoordX() && App.getApp().getGameManager().getHero().getY() == slotMap.getCoordY()) {
							line.append("[\uD83D\uDFE2]");
							found = true;
							break;
						}
						line.append("[").append(slotMap.geteMapRoom().getSymbol()).append("]");
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

	public SlotMap getSlotMapByCoordinates(int x, int y) {
		for (SlotMap slotMap : getMaps()) {
			if (slotMap.getCoordX() == x && slotMap.getCoordY() == y) {
				return slotMap;
			}
		}
		return null;
	}

}
