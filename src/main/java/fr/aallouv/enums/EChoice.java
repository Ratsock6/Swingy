package fr.aallouv.enums;

import fr.aallouv.manager.entity.Hero;

import java.util.ArrayList;

public enum EChoice {

    ATTACK_UP(10, 0, 0, 0, 0, 0, 0, 50, "Increase your Attack by 10 points."),
    PSYCHIC_ATTACK_UP(0, 10, 0, 0, 0, 0, 0, 50, "Increase your Psychic Attack by 10 points."),
    DEFENSE_UP(0, 0, 10, 0, 0, 0, 0, 50, "Increase your Defense by 10 points."),
    PSYCHIC_DEFENSE_UP(0, 0, 0, 10, 0, 0, 0, 50, "Increase your Psychic Defense by 10 points."),
    SPEED_UP(0, 0, 0, 0, 10, 0, 0, 50, "Increase your Speed by 10 points."),
    HEALTH_UP(0, 0, 0, 0, 0, 20, 0, 50, "Increase your Health by 20 points."),
    XP_UP(0, 0, 0, 0, 0, 0, 100, 100, "Gain 100 XP.");



    private final int attackValue, psychicAttackValue, defenseValue, psychicDefenseValue, speedValue, healthValue, xpValue, cost;
    private final String description;

    EChoice(int attackValue, int psychicAttackValue, int defenseValue, int psychicDefenseValue, int speedValue, int healthValue, int xpValue, int cost, String description) {
        this.attackValue = attackValue;
        this.psychicAttackValue = psychicAttackValue;
        this.defenseValue = defenseValue;
        this.psychicDefenseValue = psychicDefenseValue;
        this.speedValue = speedValue;
        this.healthValue = healthValue;
        this.xpValue = xpValue;
        this.cost = cost;
        this.description = description;
    }

    public int getAttackValue() {
        return attackValue;
    }

    public int getCost() {
        return cost;
    }

    public int getDefenseValue() {
        return defenseValue;
    }

    public int getHealthValue() {
        return healthValue;
    }

    public int getPsychicAttackValue() {
        return psychicAttackValue;
    }

    public int getPsychicDefenseValue() {
        return psychicDefenseValue;
    }

    public int getSpeedValue() {
        return speedValue;
    }

    public int getXpValue() {
        return xpValue;
    }

    public String getDescription() {
        return description;
    }


    public static EChoice getRandomChoice() {
        EChoice[] choices = EChoice.values();
        int randomIndex = (int) (Math.random() * choices.length);
        return choices[randomIndex];
    }

    public static EChoice getRandomChoiceExclude(EChoice exclude) {
        ArrayList<EChoice> choices = new ArrayList<>();
        for (EChoice choice : EChoice.values()) {
            if (choice != exclude) {
                choices.add(choice);
            }
        }
        int randomIndex = (int) (Math.random() * choices.size());
        return choices.get(randomIndex);
    }

}
