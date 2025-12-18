# My Java Application

Un projet Java simple construit avec Maven.

## Structure du projet

```
.
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/example/
│   │           └── App.java
│   └── test/
│       └── java/
│           └── com/example/
│               └── AppTest.java
├── pom.xml
└── README.md
```

## Prérequis

- Java 11 ou supérieur
- Maven 3.6.0 ou supérieur

## Compilation et construction

### Générer le fichier JAR

```bash
mvn clean package
```

Le fichier JAR généré sera situé dans le répertoire `target/` :
- `target/my-java-app-1.0.0.jar` (JAR exécutable)
- `target/my-java-app-1.0.0-shaded.jar` (JAR avec dépendances - uber JAR)

### Exécuter le JAR

```bash
java -jar target/my-java-app-1.0.0-shaded.jar
```

### Autres commandes utiles

```bash
# Nettoyer le projet
mvn clean

# Compiler uniquement
mvn compile

# Exécuter les tests
mvn test

# Installer dans le dépôt local
mvn install
```

## Notes

- La classe principale est `fr.aallouv.App`
- Le projet est configuré pour Java 11
- Le plugin Maven Shade génère un JAR avec toutes les dépendances (uber JAR)
