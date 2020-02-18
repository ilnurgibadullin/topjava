package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
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

@Repository
public class InMemoryMealRepository implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepository.class);
    private Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(meal -> save(meal, 1));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        log.info("save {} to userId={}", meal, userId);
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
        log.info("delete {} from userId={}", id, userId);
        if (repository.get(userId) != null && repository.get(userId).get(id) != null) {
            return repository.get(userId).remove(id) != null;
        }
        return false;
    }

    @Override
    public Meal get(int id, int userId) {
        log.info("get {} from userId={}", id, userId);
        if (repository.get(userId) != null && repository.get(userId).get(id) != null) {
            return repository.get(userId).get(id);
        }
        return null;
    }

    @Override
    public List<Meal> getAll(int userId) {
        log.info("getAll from userId={}", userId);
        if (repository.get(userId) != null || !repository.get(userId).isEmpty()) {
            return repository.get(userId).values().stream()
                    .sorted((meal, t1) -> t1.getDateTime().compareTo(meal.getDateTime()))
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();

    }

    @Override
    public List<Meal> getBetween(LocalDate startDate, LocalDate endDate, int userId) {
        log.info("getAllBetween from startDate={} to endDate={} from userId={}", startDate, endDate, userId);
        return getAll(userId).stream()
                    .filter(meal -> DateTimeUtil.isBetweenInclusive(meal.getDate(), startDate, endDate))
                    .sorted((meal, t1) -> t1.getDateTime().compareTo(meal.getDateTime()))
                    .collect(Collectors.toList());
    }
}

