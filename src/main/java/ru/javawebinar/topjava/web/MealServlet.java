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
import java.text.ParseException;
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
    private static String INSERT_OR_EDIT = "/meal.jsp";
    private static String LIST_MEAL = "/meals.jsp";
    private Service<Meal> service;

    public MealServlet() {
        super();
        service = new MealService(new MealDao());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        log.debug("forward to meals");

        List<MealTo> mealToList = MealsUtil.filteredByStreams(service.getAll(), LocalTime.MIN, LocalTime.MAX, 2000);
        String forward = "";
        String action = request.getParameter("action");
        if (action != null && action.equalsIgnoreCase("delete")){
            long mealId = Long.parseLong(request.getParameter("mealId"));
            service.delete(mealId);
            forward = LIST_MEAL;
            request.setAttribute("mealsTo", mealToList);
        } else if (action != null && action.equalsIgnoreCase("edit")){
            forward = INSERT_OR_EDIT;
            long mealId = Long.parseLong(request.getParameter("mealId"));
            Meal meal = service.get(mealId);
            if (meal != null) {
                request.setAttribute("meal", meal);
            }
        } else {
            forward = LIST_MEAL;
            request.setAttribute("meals", mealToList);
        }
        request.getRequestDispatcher(forward).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<MealTo> mealToList = MealsUtil.filteredByStreams(service.getAll(), LocalTime.MIN, LocalTime.MAX, 2000);
        Meal meal = new Meal(LocalDateTime.parse(request.getParameter("dateTime"),
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));
        String mealId = request.getParameter("mealid");
        if (mealId == null || mealId.isEmpty()) {
            service.save(meal);
        } else {
            meal.setId(new AtomicLong(Long.parseLong(mealId)));
            service.update(meal);
        }
        request.setAttribute("mealsTo", mealToList);
        request.getRequestDispatcher(LIST_MEAL).forward(request, response);
    }
}
