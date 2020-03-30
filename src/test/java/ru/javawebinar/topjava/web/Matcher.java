package ru.javawebinar.topjava.web;

import org.assertj.core.matcher.AssertionMatcher;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.List;

import static ru.javawebinar.topjava.MealTestData.MEALS;
import static ru.javawebinar.topjava.MealTestData.MEALTO_MATCHER;
import static ru.javawebinar.topjava.util.MealsUtil.DEFAULT_CALORIES_PER_DAY;

public class Matcher extends AssertionMatcher<List<MealTo>> {

    @Override
    public void assertion(List<MealTo> actual) throws AssertionError {
        MEALTO_MATCHER.assertMatch(actual, MealsUtil.getTos(MEALS, DEFAULT_CALORIES_PER_DAY));
    }
}
