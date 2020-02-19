package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.util.Arrays;
import java.util.List;

public class UsersUtil {
    public static final List<User> USERS = Arrays.asList(
            new User(null, "Вася", "vasya@gmail.com", "123", Role.ROLE_USER),
            new User(null, "Дима", "dima@gmail.com", "321", Role.ROLE_USER),
            new User(null, "Вова", "vova@gmail.com", "111", Role.ROLE_ADMIN)
    );

}
