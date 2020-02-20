package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepository.class);
    private final Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    @PostConstruct
    public void postConstruct() {
        MealsUtil.MEALS.forEach(meal -> save(meal, 1));
    }


    @Override
    public Meal save(Meal meal, int userId) {
        log.info("save {} to userId={}", meal, userId);
        if (repository.containsKey(userId)) {
            if (repository.get(userId) != null) {
                if (meal.isNew()) {
                    meal.setId(counter.incrementAndGet());
                    return repository.get(userId).put(meal.getId(), meal);
                } else {
                    if (repository.get(userId).get(meal.getId()) != null) {
                        return repository.get(userId).computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
                    }
                }
            } else {
                if (meal.isNew()) {
                    meal.setId(counter.incrementAndGet());
                    HashMap<Integer, Meal> map = new HashMap<>();
                    map.put(meal.getId(), meal);
                    repository.put(userId, map);
                    return repository.get(userId).get(meal.getId());
                }
            }
        }
        return null;
    }

    @Override
    public boolean delete(int id, int userId) {
        log.info("delete {} from userId={}", id, userId);
        if (repository.containsKey(userId)) {
            if (repository.get(userId) != null && repository.get(userId).get(id) != null) {
                return repository.get(userId).remove(id) != null;
            }
        }
        return false;
    }

    @Override
    public Meal get(int id, int userId) {
        log.info("get {} from userId={}", id, userId);
        if (repository.containsKey(userId)) {
            if (repository.get(userId) != null && repository.get(userId).get(id) != null) {
                return repository.get(userId).get(id);
            }
        }
        return null;
    }

    @Override
    public List<Meal> getAll(int userId) {
        log.info("getAll from userId={}", userId);
        return filteredByStream(userId, meal -> true);
    }

    @Override
    public List<Meal> getBetween(LocalDate startDate, LocalDate endDate, int userId) {
        log.info("getAllBetweenDates from startDate={} to endDate={} from userId={}", startDate, endDate, userId);
        return filteredByStream(userId, meal -> DateTimeUtil.isBetweenInclusive(meal.getDate(), startDate, endDate));
    }

    private List<Meal> filteredByStream(int userId, Predicate<Meal> filter) {
        if (repository.containsKey(userId)) {
            if (repository.get(userId) != null && !repository.get(userId).isEmpty()) {
                return repository.get(userId).values().stream()
                        .filter(filter)
                        .sorted((meal, t1) -> t1.getDateTime().compareTo(meal.getDateTime()))
                        .collect(Collectors.toList());
            }
        }
        return Collections.emptyList();
    }
}