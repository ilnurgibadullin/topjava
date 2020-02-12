package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.Dao;
import ru.javawebinar.topjava.dao.MealMemoryDao;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

@WebServlet("/meals")
public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(UserServlet.class);
    private static final long serialVersionUID = 1L;
    private static final String INSERT_OR_EDIT = "/meal.jsp";
    private static final String LIST_MEAL = "/meals.jsp";
    private static final Dao<Meal> dao = new MealMemoryDao();
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("forward to meals");
        List<MealTo> mealsTo = MealsUtil.filteredByStreams(dao.getAll(), LocalTime.MIN, LocalTime.MAX, 2000);
        String forward;
        String action = request.getParameter("action").toLowerCase();
        switch (action) {
            case "delete":
                dao.delete(Long.parseLong(request.getParameter("id")));
                forward = LIST_MEAL;
                request.setAttribute("mealsTo", mealsTo);
                break;
            case "edit":
                forward = INSERT_OR_EDIT;
                Meal meal = dao.get(Long.parseLong(request.getParameter("id")));
                request.setAttribute("meal", meal);
                break;
            case "listmeal":
                forward = LIST_MEAL;
                request.setAttribute("mealsTo", mealsTo);
                break;
            default :
                forward = LIST_MEAL;
                break;
        }
        request.getRequestDispatcher(forward).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        Meal meal;
        String id = request.getParameter("id");
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));
        LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("dateTime"), DATE_TIME_FORMATTER);
        if (id == null || id.isEmpty()) {
            meal = new Meal(0, dateTime, description, calories);
            dao.save(meal);
        } else {
            meal = new Meal(Long.parseLong(id), dateTime, description, calories);
            dao.update(meal);
        }
        List<MealTo> mealsTo = MealsUtil.filteredByStreams(dao.getAll(), LocalTime.MIN, LocalTime.MAX, 2000);
        request.setAttribute("mealsTo", mealsTo);
        request.getRequestDispatcher(LIST_MEAL).forward(request, response);
    }
}
