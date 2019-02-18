package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import static ru.javawebinar.topjava.util.DateTimeUtil.DATE_FORMATTER;
import static ru.javawebinar.topjava.util.DateTimeUtil.TIME_FORMATTER;

public class MealServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);

    //private MealRepository repository;
    private ConfigurableApplicationContext appCtx;
    private MealRestController mealRestController;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        //repository = new InMemoryMealRepositoryImpl();

        appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml");
        mealRestController = appCtx.getBean(MealRestController.class);
    }

    @Override
    public void destroy() {
        super.destroy();
        appCtx.close();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");

        Meal meal = new Meal(id.isEmpty() ? null : Integer.valueOf(id),
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));

        log.info(meal.isNew() ? "Create {}" : "Update {}", meal);
        //repository.save(SecurityUtil.authUserId(), meal);
        mealRestController.save(meal);
        response.sendRedirect("meals");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        switch (action == null ? "all" : action) {
            case "delete":
                int id = getId(request);
                log.info("Delete {}", id);
                //repository.delete(SecurityUtil.authUserId(), id);
                mealRestController.delete(id);
                response.sendRedirect("meals");
                break;
            case "create":
            case "update":
                final Meal meal = "create".equals(action) ?
                        new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000) :
                        getById(getId(request));
                //repository.get(SecurityUtil.authUserId(), getId(request));
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("/mealForm.jsp").forward(request, response);
                break;
            case "filter": {
                log.info("filter");
                request.setAttribute("meals", mealRestController.getAll(
                        parseDate(request.getParameter("ds")),
                        parseTime(request.getParameter("ts")),
                        parseDate(request.getParameter("de")),
                        parseTime(request.getParameter("te"))));
                //MealsUtil.getWithExcess(repository.getAll(SecurityUtil.authUserId()), MealsUtil.DEFAULT_CALORIES_PER_DAY));
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
            }
            case "uid" : {
                switch (request.getParameter("user")) {
                    case "ADMIN" : SecurityUtil.setUserIfd(2);
                    break;
                    case "USER2" : SecurityUtil.setUserIfd(3);
                    break;
                    default: SecurityUtil.setUserIfd(1);
                    break;
                }
            }
            case "all":
            default:
                log.info("getAll");
                request.setAttribute("meals",
                        mealRestController.getAll());
                //MealsUtil.getWithExcess(repository.getAll(SecurityUtil.authUserId()), MealsUtil.DEFAULT_CALORIES_PER_DAY));
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }

    private Meal getById(int id) {
        MealTo mealTo = mealRestController.get(id);
        return new Meal(mealTo.getId(), mealTo.getDateTime(), mealTo.getDescription(), mealTo.getCalories());
    }

    private LocalDate parseDate(String stringDate) {
        try {
            return LocalDate.parse(stringDate, DATE_FORMATTER);
        } catch (Exception e) {
            return null;
        }
    }

    private LocalTime parseTime(String stringTime) {
        try {
            return LocalTime.parse(stringTime, TIME_FORMATTER);
        } catch (Exception e) {
            return null;
        }
    }
}
