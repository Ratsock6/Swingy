package fr.aallouv.manager.player;

import fr.aallouv.App;

public class Hero {


    private int health, physiqueAttack, physiqueDefense, psychicAttack, psychicDefense, speed;
    private int x, y;
    private EClass eClass;
    private final String name;

    public Hero(String name, EClass eClass) {
        this.eClass = eClass;
        this.name = name;
        this.health = 20;
        this.physiqueAttack = 1;
        this.physiqueDefense = 1;
        this.psychicAttack = 1;
        this.psychicDefense = 1;
        this.speed = 1;
        this.x = 0;
        this.y = 0;
        App.getApp().getLogger().log("Created Hero " + this.name + " of class " + this.eClass.name() + " with default stats.");
    }

    public Hero(String name, EClass eClass, int health, int physiqueAttack, int physiqueDefense, int psychicAttack, int psychicDefense, int speed, int x, int y) {
        this.eClass = eClass;
        this.name = name;
        this.health = health;
        this.physiqueAttack = physiqueAttack;
        this.physiqueDefense = physiqueDefense;
        this.psychicAttack = psychicAttack;
        this.psychicDefense = psychicDefense;
        this.x = x;
        this.y = y;
        this.speed = speed;
        App.getApp().getLogger().log("Created Hero " + this.name + " of class " + this.eClass.name() + " with stats: Health=" + this.health + ", PhysiqueAttack=" + this.physiqueAttack + ", PhysiqueDefense=" + this.physiqueDefense + ", PsychicAttack=" + this.psychicAttack + ", PsychicDefense=" + this.psychicDefense + ", Speed=" + this.speed + ", Position=(" + this.x + "," + this.y + ")");
    }

    public String getName() {
        return name;
    }

    public EClass geteClass() {
        return eClass;
    }

    public void seteClass(EClass eClass) {
        this.eClass = eClass;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getPhysiqueAttack() {
        return physiqueAttack;
    }

    public void setPhysiqueAttack(int physiqueAttack) {
        this.physiqueAttack = physiqueAttack;
    }

    public int getPhysiqueDefense() {
        return physiqueDefense;
    }

    public void setPhysiqueDefense(int physiqueDefense) {
        this.physiqueDefense = physiqueDefense;
    }

    public int getPsychicAttack() {
        return psychicAttack;
    }

    public void setPsychicAttack(int psychicAttack) {
        this.psychicAttack = psychicAttack;
    }

    public int getPsychicDefense() {
        return psychicDefense;
    }

    public void setPsychicDefense(int psychicDefense) {
        this.psychicDefense = psychicDefense;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

}
