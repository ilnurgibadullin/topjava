package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int BREAKFAST_USER_ID = START_SEQ + 2;
    public static final int LUNCH_USER_ID = START_SEQ + 3;
    public static final int DINNER_USER_ID = START_SEQ + 4;
    public static final int LUNCH_ADMIN_ID = START_SEQ + 5;
    public static final int DINNER_ADMIN_ID = START_SEQ + 6;

    public static final Meal BREAKFAST_USER = new Meal(BREAKFAST_USER_ID,
            LocalDateTime.of(2020, 2,23, 8, 0, 0),
            "Завтрак", 1200);
    public static final Meal LUNCH_USER = new Meal(LUNCH_USER_ID,
            LocalDateTime.of(2020, 2,24, 12, 0, 0),
            "Обед", 1000);
    public static final Meal DINNER_USER = new Meal(DINNER_USER_ID,
            LocalDateTime.of(2020, 2,24, 19, 0, 0),
            "Ужин", 1700);
    public static final Meal LUNCH_ADMIN = new Meal(LUNCH_ADMIN_ID,
            LocalDateTime.of(2020, 2,23, 12, 30, 0),
            "Обед", 1500);
    public static final Meal DINNER_ADMIN = new Meal(DINNER_ADMIN_ID,
            LocalDateTime.of(2020, 2,23, 23, 0, 0),
            "Ночной жор", 200);

    public static Meal getNew() {
        return new Meal(null, LocalDateTime.now(), "Еда", 500);
    }

    public static Meal getUpdated() {
        Meal updated = new Meal(BREAKFAST_USER);
        updated.setDescription("UpdatedDescription");
        updated.setCalories(330);
        return updated;
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualToComparingFieldByField(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingFieldByFieldElementComparator().isEqualTo(expected);
    }
}
