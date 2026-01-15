package fr.aallouv.manager.combat;

import fr.aallouv.App;
import fr.aallouv.manager.ECombatStatus;
import fr.aallouv.manager.entity.Entity;
import fr.aallouv.manager.entity.Hero;
import fr.aallouv.manager.entity.Monster;
import fr.aallouv.utils.GenerateRandom;

import java.util.Scanner;

public class CombatManager {

    private Hero hero;
    private Monster monster;


    public CombatManager(Monster monster) {
        App.getApp().getLogger().log("CombatManager initialized");
        this.hero = App.getApp().getGameManager().getHero();
        this.monster = monster;
    }

    private boolean fled() {
        return GenerateRandom.random(App.getApp().getGameManager().getHero().getSpeed());
    }

    public ECombatStatus startCombat() {
        boolean heroStarts = hero.getSpeed() >= monster.getSpeed();
        boolean alreadyFled = false;
        App.getApp().getLogger().log("Combat started between Hero and Monster ID[" + monster.getId() + "], Hero starts: " + heroStarts);
        App.getApp().getGameManager().printMessage(monster.getStats());
        App.getApp().getGameManager().printMessage("------");
        App.getApp().getGameManager().printMessage(hero.toStringStats());
        App.getApp().getGameManager().printMessage("A wild " + monster.getName() + " appears!");

        while (true) {
            if (!hero.isAlive())
                return ECombatStatus.DEFEAT;
            Entity starter = heroStarts ? hero : monster;
            Entity receiver = heroStarts ? monster : hero;
            App.getApp().getLogger().log(starter.getName() + " starts the turn against " + receiver.getName());
            if (starter == hero) {
                App.getApp().getGameManager().printMessage("Choose your action:");
                App.getApp().getGameManager().printMessage("1) Attack");
                App.getApp().getGameManager().printMessage("2) Psychic Attack");
                if (!alreadyFled)
                    App.getApp().getGameManager().printMessage("3) Flee");
                Scanner scanner = new Scanner(System.in);
                String input = scanner.nextLine();


                if (input.equals("1")) {
                    monster.takePhysicalDamage(hero.getAttack());
                    App.getApp().getGameManager().printMessage("You dealt " + Math.max(hero.getAttack() - monster.getDefense(), 1) + " physical damage to the " + monster.getName() + "." + " Monster HP: " + monster.getHealth() + "/" + monster.getMaxHealth());
                    if (!monster.isAlive()) {
                        monster.onDeath();
                        App.getApp().getLogger().log("Monster ID[" + monster.getId() + "] defeated by Hero.");
                        return ECombatStatus.VICTORY;
                    }
                } else if (input.equals("2")) {
                    monster.takePsychicDamage(hero.getPsychicAttack());
                    App.getApp().getGameManager().printMessage("You dealt " + Math.max(hero.getPsychicAttack() - monster.getPsychicDefense(), 1) + " psychic damage to the " + monster.getName() + "." + " Monster HP: " + monster.getHealth() + "/" + monster.getMaxHealth());
                    if (!monster.isAlive()) {
                        monster.onDeath();
                        App.getApp().getLogger().log("Monster ID[" + monster.getId() + "] defeated by Hero.");
                        return ECombatStatus.VICTORY;
                    }
                }
                if (input.equals("3")) {
                   if (alreadyFled) {
                       App.getApp().getGameManager().printMessage("You have already tried to flee once. You cannot try again this combat.");
                   } else {
                       alreadyFled = true;
                       if (fled()) {
                           App.getApp().getGameManager().printMessage("You successfully fled the combat!");
                           App.getApp().getLogger().log("Hero fled successfully from combat.");
                           return ECombatStatus.FLED;
                       } else {
                           App.getApp().getGameManager().printMessage("Flee attempt failed!");
                           App.getApp().getLogger().log("Hero failed to flee from combat.");
                       }
                   }
                }
            } else {
                if (GenerateRandom.random(50)) {
                    hero.takePhysicalDamage(monster.getAttack());
                    App.getApp().getGameManager().printMessage("The " + monster.getName() + " dealt " + monster.getAttack() + " physical damage to you." + " Your HP: " + hero.getHealth() + "/" + hero.getMaxHealth());
                } else {
                    hero.takePsychicDamage(monster.getPsychicAttack());
                    App.getApp().getGameManager().printMessage("The " + monster.getName() + " dealt " + monster.getPsychicAttack() + " psychic damage to you." + " Your HP: " + hero.getHealth() + "/" + hero.getMaxHealth());
                }
                if (!hero.isAlive()) {
                    hero.onDeath();
                    App.getApp().getLogger().log("Hero has been defeated by Monster ID[" + monster.getId() + "].");
                    return ECombatStatus.DEFEAT;
                }
            }
            // Swap turns
            heroStarts = !heroStarts;
        }
    }

}
