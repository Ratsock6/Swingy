package fr.aallouv.swingy.model.entity;

import fr.aallouv.swingy.model.artifact.Artifact;
import fr.aallouv.swingy.model.artifact.ArtifactType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.EnumMap;
import java.util.Map;

public class Hero extends Entity {

    @NotBlank(message = "Le nom du héros ne peut pas être vide.")
    @Size(min = 2, max = 20, message = "Le nom doit faire entre 2 et 20 caractères.")
    private String name;

    private final HeroClass heroClass;
    private int experience;
    private int gold;
    private final Map<ArtifactType, Artifact> artifacts = new EnumMap<>(ArtifactType.class);

    private Hero(Builder builder) {
        this.name = builder.name;
        this.heroClass = builder.heroClass;
        this.level = builder.level;
        this.experience = builder.experience;
        this.gold = builder.gold;
        this.hitPoints = builder.heroClass.baseHp  + (builder.level - 1) * 10;
        this.maxHitPoints = this.hitPoints;
        this.attack = builder.heroClass.baseAttack  + (builder.level - 1) * 2;
        this.defense = builder.heroClass.baseDefense + (builder.level - 1) * 2;
    }

    // --- Expérience et niveau ---

    public static int xpRequiredForLevel(int level) {
        return level * 1000 + (level - 1) * (level - 1) * 450;
    }

    public boolean gainExperience(int xp) {
        experience += xp;
        if (experience >= xpRequiredForLevel(level)) {
            levelUp();
            return true;
        }
        return false;
    }

    private void levelUp() {
        level++;
        maxHitPoints += 10;
        hitPoints     = maxHitPoints;
        attack        += 2;
        defense       += 2;
    }

    // --- Artefacts ---

    public void equipArtifact(Artifact artifact) {
        Artifact old = artifacts.get(artifact.getType());
        if (old != null) removeArtifactBonus(old);
        artifacts.put(artifact.getType(), artifact);
        applyArtifactBonus(artifact);
    }

    private void applyArtifactBonus(Artifact a) {
        switch (a.getType()) {
            case WEAPON -> attack += a.getBonus();
            case ARMOR -> defense += a.getBonus();
            case HELM -> { maxHitPoints += a.getBonus(); hitPoints += a.getBonus(); }
        }
    }

    private void removeArtifactBonus(Artifact a) {
        switch (a.getType()) {
            case WEAPON -> attack -= a.getBonus();
            case ARMOR -> defense -= a.getBonus();
            case HELM -> { maxHitPoints -= a.getBonus(); hitPoints = Math.min(hitPoints, maxHitPoints); }
        }
    }

    public void fullHeal() {
        hitPoints = maxHitPoints;
    }

    // --- Getters ---

    @Override
    public String getName() {
        return name;
    }

    public HeroClass getHeroClass() {
        return heroClass;
    }

    public int getExperience() {
        return experience;
    }

    public int getGold() {
        return gold;
    }

    public Map<ArtifactType, Artifact> getArtifacts() {
        return artifacts;
    }

    public int getXpToNextLevel() {
        return xpRequiredForLevel(level) - experience;
    }

    // --- Builder ---

    public static class Builder {
        private final String    name;
        private final HeroClass heroClass;
        private int level      = 1;
        private int experience = 0;
        private int gold       = 0;

        public Builder(String name, HeroClass heroClass) {
            this.name      = name;
            this.heroClass = heroClass;
        }

        public Builder level(int level) {
            this.level = level;
            return this;
        }

        public Builder experience(int experience) {
            this.experience = experience;
            return this;
        }

        public Builder gold(int gold) {
            this.gold = gold;
            return this;
        }

        public Hero build() {
            return new Hero(this);
        }
    }
}
