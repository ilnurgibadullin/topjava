package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.util.List;

public interface MealService {

    Meal create(Meal meal, int userId);

    void update(Meal meal, int userId);

    void delete(int id, int userId);

    Meal get(int id, int userId);

    List<Meal> getAll(int userId);

    List<Meal> getBetweenDates(LocalDate startDate, LocalDate endDate, int userId);
}
