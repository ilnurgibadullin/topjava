package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;

@Service
public class MealServiceImpl implements MealService {

    private final MealRepository repository;

    @Autowired
    public MealServiceImpl(MealRepository repository) {
        this.repository = repository;
    }

    public Meal create(Meal meal, int userId) {
        if (repository.save(meal, userId) != null) {
            return repository.save(meal, userId);
        } else {
            throw new NotFoundException("Not exists user");
        }
    }

    public void update(Meal meal, int userId) {
        if (get(meal.getId(), userId) != null) {
            repository.save(meal, userId);
        } else {
            throw new NotFoundException("Not exists user or not exists meal");
        }
    }

    public void delete(int id, int userId) {
        if (repository.get(id, userId) != null) {
            repository.delete(id, userId);
        } else {
            throw new NotFoundException("Not exists user or not exists meal");
        }
    }

    public Meal get(int id, int userId) {
        if (repository.get(id, userId) != null) {
            return repository.get(id, userId);
        } else {
            throw new NotFoundException("Not exists user or not exists meal");
        }
    }

    public List<Meal> getAll(int userId) {
        return repository.getAll(userId);
    }

    public List<Meal> getBetweenDates(@Nullable LocalDate startDate, @Nullable LocalDate endDate, int userId) {
        return repository.getBetween(startDate, endDate, userId);
    }
}