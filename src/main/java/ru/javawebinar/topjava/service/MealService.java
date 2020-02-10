package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.dao.Dao;
import ru.javawebinar.topjava.model.Meal;

import java.util.List;


public class MealService implements Service<Meal> {

    private Dao<Meal> dao;

    public MealService(Dao<Meal> dao) {
        this.dao = dao;
    }

    @Override
    public Meal get(long id) {
        return dao.get(id);
    }

    @Override
    public List<Meal> getAll() {
        return dao.getAll();
    }

    @Override
    public void save(Meal meal) {
        dao.save(meal);
    }

    @Override
    public void update(Meal meal) {
        dao.update(meal);
    }

    @Override
    public void delete(long id) {
        dao.delete(id);
    }
}
