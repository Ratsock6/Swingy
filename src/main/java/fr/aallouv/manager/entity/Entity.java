package fr.aallouv.manager.entity;

public abstract class Entity {

    protected String name;
    protected int level;
    protected int xp;
    protected int health;
    protected int maxHealth;

    // Stats
    protected int attack;
    protected int defense;
    protected int psychicAttack;
    protected int psychicDefense;
    protected int speed;

    protected Entity(String name, int level, int xp, int health, int attack, int defense, int psychicAttack, int psychicDefense, int speed) {
        this.name = name;
        this.level = level;
        this.xp = xp;
        this.health = health;
        this.attack = attack;
        this.defense = defense;
        this.psychicAttack = psychicAttack;
        this.psychicDefense = psychicDefense;
        this.speed = speed;
        this.maxHealth = health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public int getHealth() {
        return health;
    }

    public int getSpeed() {
        return speed;
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

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPsychicAttack(int psychicAttack) {
        this.psychicAttack = psychicAttack;
    }

    public void setPsychicDefense(int psychicDefense) {
        this.psychicDefense = psychicDefense;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public int getXp() {
        return xp;
    }

    public boolean isAlive() {
        return health > 0;
    }

    public void takePhysicalDamage(int damage) {
        int actualDamage = Math.max(damage - defense, 1);
        health -= actualDamage;
        if (health < 0) {
            health = 0;
            onDeath();
        }
    }

    public void takePsychicDamage(int damage) {
        int actualDamage = Math.max(damage - psychicDefense, 1);
        health -= actualDamage;
        if (health < 0) {
            health = 0;
            onDeath();
        }
    }

    public String getStats() {
        return
                "\nName: " + name +
                "\nLevel: " + level +
                "\nHP: " + health + "/" + maxHealth +
                "\nAttack: " + attack +
                "\nDefense: " + defense +
                "\nPsychic Attack: " + psychicAttack +
                "\nPsychic Defense: " + psychicDefense +
                "\nSpeed: " + speed;
    }

    public abstract void addXp(int xp);
    public abstract void onDeath();
}

