package fr.aallouv.manager.map;

import fr.aallouv.App;

import java.util.ArrayList;
import java.util.Random;

public class MapManager {

	private final int numberOfRoom;
	private final ArrayList<SlotMap> maps;

	public MapManager(int numberOfRoom) {
		this.numberOfRoom = numberOfRoom;
		this.maps = new ArrayList<>();

		new SlotMap(EMapRoom.START, 0, 0);


		for (int i = 1; i < numberOfRoom - 2; i++) {
			SlotMap slotMap = getMaps().get(new Random().nextInt(getMaps().size()));
			ArrayList<CardinalPoint> cardinalPointsAvailable = getCardinalPointsAvailable(slotMap.getCoordX(), slotMap.getCoordY());
			CardinalPoint selectedCardinalPoint = cardinalPointsAvailable.get(new Random().nextInt(cardinalPointsAvailable.size()));
			EMapRoom[] eMapRoomArrayList = EMapRoom.values();
			EMapRoom selectedRoom = eMapRoomArrayList[new Random().nextInt(eMapRoomArrayList.length - 2)];

			int finalX = slotMap.getCoordX() + selectedCardinalPoint.getAddX();
			int finalY = slotMap.getCoordY() + selectedCardinalPoint.getAddY();
			new SlotMap(selectedRoom, finalX, finalY);

		}

		//TODO: Continuer la logique. Problèmatique actuel faire attention que le point cardinal choisi soit possible pour placer la salle d'exit après. Qu'il ne soit pas entouré de salle aussi.
		SlotMap slotMap = getAvailableSlotMapToAddBossRoom().get(new Random().nextInt(getMaps().size()));
		ArrayList<CardinalPoint> cardinalPointsAvailable = getCardinalPointsAvailable(slotMap.getCoordX(), slotMap.getCoordY());


	}

	public int getNumberOfRoom() {
		return numberOfRoom;
	}

	public ArrayList<SlotMap> getMaps() {
		return maps;
	}


	public ArrayList<CardinalPoint> getCardinalPointsAvailable(int coordX, int coordY) {
		ArrayList<CardinalPoint> cardinalPoints = new ArrayList<>();
		cardinalPoints.add(CardinalPoint.NORTH);
		cardinalPoints.add(CardinalPoint.SOUTH);
		cardinalPoints.add(CardinalPoint.EAST);
		cardinalPoints.add(CardinalPoint.WEST);

		for (SlotMap slotsMap : getMaps()) {
			int distanceX = coordX - slotsMap.getCoordX();
			int distanceY = coordY - slotsMap.getCoordY();
			if (!((distanceY >= -1 && distanceY <= 1) && (distanceX >= -1 && distanceX <= 1)))
				continue;
			if (distanceX != 0 && distanceY != 0)
				continue;
			if (distanceX == 0 && distanceY == -1)
				cardinalPoints.remove(CardinalPoint.NORTH);
			else if (distanceX == 0 && distanceY == 1)
				cardinalPoints.remove(CardinalPoint.SOUTH);
			else if (distanceX == -1)
				cardinalPoints.remove(CardinalPoint.WEST);
			else if (distanceX == 1)
				cardinalPoints.remove(CardinalPoint.EAST);
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

}
