package ru.javawebinar.topjava.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.service.UserService;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

@Controller
@RequestMapping("/meals")
public class JspMealController {
    @Autowired
    private MealService service;

    @GetMapping(value = {"/all", "/"})
    public String getMeals(Model model) {
        int userId = SecurityUtil.authUserId();
        model.addAttribute("meals", service.getAll(userId));
        return "forward:meals";
    }

    @GetMapping("/delete")
    public String deleteMeal(HttpServletRequest request) {
        int id = getId(request);
        int userId = SecurityUtil.authUserId();
        service.delete(id, userId);
        return "redirect:meals";
    }

    @GetMapping("/create")
    public String createMeal(Model model) {
        Meal meal = new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000);
        model.addAttribute("meal", meal);
        return "forward:mealForm";
    }

    @GetMapping("/update")
    public String updateMeal(Model model, HttpServletRequest request) {
        int userId = SecurityUtil.authUserId();
        Meal meal = service.get(getId(request), userId);
        model.addAttribute("meal", meal);
        return "forward:mealForm";
    }

    @GetMapping("/filter")
    public String filterMeals(Model model, HttpServletRequest request) {
        LocalDate startDate = parseLocalDate(request.getParameter("startDate"));
        LocalDate endDate = parseLocalDate(request.getParameter("endDate"));
        LocalTime startTime = parseLocalTime(request.getParameter("startTime"));
        LocalTime endTime = parseLocalTime(request.getParameter("endTime"));
        int userId = SecurityUtil.authUserId();
        model.addAttribute("meals", MealsUtil.getFilteredTos(service.getBetweenInclusive(startDate, endDate, userId),
                SecurityUtil.authUserCaloriesPerDay(), startTime, endTime));
        return "forward:meals";
    }

    @PostMapping("/")
    public String createOrUpdateMeal(HttpServletRequest request) {
        Meal meal = new Meal(
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));
        int userId = SecurityUtil.authUserId();
        if (StringUtils.isEmpty(request.getParameter("id"))) {
            service.create(meal, userId);
        } else {
            service.update(meal, userId);
        }
        return "redirect:meals";
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }
}
