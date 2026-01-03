# 🕹️ Swingy

Swingy is a **text-based RPG game** developed in **Java**, as part of the Java projects at **42**.  
The goal of the project is to learn how to build a clean **MVC architecture**, handle **console and GUI inputs**, and manage a simple game logic with persistence.

---

## 📌 Project Overview

In Swingy, the player controls a hero exploring a **procedurally generated labyrinth**.  
The objective is to **kill the boss** while surviving encounters, managing resources, and making strategic choices.

The game can be played in:
- **Console mode**
- **Graphical mode (Swing GUI)**

---

## 🎯 Features

- Text-based RPG gameplay
- Procedural labyrinth generation
- Multiple room types (combat, rest, trap, choice, etc.)
- Turn-based movement (North, South, East, West)
- Hero creation and selection
- Combat system with physical and psychic stats
- Artifacts (Weapon, Armor, Helm)
- Gold system
- Hero persistence (save/load)
- Input validation using annotations
- MVC architecture
- Runnable JAR built with Maven

---

## 🧙 Hero

Each hero has the following attributes:
- Name
- Class
- Level
- Experience
- Hit Points
- Attack / Defense
- Psychic Attack / Psychic Defense
- Gold
- Equipped artifacts

Heroes are saved when exiting the game and loaded at startup.

---

## 🗺️ Map & Rooms

The game map is a **labyrinth** made of interconnected rooms.

### Room Types

- **Start Room** – Starting point of the hero
- **Combat Room** – Standard enemy encounter
- **Elite Room** – Stronger enemy with better rewards
- **Boss Room** – Powerful enemy guarding progression
- **Rest Room** – Restores part of the hero’s HP
- **Trap Room** – Inflicts direct damage
- **Choice Room** – Player choices (shop, stat boost, risk/reward)
- **Distortion Room** – Teleports the hero to another location
- **Exit Room** – Winning condition

Each room has multiple visual variants depending on available exits (N/E/S/W).

---

## ⚔️ Combat

Combat is automatically resolved based on:
- Physical stats
- Psychic stats
- A small random factor

If the hero loses a fight, the game ends.  
If the hero wins, they gain:
- Experience
- Gold
- A chance to obtain an artifact

---

## 📈 Experience & Leveling

Experience required for the next level follows this formula:

```
XP = level * 1000 + (level - 1)² * 450
```


Leveling up improves the hero’s stats.

---

## 🧠 Architecture

The project follows the **Model–View–Controller (MVC)** pattern.

### Model
- Hero, Monster, Entity
- Map, SlotMap

### View
- Console view
- Swing GUI view

### Controller
- Game controller
- Input handling
- Game flow logic

Persistence is handled separately to allow easy evolution.

---

## ▶️ How to Run

### Build the project
```bash
mvn clean package
```

### Run in console mode

```bash
java -jar target/swingy-1.0.0.jar console 50
```

### Run in GUI mode

```bash
java -jar target/swingy-1.0.0.jar gui 50
```

*The number `50` represents the number of room*

---

## 🛠️ Technologies
- Java
- Swing (for GUI)
- Maven (for build and dependency management)
- Annotations (for input validation)