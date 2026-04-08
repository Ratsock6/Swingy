package fr.aallouv.swingy.model.game;

import fr.aallouv.swingy.model.artifact.Artifact;
import fr.aallouv.swingy.model.artifact.ArtifactType;
import fr.aallouv.swingy.model.entity.Hero;
import fr.aallouv.swingy.model.entity.Villain;

import java.util.Random;

public class GameEngine {

    private static final int TRAP_DAMAGE_PERCENT = 15;
    private static final int REST_HEAL_PERCENT = 30;
    private static final double RUN_SUCCESS_CHANCE = 0.5;

    private final Random random = new Random();

    // --- Combat ---

    public CombatResult simulateCombat(Hero hero, Villain villain) {
        int heroHp = hero.getHitPoints();
        int villainHp = villain.getHitPoints();

        while (heroHp > 0 && villainHp > 0) {
            int heroDamage = computeDamage(hero.getAttack(), villain.getDefense());
            villainHp -= heroDamage;
            if (villainHp <= 0) break;

            int villainDamage = computeDamage(villain.getAttack(), hero.getDefense());
            heroHp -= villainDamage;
        }

        boolean heroWon = heroHp > 0;

        if (heroWon) {
            hero.setHitPoints(heroHp);
            boolean leveledUp = hero.gainExperience(villain.getExperienceReward());
            Artifact drop = rollArtifactDrop(villain);
            return new CombatResult(true, villain.getExperienceReward(), drop);
        } else {
            hero.setHitPoints(0);
            return new CombatResult(false, 0, null);
        }
    }

    private int computeDamage(int attack, int defense) {
        int base = Math.max(1, attack - defense);
        int variance = (int) (base * 0.2);
        int roll = variance == 0 ? 0 : random.nextInt(variance * 2 + 1) - variance;
        return Math.max(1, base + roll);
    }

    private Artifact rollArtifactDrop(Villain villain) {
        int dropChance = 20 + villain.getLevel() * 5;
        if (random.nextInt(100) >= dropChance) return null;

        ArtifactType[] types = ArtifactType.values();
        ArtifactType type = types[random.nextInt(types.length)];
        int bonus = 2 + villain.getLevel() + random.nextInt(3);

        return switch (type) {
            case WEAPON -> new Artifact("Épée ++" + bonus, type, bonus);
            case ARMOR -> new Artifact("Armure ++" + bonus, type, bonus);
            case HELM -> new Artifact("Heaume ++" + bonus, type, bonus);
        };
    }

    // --- Fuite ---

    public boolean tryRun() {
        return random.nextDouble() < RUN_SUCCESS_CHANCE;
    }

    // --- Piège ---

    public int computeTrapDamage(Hero hero) {
        return Math.max(1, hero.getMaxHitPoints() * TRAP_DAMAGE_PERCENT / 100);
    }

    // --- Repos ---

    public int computeHeal(Hero hero) {
        int healed = Math.max(1, hero.getMaxHitPoints() * REST_HEAL_PERCENT / 100);
        int actual = Math.min(healed, hero.getMaxHitPoints() - hero.getHitPoints());
        hero.setHitPoints(hero.getHitPoints() + actual);
        return actual;
    }

    // --- Génération de villain ---

    public Villain generateVillain(int heroLevel) {
        int level = Math.max(1, heroLevel + random.nextInt(3) - 1);
        int hp = 30 + level * 10 + random.nextInt(20);
        int attack = 5 + level * 2 + random.nextInt(5);
        int defense = 2 + level + random.nextInt(3);
        int xp = level * 80 + random.nextInt(50);

        String[] names = {"Gobelin", "Squelette", "Ogre", "Démon", "Loup sombre", "Bandit"};
        String name = names[random.nextInt(names.length)] + " Niv." + level;

        return new Villain(name, level, hp, attack, defense, 0, 0, xp);
    }

    public Villain generateElite(int heroLevel) {
        int level = heroLevel + 1;
        int hp = 60 + level * 15 + random.nextInt(30);
        int attack = 8 + level * 3 + random.nextInt(6);
        int defense = 4 + level * 2 + random.nextInt(4);
        int xp = level * 150 + random.nextInt(80);

        return new Villain("Élite " + level, level, hp, attack, defense, 0, 0, xp);
    }

    public Villain generateBoss(int heroLevel) {
        int level = heroLevel + 2;
        int hp = 150 + level * 25;
        int attack = 12 + level * 4;
        int defense = 8 + level * 3;
        int xp = level * 300;

        return new Villain("Boss Niv." + level, level, hp, attack, defense, 0, 0, xp);
    }
}
