package fr.aallouv.manager.entity;

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



}
