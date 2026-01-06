package fr.aallouv.manager.entity;

import fr.aallouv.App;
import fr.aallouv.manager.map.EMapRoom;

import java.util.Random;

public class Monster extends Entity {

    private static int monsterId = 0;
    private final int id;

    public Monster(String name, int level, int hitPoints, int attack, int defense, int psychicAttack, int psychicDefense, int speed) {
        super(name, level, 0, hitPoints, attack, defense, psychicAttack, psychicDefense, speed);
        this.id = monsterId;
        monsterId++;
        App.getApp().getLogger().log("Created Monster ID[" + this.id + "]: " + name + " at level " + level);
    }

    public int getId() {
        return id;
    }

    @Override
    public void addXp(int xp) {
        // Nothing to do for Monster
    }

    @Override
    public void onDeath() {
        App.getApp().getLogger().log("Monster ID[" + this.id + "] " + this.name + " has been defeated.");
        App.getApp().getGameManager().printMessage("You have defeated the " + this.name + "!");

        Random random = new Random();
        int xpGained = (level + 3) * 1000 + random.nextInt(501); // Random bonus between 0 and 500
        App.getApp().getGameManager().getHero().addXp(xpGained);
    }


    public static Monster createMonster(EMapRoom eMapRoom) {
        Random rand = new Random();
        switch (eMapRoom) {
            case COMBAT:
                return new Monster("Goblin", rand.nextInt(5), rand.nextInt(50) + 1, rand.nextInt(10) + 1, rand.nextInt(5) + 1, rand.nextInt(5) + 1, rand.nextInt(5) + 1, rand.nextInt(10) + 1);
            case ELITE:
                return new Monster("Orc", rand.nextInt(3) + 3, rand.nextInt(100) + 50, rand.nextInt(20) + 10, rand.nextInt(10) + 5, rand.nextInt(10) + 5, rand.nextInt(10) + 5, rand.nextInt(15) + 5);
            case BOSS:
                return new Monster("Dragon", 10, rand.nextInt(200) + 100, rand.nextInt(30) + 20, rand.nextInt(15) + 10, rand.nextInt(15) + 10, rand.nextInt(15) + 10, rand.nextInt(20) + 10);
            default:
                return new Monster("Dummy", 1, 0, 0, 0, 0, 0, 0);
        }
    }



}
