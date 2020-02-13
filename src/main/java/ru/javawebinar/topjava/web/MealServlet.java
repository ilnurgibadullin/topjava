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
    private static final String INSERT_OR_EDIT = "meal.jsp";
    private static final String LIST_MEAL = "meals.jsp";
    private final Dao<Meal> dao = new MealMemoryDao();
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("forward to meals");
        String action = request.getParameter("action").toLowerCase();
        switch (action == null ? "all" : action) {
            case "delete":
                dao.delete(Long.parseLong(request.getParameter("id")));
                response.sendRedirect(LIST_MEAL);
                break;
            case "edit":
                Meal meal = dao.get(Long.parseLong(request.getParameter("id")));
                request.setAttribute("meal", meal);
                request.getRequestDispatcher(INSERT_OR_EDIT).forward(request, response);
                break;
            case "insert":
                request.getRequestDispatcher(INSERT_OR_EDIT).forward(request, response);
                break;
            case "all":
            default :
                request.setAttribute("mealsTo",
                        MealsUtil.filteredByStreams(dao.getAll(), LocalTime.MIN, LocalTime.MAX, 2000));
                request.getRequestDispatcher(LIST_MEAL).forward(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        request.setCharacterEncoding("UTF-8");
        Meal meal;
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));
        LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("dateTime"), DATE_TIME_FORMATTER);
        String id = request.getParameter("id");
        if (id == null || id.isEmpty()) {
            meal = new Meal(0, dateTime, description, calories);
            dao.save(meal);
        } else {
            meal = new Meal(Long.parseLong(id), dateTime, description, calories);
            dao.update(meal);
        }
        response.sendRedirect(LIST_MEAL);
    }
}
