package fr.aallouv.manager.map;

import fr.aallouv.App;
import fr.aallouv.utils.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class MapManager {

	private final int numberOfRoom;
	private final ArrayList<SlotMap> maps;

	public MapManager(int numberOfRoom) {
		this.numberOfRoom = numberOfRoom;
		this.maps = new ArrayList<>();
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
			EMapRoom[] eMapRoomArrayList = EMapRoom.values();
			EMapRoom selectedRoom = eMapRoomArrayList[new Random().nextInt(eMapRoomArrayList.length - 2)];

			int finalX = slotMap.getCoordX() + selectedCardinalPoint.getAddX();
			int finalY = slotMap.getCoordY() + selectedCardinalPoint.getAddY();
			new SlotMap(selectedRoom, finalX, finalY);
			App.getApp().getLogger().space();

		}

		//TODO: Continuer la logique. Problèmatique actuel faire attention que le point cardinal choisi soit possible pour placer la salle d'exit après. Qu'il ne soit pas entouré de salle aussi.
//		SlotMap slotMap = getAvailableSlotMapToAddBossRoom().get(new Random().nextInt(getMaps().size()));
//		ArrayList<CardinalPoint> cardinalPointsAvailable = getCardinalPointsAvailable(slotMap.getCoordX(), slotMap.getCoordY());
//		Collections.shuffle(cardinalPointsAvailable);
//		for (CardinalPoint cardinalPointAvaialable : cardinalPointsAvailable) {
//			int finalX = slotMap.getCoordX() + cardinalPointAvaialable.getAddX();
//			int finalY = slotMap.getCoordY() + cardinalPointAvaialable.getAddY();
//			if (getCardinalPointsAvailable(finalX, finalY).isEmpty())
//				continue;
//			SlotMap bossSlot = new SlotMap(EMapRoom.BOSS, finalX, finalY);
//			ArrayList<CardinalPoint> cardinalPointsAvailableForExit = getCardinalPointsAvailable(bossSlot.getCoordX(), bossSlot.getCoordY());
//			Collections.shuffle(cardinalPointsAvailableForExit);
//			int finalExitX = bossSlot.getCoordX() + cardinalPointsAvailableForExit.get(0).getAddX();
//			int finalExitY = bossSlot.getCoordY() + cardinalPointsAvailableForExit.get(0).getAddY();
//			new SlotMap(EMapRoom.EXIT, finalExitX, finalExitY);
//			break;
//		}

		for (SlotMap slotMapIter : getMaps()) {
			App.getApp().getLogger().log("Room[" + slotMapIter.getId() + "]: " + slotMapIter.geteMapRoom().name() + " at (" + slotMapIter.getCoordX() + "," + slotMapIter.getCoordY() + ")");
		}
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

	public void printMapInTerminal() {
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
			System.out.println(line);
		}

//		for (int y = ordonneMax; y >= ordonneMin; y--) {
//			StringBuilder line = new StringBuilder();
//			for (int x = absisMin; x <= abscisseMax; x++) {
//				boolean found = false;
//				for (SlotMap slotMap : getMaps()) {
//					if (slotMap.getCoordX() == x && slotMap.getCoordY() == y) {
//						line.append("[").append(slotMap.geteMapRoom().getSymbol()).append("]");
//						found = true;
//						break;
//					}
//				}
//				if (!found) {
//					line.append("[ ]");
//				}
//			}
//			System.out.println(line);
//		}
	}

}
