package fr.aallouv.swingy.model.entity;

public class Villain extends Entity {

    private final int experienceReward;

    public Villain(String name, int level, int hp, int attack, int defense, int psychicAttack, int psychicDefense, int experienceReward) {
        this.name = name;
        this.level = level;
        this.hitPoints = hp;
        this.maxHitPoints = hp;
        this.attack = attack;
        this.defense = defense;
        this.experienceReward = experienceReward;
    }

    public int getExperienceReward() { return experienceReward; }
}
