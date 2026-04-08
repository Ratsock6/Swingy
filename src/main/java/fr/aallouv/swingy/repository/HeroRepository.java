package fr.aallouv.swingy.repository;

import fr.aallouv.swingy.model.entity.Hero;

import java.util.List;

public interface HeroRepository {
    void save(Hero hero);
    void delete(String heroName);
    List<Hero> findAll();
}
