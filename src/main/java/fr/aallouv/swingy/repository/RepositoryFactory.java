package fr.aallouv.swingy.repository;

import fr.aallouv.swingy.util.ConfigLoader;

import java.sql.SQLException;

public class RepositoryFactory {

    public static HeroRepository create() {
        ConfigLoader config = new ConfigLoader("config.yml");
        try {
            PostgresHeroRepository repo = new PostgresHeroRepository(config);
            System.out.println("Connecté à la base de données PostgreSQL.");
            return repo;
        } catch (SQLException e) {
            System.err.println("Impossible de se connecter à la DB : " + e.getMessage());
            System.err.println("Utilisation du fichier texte à la place.");
            return new FileHeroRepository();
        }
    }
}
