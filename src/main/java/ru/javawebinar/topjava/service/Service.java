package ru.javawebinar.topjava.service;

import java.util.List;

public interface Service<T> {

    T get(long id);

    List<T> getAll();

    void save(T t);

    void update(T t);

    void delete(long id);
}
