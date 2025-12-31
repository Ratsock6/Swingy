package fr.aallouv.manager.entity;

import fr.aallouv.manager.map.EMapRoom;
import fr.aallouv.manager.map.SlotMap;

import java.util.Random;

public class Monster extends Entity {

    private static int monsterId = 0;
    private final int id;

    public Monster(String name, int level, int hitPoints, int attack, int defense, int psychicAttack, int psychicDefense, int speed) {
        super(name, level, hitPoints, attack, defense, psychicAttack, psychicDefense, speed);
        this.id = monsterId;
        monsterId++;
    }

    public int getId() {
        return id;
    }

    @Override
    public void onDeath() {
        // Monster-specific death behavior can be implemented here
    }


    public static Monster createMonster(EMapRoom eMapRoom) {
        Random rand = new Random();
        switch (eMapRoom) {
            case COMBAT:
                return new Monster("Goblin", rand.nextInt(5), rand.nextInt(50) + 1, rand.nextInt(10) + 1, rand.nextInt(5) + 1, rand.nextInt(5) + 1, rand.nextInt(5) + 1, rand.nextInt(10) + 1);
            case ELITE:
                return new Monster("Orc", rand.nextInt(3) + 3, rand.nextInt(100) + 50, rand.nextInt(20) + 10, rand.nextInt(10) + 5, rand.nextInt(10) + 5, rand.nextInt(10) + 5, rand.nextInt(15) + 5);
            case BOSS:
                return new Monster("Boss", 10, rand.nextInt(200) + 100, rand.nextInt(30) + 20, rand.nextInt(15) + 10, rand.nextInt(15) + 10, rand.nextInt(15) + 10, rand.nextInt(20) + 10);
            default:
                return new Monster("Dummy", 1, 0, 0, 0, 0, 0, 0);
        }

    }



}
