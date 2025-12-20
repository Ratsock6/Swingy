package fr.aallouv.manager.map;

import java.util.ArrayList;

public class MapManager {

	private final int numberOfRoom;
	private final ArrayList<SlotMap> maps;

	public MapManager(int numberOfRoom) {
		this.numberOfRoom = numberOfRoom;
		this.maps = new ArrayList<>();

		new SlotMap(EMapRoom.START, 0, 0);

		ArrayList<CardinalPoint> cardinalPointsAvailable = new ArrayList<>();
		for (int i = 1; i < numberOfRoom; i++) {


			cardinalPointsAvailable.clear();
		}
	}

	public int getNumberOfRoom() {
		return numberOfRoom;
	}

	public ArrayList<SlotMap> getMaps() {
		return maps;
	}


	public ArrayList<CardinalPoint> getCardinalPointsAvailable(SlotMap slotMap) {
		ArrayList cardinalPoints = new ArrayList();

		for (SlotMap slotsMap : getMaps()) {
			int distanceX = slotMap.getCoordX() - slotsMap.getCoordX();
			int distanceY = slotMap.getCoordY() - slotsMap.getCoordY();
			if (!((distanceY >= -1 && distanceY <= 1) && (distanceX >= -1 && distanceX <= 1)))
				continue;
		}

		return  cardinalPoints;
	}

}
