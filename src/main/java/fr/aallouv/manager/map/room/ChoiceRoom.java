package fr.aallouv.manager.map.room;

import fr.aallouv.App;
import fr.aallouv.manager.map.EMapRoom;

import java.util.ArrayList;
import java.util.Scanner;

import static fr.aallouv.manager.map.room.ChoiceRoom.EChoice.getRandomChoice;

public class ChoiceRoom extends Room {

    private final EChoice firstChoice;
    private final EChoice secondChoice;
    private boolean trigger;


    public ChoiceRoom(int x, int y) {
        super(x, y, EMapRoom.CHOICE.getSymbol(), EMapRoom.CHOICE.getName(), EMapRoom.CHOICE.getDescription());
        firstChoice = getRandomChoice();
        secondChoice = EChoice.getRandomChoiceExclude(firstChoice);
        trigger = false;
    }

    @Override
    public void onEnter() {
        if (!trigger) {
            App.getApp().getGameManager().printMessage("You have two choices:\n1) " + firstChoice.getDescription() + " (Cost: " + firstChoice.getCost() + ")" + "\n2) " + secondChoice.getDescription() + " (Cost: " + secondChoice.getCost() + ")");
            App.getApp().getGameManager().printMessage("Type 1 or to make your choice:");
            App.getApp().getGameManager().printMessage("If you want to skip the room and come back later, type anything.");
            Scanner scanner = new Scanner(System.in);
            String choiceInput = scanner.nextLine();
            EChoice chosen;
            if (choiceInput.equals("1")) {
                chosen = firstChoice;
                trigger = true;
            } else if (choiceInput.equals("2")) {
                chosen = secondChoice;
                trigger = true;
            } else {
                App.getApp().getGameManager().printMessage("Skip the room");
                return;
            }
            if (chosen.getCost() > App.getApp().getGameManager().getHero().getGold()) {
                trigger = false;
                App.getApp().getGameManager().printMessage("You do not have enough gold for this choice. No effects applied.");
                return;
            } else {
                App.getApp().getGameManager().getHero().setGold(App.getApp().getGameManager().getHero().getGold() - chosen.getCost());
                App.getApp().getGameManager().printMessage("You chose: " + chosen.getDescription() + ". Gold remaining: " + App.getApp().getGameManager().getHero().getGold());
            }
            App.getApp().getGameManager().getHero().setMaxHealth(App.getApp().getGameManager().getHero().getMaxHealth() + chosen.getHealthValue());
            App.getApp().getGameManager().getHero().setHealth(App.getApp().getGameManager().getHero().getHealth() + chosen.getHealthValue());
            App.getApp().getGameManager().getHero().setAttack(App.getApp().getGameManager().getHero().getAttack() + chosen.getAttackValue());
            App.getApp().getGameManager().getHero().setDefense(App.getApp().getGameManager().getHero().getDefense() + chosen.getDefenseValue());
            App.getApp().getGameManager().getHero().setPsychicAttack(App.getApp().getGameManager().getHero().getPsychicAttack() + chosen.getPsychicAttackValue());
            App.getApp().getGameManager().getHero().setPsychicDefense(App.getApp().getGameManager().getHero().getPsychicDefense() + chosen.getPsychicDefenseValue());
            App.getApp().getGameManager().getHero().setSpeed(App.getApp().getGameManager().getHero().getSpeed() + chosen.getSpeedValue());
            App.getApp().getGameManager().getHero().addXp(chosen.getXpValue());
        } else {
            App.getApp().getGameManager().printMessage("You have already made a choice in this room.");
        }
    }



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

}
