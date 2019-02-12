package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.storage.MealDaoImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MealServlet extends HttpServlet {

    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);

    private static String INSERT_OR_EDIT = "/mealEditor.jsp";
    private static String LIST_MEALS = "/meals.jsp";
    private MealDao dao;

    public MealServlet() {
        super();
        dao = new MealDaoImpl();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("enter doGet method.");
        String forward = "";
        String action = request.getParameter("action");

        switch (action) {
            case "delete": {
                long id = Integer.parseInt(request.getParameter("id"));
                log.debug("request on delete by id {}.", id);
                dao.deleteMeal(id);
                forward = LIST_MEALS;
                request.setAttribute("meals", dao.getAllMeals());
                break;
            }
            case "edit": {
                forward = INSERT_OR_EDIT;
                long id = Integer.parseInt(request.getParameter("id"));
                log.debug("select meal for edit by id {}.", id);
                MealTo mealTo = dao.getMealById(id);
                request.setAttribute("mealTo", mealTo);
                break;
            }
            case "listMeal": {
                log.debug("request on select meals.");
                forward = LIST_MEALS;
                request.setAttribute("meals", dao.getAllMeals());
                break;
            }
            default: {
                log.debug("request on editor page.");
                forward = INSERT_OR_EDIT;
                break;
            }
        }

        RequestDispatcher view = request.getRequestDispatcher(forward);
        view.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("enter doPost method.");
        request.setCharacterEncoding("UTF-8");
        MealTo mealTo = parseRequestToMeal(request);

        if (mealTo.getId() == null) {
            log.debug("creating new Meal.");
            dao.addMeal(mealTo);
        } else {
            log.debug("updating Meal by id {}.", mealTo.getId());
            dao.updateMeal(mealTo);
        }
        RequestDispatcher view = request.getRequestDispatcher(LIST_MEALS);
        request.setAttribute("meals", dao.getAllMeals());
        view.forward(request, response);
    }

    private MealTo parseRequestToMeal(HttpServletRequest request) {
        log.debug("parse request to mealTo object.");
        String mealToID = request.getParameter("id");
        Long id = mealToID == null || mealToID.isEmpty() ? null : Long.parseLong(mealToID);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd.MM.yyyy");
        LocalDateTime ldt = LocalDateTime.parse(request.getParameter("dateTime"), formatter);
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories").trim());
        boolean excess = Boolean.parseBoolean(request.getParameter("excess"));
        return new MealTo(id, ldt, description, calories, excess);
    }
}
