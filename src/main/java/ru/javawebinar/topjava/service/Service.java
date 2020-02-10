package ru.javawebinar.topjava.service;

import java.util.List;
import java.util.Optional;

public interface Service<T> {

    Optional<T> get(long id);

    List<T> getAll();

    void save(T t);

    void update(T t);

    void delete(long id);
}
