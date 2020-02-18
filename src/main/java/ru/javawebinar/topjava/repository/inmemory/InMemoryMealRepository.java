package ru.javawebinar.topjava.repository.inmemory;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class InMemoryMealRepository implements MealRepository {
    private Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        for (Meal meal : MealsUtil.MEALS) {
            repository.get(1).put(meal.getId(), meal);
        }
    }

    @Override
    public Meal save(Meal meal, int userId) {
        if (repository.get(userId) != null) {
            if (meal.isNew()) {
                meal.setId(counter.incrementAndGet());
                repository.get(userId).put(meal.getId(), meal);
                return meal;
            } else {
                if (repository.get(userId).get(meal.getId()) != null) {
                    return repository.get(userId).computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
                }
            }
        }
        return null;
    }

    @Override
    public boolean delete(int id, int userId) {
        if (repository.get(userId) != null && repository.get(userId).get(id) != null) {
            return repository.get(userId).remove(id) != null;
        }
        return false;
    }

    @Override
    public Meal get(int id, int userId) {
        if (repository.get(userId) != null && repository.get(userId).get(id) != null) {
            return repository.get(userId).get(id);
        }
        return null;
    }

    @Override
    public List<Meal> getAll(int userId) {
        if (repository.get(userId) != null || !repository.get(userId).isEmpty()) {
            return repository.get(userId).values().stream()
                    .sorted((meal, t1) -> t1.getDateTime().compareTo(meal.getDateTime()))
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();

    }

    @Override
    public List<Meal> getBetween(LocalDate startDate, LocalDate endDate, int userId) {
        return getAll(userId).stream()
                    .filter(meal -> DateTimeUtil.isBetweenInclusive(meal.getDate(), startDate, endDate))
                    .sorted((meal, t1) -> t1.getDateTime().compareTo(meal.getDateTime()))
                    .collect(Collectors.toList());
    }
}

