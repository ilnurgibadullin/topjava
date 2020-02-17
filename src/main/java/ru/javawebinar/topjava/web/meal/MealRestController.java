package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.service.MealServiceImpl;
import ru.javawebinar.topjava.to.MealTo;

import java.util.List;


@Controller
public class MealRestController {

    private MealServiceImpl service;

    @Autowired
    public MealRestController(MealServiceImpl service) {
        this.service = service;
    }

    public List<MealTo> getAll() {
        return null;
    }

}