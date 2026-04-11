package fr.aallouv.swingy.model.entity;

public enum HeroClass {

    WARRIOR("Warrior",120,15,8,5,3),
    MAGE("Mage",70,8,4,14,10),
    ROGUE("Rogue",90,12,5,8,6);

    public final String displayName;
    public final int baseHp;
    public final int baseAttack;
    public final int baseDefense;
    public final int basePsychicAttack;
    public final int basePsychicDefense;

    HeroClass(String displayName, int baseHp, int baseAttack, int baseDefense, int basePsychicAttack, int basePsychicDefense) {
        this.displayName = displayName;
        this.baseHp = baseHp;
        this.baseAttack = baseAttack;
        this.baseDefense = baseDefense;
        this.basePsychicAttack = basePsychicAttack;
        this.basePsychicDefense = basePsychicDefense;
    }
}