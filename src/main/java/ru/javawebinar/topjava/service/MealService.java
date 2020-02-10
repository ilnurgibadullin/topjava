package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.dao.Dao;
import ru.javawebinar.topjava.model.Meal;

import java.util.List;
import java.util.Optional;

public class MealService implements Service<Meal> {

    private Dao<Meal> dao;

    @Override
    public Optional<Meal> get(long id) {
        return Optional.empty();
    }

    @Override
    public List<Meal> getAll() {
        return dao.getAll();
    }

    @Override
    public void save(Meal meal) {

    }

    @Override
    public void update(Meal meal) {

    }

    @Override
    public void delete(long id) {

    }
}
