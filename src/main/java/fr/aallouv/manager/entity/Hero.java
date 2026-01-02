package fr.aallouv.manager.entity;

import fr.aallouv.App;
import fr.aallouv.manager.map.CardinalPoint;

public class Hero extends Entity {

    private EClass eClass;
    private int x, y;

    public Hero(String name, EClass eClass) {
        super(name, 1, 0, eClass.getHitPoint(), eClass.getPhysicalAttack(), eClass.getPhysicalDefense(), eClass.getPsychicAttack(), eClass.getPsychicDefense(), eClass.getSpeed());
        this.eClass = eClass;
        App.getApp().getLogger().log("Created Hero: " + name + " of class " + eClass.getClassName());
    }

    public Hero(String name, EClass eClass, int level, int xp, int hitPoints, int attack, int defense, int psychicAttack, int psychicDefense, int speed) {
        super(name ,level, xp, hitPoints, attack, defense, psychicAttack, psychicDefense, speed);
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

    public int xpForNextLevel() {
        int base = level * 1000;
        int quadratic = (level - 1) * (level - 1) * 450;
        return base + quadratic;
    }

    public String toStringStats() {
        return "\nHero Stats:" +
                "\nName: " + name +
                "\nClass: " + eClass.getClassName() +
                "\nLevel: " + level +
                "\nXP: " + xp + "/" + xpForNextLevel() +
                "\nHP: " + health + "/" + maxHealth +
                "\nAttack: " + attack +
                "\nDefense: " + defense +
                "\nPsychic Attack: " + psychicAttack +
                "\nPsychic Defense: " + psychicDefense +
                "\nSpeed: " + speed +
                "\nPosition: (" + x + ", " + y + ")\n";
    }

    public void move(CardinalPoint direction) {
        switch (direction) {
            case NORTH:
                setY(getY() + 1);
                break;
            case SOUTH:
                setY(getY() - 1);
                break;
            case EAST:
                setX(getX() + 1);
                break;
            case WEST:
                setX(getX() - 1);
                break;
        }
        App.getApp().getLogger().log("Hero " + this.name + " moved " + direction.name() + " to position (" + this.x + ", " + this.y + ")");
        App.getApp().getGameManager().enterRoom(App.getApp().getGameManager().getMapManager().getSlotMapByCoordinates(x, y));
    }


    @Override
    public void addXp(int xp) {
        this.xp += xp;
        App.getApp().getLogger().log("Hero " + this.name + " gained " + xp + " XP. Total XP: " + this.xp);
        int xpForNextLevel = xpForNextLevel();
        while (this.xp >= xpForNextLevel) {
            this.xp -= xpForNextLevel;
            this.level++;
            this.maxHealth += 10;
            this.health = this.maxHealth;
            this.attack += 2;
            this.defense += 2;
            this.psychicAttack += 2;
            this.psychicDefense += 2;
            this.speed += 1;
            App.getApp().getLogger().log("Hero " + this.name + " leveled up to level " + this.level + "!");
            App.getApp().getGameManager().printMessage("Congratulations! " + this.name + " has reached level " + this.level + "! New stats - HP: " + this.maxHealth + ", Attack: " + this.attack + ", Defense: " + this.defense + ", Psychic Attack: " + this.psychicAttack + ", Psychic Defense: " + this.psychicDefense + ", Speed: " + this.speed);
            App.getApp().getGameManager().printMessage("XP needed for next level: " + xpForNextLevel());
            xpForNextLevel = xpForNextLevel();
        }
    }

    @Override
    public void onDeath() {
        App.getApp().getLogger().log("Hero " + this.name + " has died.");
        setX(0);
        setY(0);
        this.health = eClass.getHitPoint();
        App.getApp().getLogger().log("Hero " + this.name + " has been respawned at starting position with full health.");
        App.getApp().getGameManager().printMessage("▄▄▄▄▄▄   ▄▄▄▄▄  ▄▄▄▄▄▄▄ ▄▄▄▄▄▄   ");
        App.getApp().getGameManager().printMessage("███▀▀██▄  ███  ███▀▀▀▀▀ ███▀▀██▄ ");
        App.getApp().getGameManager().printMessage("███  ███  ███  ███▄▄    ███  ███ ");
        App.getApp().getGameManager().printMessage("███  ███  ███  ███      ███  ███ ");
        App.getApp().getGameManager().printMessage("██████▀  ▄███▄ ▀███████ ██████▀");
        App.getApp().getGameManager().printMessage("##############################################################################");
        App.getApp().getGameManager().printMessage("#                                                                            #");
        App.getApp().getGameManager().printMessage("#                          You have been defeated!                           #");
        App.getApp().getGameManager().printMessage("#               You are being respawned at the starting position.            #");
        App.getApp().getGameManager().printMessage("#                     Your health has been restored to full.                 #");
        App.getApp().getGameManager().printMessage("#                                                                            #");
        App.getApp().getGameManager().printMessage("##############################################################################");
        App.getApp().getGameManager().enterRoom(App.getApp().getGameManager().getMapManager().getSlotMapByCoordinates(x, y));
    }
}
