package fr.aallouv.swingy.repository;

import fr.aallouv.swingy.model.entity.Hero;
import fr.aallouv.swingy.model.entity.HeroClass;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileHeroRepository implements HeroRepository {

    private static final String FILE_PATH = "heroes.txt";
    private static final String SEPARATOR = ";";

    @Override
    public void save(Hero hero) {
        List<Hero> heroes = findAll();

        boolean found = false;
        for (int i = 0; i < heroes.size(); i++) {
            if (heroes.get(i).getName().equals(hero.getName())) {
                heroes.set(i, hero);
                found = true;
                break;
            }
        }
        if (!found) heroes.add(hero);

        writeAll(heroes);
    }

    @Override
    public void delete(String heroName) {
        List<Hero> heroes = findAll();
        heroes.removeIf(h -> h.getName().equals(heroName));
        writeAll(heroes);
    }

    @Override
    public List<Hero> findAll() {
        List<Hero> heroes = new ArrayList<>();
        File file = new File(FILE_PATH);
        if (!file.exists()) return heroes;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) continue;
                Hero hero = deserialize(line);
                if (hero != null) heroes.add(hero);
            }
        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture du fichier heroes.txt : " + e.getMessage());
        }
        return heroes;
    }

    private void writeAll(List<Hero> heroes) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, false))) {
            for (Hero hero : heroes) {
                writer.write(serialize(hero));
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Erreur lors de l'écriture du fichier heroes.txt : " + e.getMessage());
        }
    }

    private String serialize(Hero hero) {
        return String.join(SEPARATOR,
                hero.getName(),
                hero.getHeroClass().name(),
                String.valueOf(hero.getLevel()),
                String.valueOf(hero.getExperience()),
                String.valueOf(hero.getGold()),
                String.valueOf(hero.getHitPoints()),
                String.valueOf(hero.getMaxHitPoints()),
                String.valueOf(hero.getAttack()),
                String.valueOf(hero.getDefense())
        );
    }

    private Hero deserialize(String line) {
        try {
            String[] parts = line.split(SEPARATOR);
            String name = parts[0];
            HeroClass heroClass = HeroClass.valueOf(parts[1]);
            int level = Integer.parseInt(parts[2]);
            int experience = Integer.parseInt(parts[3]);
            int gold = Integer.parseInt(parts[4]);
            int hp = Integer.parseInt(parts[5]);
            int maxHp = Integer.parseInt(parts[6]);
            int attack = Integer.parseInt(parts[7]);
            int defense = Integer.parseInt(parts[8]);

            Hero hero = new Hero.Builder(name, heroClass)
                    .level(level)
                    .experience(experience)
                    .gold(gold)
                    .build();

            hero.setMaxHitPoints(maxHp);
            hero.setHitPoints(hp);
            hero.setAttack(attack);
            hero.setDefense(defense);

            return hero;
        } catch (Exception e) {
            System.err.println("Ligne corrompue ignorée : " + line);
            return null;
        }
    }
}
