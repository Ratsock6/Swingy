package fr.aallouv.manager.entity;

public enum EClass {

    WARRIOR("Warrior", 150,15, 10, 0, 0, 5),
    MAGE("Mage", 90 , 5,5, 15, 10, 5),
    ROGUE("Rogue", 125,10,5, 5, 5, 15);

    private final String className;
    private final int hitPoint, physicalAttack, physicalDefense, psychicAttack, psychicDefense, speed;

    EClass(String className, int hitPoint, int physicalAttack, int physicalDefense, int psychicAttack, int psychicDefense, int speed) {
        this.className = className;
        this.physicalAttack = physicalAttack;
        this.physicalDefense = physicalDefense;
        this.psychicAttack = psychicAttack;
        this.psychicDefense = psychicDefense;
        this.speed = speed;
        this.hitPoint = hitPoint;
    }

    public String getClassName() {
        return className;
    }

    public int getPhysicalAttack() {
        return physicalAttack;
    }

    public int getPhysicalDefense() {
        return physicalDefense;
    }

    public int getPsychicAttack() {
        return psychicAttack;
    }

    public int getPsychicDefense() {
        return psychicDefense;
    }

    public int getSpeed() {
        return speed;
    }

    public int getHitPoint() {
        return hitPoint;
    }
}
