package fr.aallouv.manager.map.room;

import fr.aallouv.App;
import fr.aallouv.manager.map.EMapRoom;

import java.util.Random;

public class TrapRoom extends Room {

    public TrapRoom(int x, int y) {
        super(x, y, EMapRoom.TRAP.getSymbol(), EMapRoom.TRAP.getName(), EMapRoom.TRAP.getDescription());
    }

    @Override
    public void onEnter() {
        if (!visited) {
            int esquiveChance = new Random().nextInt(100);
            App.getApp().getLogger().log("Trap triggered! Esquive chance: " + esquiveChance + ", Hero speed: " + App.getApp().getGameManager().getHero().getSpeed() + ", Active: " + (esquiveChance < App.getApp().getGameManager().getHero().getSpeed()));
            if (esquiveChance < App.getApp().getGameManager().getHero().getSpeed()) {
                App.getApp().getGameManager().printMessage("You triggered a trap but managed to evade it thanks to your speed! No damage taken.");
            } else {
                int damage = new Random().nextInt(20) + 10;
                App.getApp().getGameManager().getHero().takePhysicalDamage(damage);
                App.getApp().getGameManager().printMessage("You triggered a trap! You took " + damage + " damage. Current health: " + App.getApp().getGameManager().getHero().getHealth());
            }
        }
    }

}
