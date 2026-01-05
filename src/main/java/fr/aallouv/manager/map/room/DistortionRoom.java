package fr.aallouv.manager.map.room;

import fr.aallouv.App;
import fr.aallouv.manager.map.EMapRoom;

import java.util.ArrayList;
import java.util.Random;

public class DistortionRoom extends Room {

    public DistortionRoom(int x, int y) {
        super(x, y, EMapRoom.DISTORTION.getSymbol(), EMapRoom.DISTORTION.getName(), EMapRoom.DISTORTION.getDescription());
    }

    @Override
    public void onEnter() {
        if (!visited) {
            App.getApp().getGameManager().printMessage("You have entered a distortion room. Your position will be randomized!");
            ArrayList<Room> slotVisited = App.getApp().getGameManager().getMapManager().getVisitedRooms();
            if (slotVisited.size() > 1) {
                Room randomRoom;
                do {
                    randomRoom = slotVisited.get(new Random().nextInt(slotVisited.size()));
                } while (randomRoom == this);
                App.getApp().getGameManager().getHero().setX(randomRoom.getX());
                App.getApp().getGameManager().getHero().setY(randomRoom.getY());
                App.getApp().getGameManager().printMessage("You have been teleported to a new location: (" + App.getApp().getGameManager().getHero().getX() + ", " + App.getApp().getGameManager().getHero().getY() + ")");
                randomRoom.onEnter();
            } else {
                App.getApp().getGameManager().printMessage("No other visited rooms to teleport to. Staying in the current room.");
            }
        }
    }

}
