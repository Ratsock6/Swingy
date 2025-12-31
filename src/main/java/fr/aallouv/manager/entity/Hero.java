package fr.aallouv.manager.entity;

import fr.aallouv.App;

public class Hero extends Entity {

    private EClass eClass;
    private int x, y;

    public Hero(String name, EClass eClass) {
        super(name, 1, eClass.getHitPoint(), eClass.getPhysicalAttack(), eClass.getPhysicalDefense(), eClass.getPsychicAttack(), eClass.getPsychicDefense(), eClass.getSpeed());
        this.eClass = eClass;
        App.getApp().getLogger().log("Created Hero: " + name + " of class " + eClass.getClassName());
    }

    public Hero(String name, EClass eClass, int level, int hitPoints, int attack, int defense, int psychicAttack, int psychicDefense, int speed) {
        super(name ,level, hitPoints, attack, defense, psychicAttack, psychicDefense, speed);
        this.eClass = eClass;
        App.getApp().getLogger().log("Created Hero: " + name + " of class " + eClass.getClassName() + " at level " + level);
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

    public EClass geteClass() {
        return eClass;
    }

    public void seteClass(EClass eClass) {
        this.eClass = eClass;
    }

    @Override
    public void onDeath() {
        App.getApp().getLogger().log("Hero " + this.name + " has died.");
        setX(0);
        setY(0);
        this.hitPoints = eClass.getHitPoint();
        App.getApp().getLogger().log("Hero " + this.name + " has been respawned at starting position with full health.");
        App.getApp().getGameManager().enterRoom(App.getApp().getGameManager().getMapManager().getSlotMapByCoordinates(x, y));
    }
}
