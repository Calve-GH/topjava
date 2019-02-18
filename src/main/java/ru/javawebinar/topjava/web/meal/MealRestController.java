package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.web.SecurityUtil.*;

@Controller
public class MealRestController {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    private MealService service;

    @Autowired
    public MealRestController(MealService service) {
        this.service = service;
    }

    public void delete(int id) throws NotFoundException {
        service.delete(authUserId(), id);
    }

    public MealTo get(int id) throws NotFoundException {
        return service.get(authUserId(), id);
    }

    public void save(Meal meal) {
        service.create(authUserId(), meal);
    }

    public List<MealTo> getAll() {
        return service.getAll(authUserId(), authUserCaloriesPerDay());
    }

    public List<MealTo> getAll(LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime) {
        LocalDate sd = startDate == null ? LocalDate.MIN : startDate;
        LocalTime st = startTime == null ? LocalTime.MIN : startTime;
        LocalDate ed = endDate == null ? LocalDate.MAX : endDate;
        LocalTime et = endTime == null ? LocalTime.MAX : endTime;
        return service.getAll(authUserId(), authUserCaloriesPerDay(), sd, st ,ed, et);
    }
}