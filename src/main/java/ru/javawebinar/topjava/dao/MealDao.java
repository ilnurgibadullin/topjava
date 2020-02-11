package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class MealDao implements Dao<Meal> {
    public static AtomicLong count = new AtomicLong(7);

    private List<Meal> mealList = Collections.synchronizedList(new ArrayList<>(Arrays.asList(
            new Meal(new AtomicLong(1), LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
            new Meal(new AtomicLong(2), LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
            new Meal(new AtomicLong(3), LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
            new Meal(new AtomicLong(4), LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
            new Meal(new AtomicLong(5), LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
            new Meal(new AtomicLong(6), LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
            new Meal(new AtomicLong(7), LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
    )));

    @Override
    public Meal get(long id) {
        for (Meal meal : mealList) {
            if (meal.getId().longValue() == id) {
                return meal;
            }
        }
        return null;
    }

    @Override
    public List<Meal> getAll() {
        return mealList;
    }

    @Override
    public void save(Meal meal) {
        mealList.add(meal);
    }

    @Override
    public void update(Meal meal) {
        save(meal);
        delete(meal.getId().longValue());
    }

    @Override
    public void delete(long id) {
        for (int i = 0; i < mealList.size(); i++) {
            if (mealList.get(i).getId().longValue() == id) {
                mealList.remove(i);
                break;
            }
        }
    }
}
