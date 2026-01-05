package fr.aallouv.manager.map.room;

import fr.aallouv.App;
import fr.aallouv.manager.map.EMapRoom;

import java.util.Random;

public class TreasureRoom extends Room {

    public TreasureRoom(int x, int y) {
        super(x, y, EMapRoom.TREASURE.getSymbol(), EMapRoom.TREASURE.getName(), EMapRoom.TREASURE.getDescription());
    }

    @Override
    public void onEnter() {
        if (!visited) {
            App.getApp().getGameManager().getHero().setGold(App.getApp().getGameManager().getHero().getGold() + new Random().nextInt(100) + 50);
            App.getApp().getGameManager().printMessage("You found a treasure room! You gained some gold. Current gold: " + App.getApp().getGameManager().getHero().getGold());
        } else {
            App.getApp().getGameManager().printMessage("You have already searched the chest in this room.");
        }
    }

}
