package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
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
import java.util.Optional;
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
        service = new MealService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        log.debug("forward to meals");

        List<MealTo> mealToList = MealsUtil.filteredByStreams(service.getAll(), LocalTime.MIN, LocalTime.MAX, 2000);

        String forward;
        String action = request.getParameter("action");

        if (action.equalsIgnoreCase("delete")){
            long mealId = Long.parseLong(request.getParameter("mealId"));
            service.delete(mealId);
            forward = LIST_MEAL;
            request.setAttribute("mealsTo", mealToList);
        } else if (action.equalsIgnoreCase("edit")){
            forward = INSERT_OR_EDIT;
            long mealId = Long.parseLong(request.getParameter("mealId"));
            Optional<Meal> meal = service.get(mealId);
            request.setAttribute("meal", meal.get());
        } else if (action.equalsIgnoreCase("listMeal")){
            forward = LIST_MEAL;
            request.setAttribute("meals", mealToList);
        } else {
            forward = INSERT_OR_EDIT;
        }
        request.getRequestDispatcher(forward).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<MealTo> mealToList = MealsUtil.filteredByStreams(service.getAll(), LocalTime.MIN, LocalTime.MAX, 2000);
        Meal meal;
        meal = new Meal(LocalDateTime.parse(req.getParameter("dob"),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), req.getParameter("description"),
                    Integer.parseInt(req.getParameter("calories")));
        String mealId = req.getParameter("mealid");
        if(mealId == null || mealId.isEmpty()) {
            service.save(meal);
        } else {
            meal.setId(new AtomicLong(Integer.parseInt(mealId)));
            service.update(meal);
        }
        req.setAttribute("mealsTo", mealToList);
        req.getRequestDispatcher(LIST_MEAL).forward(req, resp);
    }
}
