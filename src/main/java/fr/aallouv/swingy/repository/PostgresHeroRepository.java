package fr.aallouv.swingy.repository;

import fr.aallouv.swingy.model.entity.Hero;
import fr.aallouv.swingy.model.entity.HeroClass;
import fr.aallouv.swingy.util.ConfigLoader;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostgresHeroRepository implements HeroRepository {

    private final Connection connection;

    public PostgresHeroRepository(ConfigLoader config) throws SQLException {
        this.connection = DriverManager.getConnection(
                config.getJdbcUrl(),
                config.getUser(),
                config.getPassword()
        );
        initSchema();
    }

    private void initSchema() throws SQLException {
        String sql = """
                CREATE TABLE IF NOT EXISTS heroes (
                    name        VARCHAR(50) PRIMARY KEY,
                    hero_class  VARCHAR(20) NOT NULL,
                    level       INT NOT NULL,
                    experience  INT NOT NULL,
                    gold        INT NOT NULL,
                    hp          INT NOT NULL,
                    max_hp      INT NOT NULL,
                    attack      INT NOT NULL,
                    defense     INT NOT NULL
                );
                """;
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        }
    }

    @Override
    public void save(Hero hero) {
        String sql = """
                INSERT INTO heroes (name, hero_class, level, experience, gold, hp, max_hp, attack, defense)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
                ON CONFLICT (name) DO UPDATE SET
                    hero_class = EXCLUDED.hero_class,
                    level      = EXCLUDED.level,
                    experience = EXCLUDED.experience,
                    gold       = EXCLUDED.gold,
                    hp         = EXCLUDED.hp,
                    max_hp     = EXCLUDED.max_hp,
                    attack     = EXCLUDED.attack,
                    defense    = EXCLUDED.defense;
                """;
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, hero.getName());
            stmt.setString(2, hero.getHeroClass().name());
            stmt.setInt(3, hero.getLevel());
            stmt.setInt(4, hero.getExperience());
            stmt.setInt(5, hero.getGold());
            stmt.setInt(6, hero.getHitPoints());
            stmt.setInt(7, hero.getMaxHitPoints());
            stmt.setInt(8, hero.getAttack());
            stmt.setInt(9, hero.getDefense());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erreur lors de la sauvegarde du héros : " + e.getMessage());
        }
    }

    @Override
    public void delete(String heroName) {
        String sql = "DELETE FROM heroes WHERE name = ?;";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, heroName);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression du héros : " + e.getMessage());
        }
    }

    @Override
    public List<Hero> findAll() {
        List<Hero> heroes = new ArrayList<>();
        String sql = "SELECT * FROM heroes;";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Hero hero = deserialize(rs);
                if (hero != null) heroes.add(hero);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la lecture des héros : " + e.getMessage());
        }
        return heroes;
    }

    private Hero deserialize(ResultSet rs) {
        try {
            String name      = rs.getString("name");
            HeroClass hClass = HeroClass.valueOf(rs.getString("hero_class"));
            int level        = rs.getInt("level");
            int experience   = rs.getInt("experience");
            int gold         = rs.getInt("gold");
            int hp           = rs.getInt("hp");
            int maxHp        = rs.getInt("max_hp");
            int attack       = rs.getInt("attack");
            int defense      = rs.getInt("defense");

            Hero hero = new Hero.Builder(name, hClass)
                    .level(level)
                    .experience(experience)
                    .gold(gold)
                    .build();

            hero.setMaxHitPoints(maxHp);
            hero.setHitPoints(hp);
            hero.setAttack(attack);
            hero.setDefense(defense);

            return hero;
        } catch (SQLException e) {
            System.err.println("Erreur lors de la désérialisation d'un héros : " + e.getMessage());
            return null;
        }
    }
}
