package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class MealMemoryDao implements Dao<Meal> {
    private final AtomicLong count = new AtomicLong(0);
    private final Map<Long, Meal> mealHashMap = new ConcurrentHashMap<>();

    public MealMemoryDao() {
        save(new Meal(0, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));
        save(new Meal(0, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000));
        save(new Meal(0, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100));
        save(new Meal(0, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500));
        save(new Meal(0, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000));
        save(new Meal(0, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500));
    }

    @Override
    public Meal get(long id) {
        return mealHashMap.getOrDefault(id, null);
    }

    @Override
    public List<Meal> getAll() {
        return (List<Meal>) mealHashMap.values();
    }

    @Override
    public void save(Meal meal) {
        long id = count.incrementAndGet();
        mealHashMap.put(id, new Meal(id, meal.getDateTime(), meal.getDescription(), meal.getCalories()));
    }

    @Override
    public void update(Meal meal) {
        mealHashMap.put(meal.getId(), meal);
    }

    @Override
    public void delete(long id) {
        mealHashMap.remove(id);
    }
}
