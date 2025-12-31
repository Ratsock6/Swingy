package fr.aallouv.manager.entity;

public abstract class Entity {

    protected String name;
    protected int level;
    protected int hitPoints;

    // Stats
    protected int attack;
    protected int defense;
    protected int psychicAttack;
    protected int psychicDefense;
    protected int speed;

    protected Entity(String name, int level, int hitPoints, int attack, int defense, int psychicAttack, int psychicDefense, int speed) {
        this.name = name;
        this.level = level;
        this.hitPoints = hitPoints;
        this.attack = attack;
        this.defense = defense;
        this.psychicAttack = psychicAttack;
        this.psychicDefense = psychicDefense;
        this.speed = speed;
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public int getAttack() {
        return attack;
    }

    public int getDefense() {
        return defense;
    }

    public int getPsychicAttack() {
        return psychicAttack;
    }

    public int getPsychicDefense() {
        return psychicDefense;
    }




    public boolean isAlive() {
        return hitPoints > 0;
    }

    public void takePhysicalDamage(int damage) {
        int actualDamage = Math.max(damage - defense, 0);
        hitPoints -= actualDamage;
        if (hitPoints < 0) {
            hitPoints = 0;
            onDeath();
        }
    }

    public void takePsychicDamage(int damage) {
        int actualDamage = Math.max(damage - psychicDefense, 0);
        hitPoints -= actualDamage;
        if (hitPoints < 0) {
            hitPoints = 0;
            onDeath();
        }
    }


    public abstract void onDeath();
}

