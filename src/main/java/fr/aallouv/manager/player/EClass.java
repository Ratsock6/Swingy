package fr.aallouv.manager.player;

public enum EClass {

    WARRIOR("Warrior"),
    MAGE("Mage"),
    ROGUE("Rogue");

    private final String className;

    EClass(String className) {
        this.className = className;
    }

    public String getClassName() {
        return className;
    }

}
