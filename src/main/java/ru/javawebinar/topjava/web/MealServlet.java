package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.service.Service;
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
import java.util.concurrent.atomic.AtomicLong;

import static org.slf4j.LoggerFactory.getLogger;

@WebServlet("/meals")
public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(UserServlet.class);
    private static final long serialVersionUID = 1L;
    private static final String INSERT_OR_EDIT = "/meal.jsp";
    private static final String LIST_MEAL = "/meals.jsp";
    private static final Service<Meal> service = new MealService(new MealDao());
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        log.debug("forward to meals");

        List<MealTo> mealToList = MealsUtil.filteredByStreams(service.getAll(), LocalTime.MIN, LocalTime.MAX, 2000);
        String forward = "";
        String action = request.getParameter("action");
        if (action.equalsIgnoreCase("delete")){
            long id = Long.parseLong(request.getParameter("id"));
            service.delete(id);
            forward = LIST_MEAL;
            request.setAttribute("mealsTo", mealToList);
        } else if (action.equalsIgnoreCase("edit")){
            forward = INSERT_OR_EDIT;
            long mealId = Long.parseLong(request.getParameter("id"));
            Meal mealTo = service.get(mealId);
            request.setAttribute("mealTo", mealTo);
        } else if (action.equalsIgnoreCase("listMeal")) {
            forward = LIST_MEAL;
            request.setAttribute("mealsTo", mealToList);
        } else {
            forward = INSERT_OR_EDIT;
        }
        request.getRequestDispatcher(forward).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Meal meal;
        String id = request.getParameter("id");
        if (id == null || id.isEmpty()) {
            meal = new Meal(new AtomicLong(MealDao.count.incrementAndGet()),
                    LocalDateTime.parse(request.getParameter("dateTime"), DATE_TIME_FORMATTER),
                    request.getParameter("description"), Integer.parseInt(request.getParameter("calories")));
            service.save(meal);
        } else {
            meal = new Meal(new AtomicLong(Long.parseLong(id)),
                    LocalDateTime.parse(request.getParameter("dateTime"), DATE_TIME_FORMATTER),
                    request.getParameter("description"), Integer.parseInt(request.getParameter("calories")));
            service.update(meal);
        }
        List<MealTo> mealToList = MealsUtil.filteredByStreams(service.getAll(), LocalTime.MIN, LocalTime.MAX, 2000);
        request.setAttribute("mealsTo", mealToList);
        request.getRequestDispatcher(LIST_MEAL).forward(request, response);
    }
}
