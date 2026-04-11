package fr.aallouv.swingy.util;

import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class ConfigLoader {

    private final Map<String, String> dbConfig;

    public ConfigLoader(String path) {
        Yaml yaml = new Yaml();
        Map<String, Object> root = null;

        try (InputStream is = new FileInputStream(path)) {
            root = yaml.load(is);
        } catch (IOException e) {
            System.err.println("config.yml introuvable, utilisation des valeurs par défaut.");
        }

        if (root != null && root.containsKey("db")) {
            Map<String, Object> db = (Map<String, Object>) root.get("db");
            dbConfig = Map.of(
                    "host",     String.valueOf(db.getOrDefault("host", "localhost")),
                    "port",     String.valueOf(db.getOrDefault("port", 5432)),
                    "name",     String.valueOf(db.getOrDefault("name", "swingy")),
                    "user",     String.valueOf(db.getOrDefault("user", "postgres")),
                    "password", String.valueOf(db.getOrDefault("password", "postgres"))
            );
        } else {
            dbConfig = Map.of(
                    "host", "localhost",
                    "port", "5432",
                    "name", "swingy",
                    "user", "postgres",
                    "password", "postgres"
            );
        }
    }

    public String getHost()     { return dbConfig.get("host"); }
    public String getPort()     { return dbConfig.get("port"); }
    public String getName()     { return dbConfig.get("name"); }
    public String getUser()     { return dbConfig.get("user"); }
    public String getPassword() { return dbConfig.get("password"); }

    public String getJdbcUrl() {
        return "jdbc:postgresql://" + getHost() + ":" + getPort() + "/" + getName();
    }
}
