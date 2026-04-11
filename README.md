# Swingy

RPG textuel en Java avec mode console et mode GUI (Java Swing).  
Projet réalisé dans le cadre du cursus 42.

---

## Prérequis

- Java 17
- Maven
- Docker (optionnel, pour la persistance PostgreSQL)

---

## Installation

```bash
git clone <repo>
cd Swingy
mvn clean package
```

Le jar exécutable est généré dans `target/swingy.jar`.

---

## Lancement

```bash
# Mode console
java -jar target/swingy.jar console

# Mode GUI
java -jar target/swingy.jar gui
```

---

## Base de données (bonus)

Le jeu tente de se connecter à une base PostgreSQL au démarrage.  
Si la connexion échoue, il bascule automatiquement sur un fichier texte (`heroes.txt`).

### Démarrer la base avec Docker

```bash
docker compose up -d
```

### Configuration

Modifie `config.yml` à la racine du projet :

```yaml
db:
  host: localhost
  port: 5432
  name: swingy
  user: postgres
  password: postgres
```

### Consulter les données

```bash
docker exec -it swingy_db psql -U postgres -d swingy
```

```sql
SELECT * FROM heroes;
```

---

## Gameplay

### Objectif
Naviguer sur la carte, vaincre le boss, puis atteindre la sortie.

### Déplacement
| Console | GUI | Action |
|---------|-----|--------|
| `n`     | ↑   | Nord   |
| `s`     | ↓   | Sud    |
| `e`     | →   | Est    |
| `w`     | ←   | Ouest  |

### Actions
| Console | GUI | Action                  |
|---------|-----|-------------------------|
| `1`     | `F` | Combattre               |
| `2`     | `R` | Fuir                    |
| `1`     | `E` | Équiper un artefact     |
| `2`     | `I` | Ignorer un artefact     |
| `1`     | `H` | Se soigner (RestRoom)   |
| `1`     | `O` | Ouvrir un coffre        |
| `2`     | `X` | Plus tard               |
| `2`     | `G` | Offre or (ChoiceRoom)   |
| `3`     | `T` | Sacrifice (ChoiceRoom)  |
| `q`     | `S` | Afficher les stats      |
| `gui`   | `C` | Basculer vers l'autre mode |
| `x`     | `M` | Retour au menu          |

---

## Salles

| Salle      | Description                                              |
|------------|----------------------------------------------------------|
| Start      | Salle de départ                                          |
| Combat     | Combat standard                                          |
| Elite      | Combat difficile, meilleure récompense                   |
| Boss       | Boss unique — doit être vaincu pour débloquer la sortie  |
| Exit       | Sortie du niveau — accessible uniquement après le boss   |
| Rest       | Se soigner (une seule fois)                              |
| Trap       | Inflige des dégâts directs                               |
| Choice     | Choix entre offre or ou sacrifice de stats               |
| Coffre     | Or à ramasser                                            |
| Distortion | Téléportation vers une salle déjà visitée                |

---

## Classes de héros

| Classe  | HP  | ATK | DEF |
|---------|-----|-----|-----|
| Warrior | 120 | 15  | 8   |
| Mage    | 70  | 8   | 4   |
| Rogue   | 90  | 12  | 5   |

---

## Architecture

```
src/main/java/fr/aallouv/swingy/
├── Main.java
├── ViewSwitcher.java
├── controller/
│   └── GameController.java
├── model/
│   ├── entity/       # Entity, Hero, Villain, HeroClass
│   ├── artifact/     # Artifact, ArtifactType
│   ├── map/          # GameMap, Room et sous-classes, Direction
│   └── game/         # GameState, GameEngine, CombatResult
├── view/
│   ├── View.java
│   ├── console/      # ConsoleView, ConsoleRunner
│   └── gui/          # MainWindow, MenuPanel, GamePanel, RoomPanel, MinimapPanel
├── repository/
│   ├── HeroRepository.java
│   ├── FileHeroRepository.java
│   ├── PostgresHeroRepository.java
│   └── RepositoryFactory.java
└── util/
    ├── AssetLoader.java
    └── ConfigLoader.java
```

Le projet respecte l'architecture **MVC** :
- **Model** — logique métier pure, sans dépendance vers la vue
- **View** — interface `View` implémentée par `ConsoleView` et `GamePanel`
- **Controller** — `GameController` fait le lien entre les deux

---

## Dépendances

| Librairie           | Version | Usage                                    |
|---------------------|---------|------------------------------------------|
| Hibernate Validator | 8.0.1   | Validation des inputs (javax.validation) |
| PostgreSQL JDBC     | 42.7.3  | Connexion à la base de données (bonus)   |
| SnakeYAML           | 2.2     | Lecture du fichier config.yml (bonus)    |

---

## Merci

Merci à Nathan mon ami tout de les jours pour les assets. [Portefolio](https://www.artstation.com/nathanlejeune)