package fr.aallouv.swingy.model.entity;

public abstract class Entity {

    protected String name;
    protected int    level;
    protected int    hitPoints;
    protected int    maxHitPoints;
    protected int    attack;
    protected int    defense;
    protected int    psychicAttack;
    protected int    psychicDefense;

    public boolean isAlive() {
        return hitPoints > 0;
    }

    public void takeDamage(int damage) {
        hitPoints = Math.max(0, hitPoints - damage);
    }

    // --- Getters ---

    public String getName()           { return name; }
    public int    getLevel()          { return level; }
    public int    getHitPoints()      { return hitPoints; }
    public int    getMaxHitPoints()   { return maxHitPoints; }
    public int    getAttack()         { return attack; }
    public int    getDefense()        { return defense; }
    public int    getPsychicAttack()  { return psychicAttack; }
    public int    getPsychicDefense() { return psychicDefense; }

    // --- Setters ---

    public void setHitPoints(int hp)  { this.hitPoints = Math.max(0, hp); }
    public void setAttack(int v)      { this.attack = v; }
    public void setDefense(int v)     { this.defense = v; }
}
