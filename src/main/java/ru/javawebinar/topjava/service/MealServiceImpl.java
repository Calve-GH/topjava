package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.*;

@Service
public class MealServiceImpl implements MealService {

    private final MealRepository repository;

    @Autowired
    public MealServiceImpl(MealRepository repository) {
        this.repository = repository;
    }

    @Override
    public void delete(int userId, int id) throws NotFoundException {
        checkNotFoundWithId(repository.delete(userId, id), id);
    }

    @Override
    public MealTo get(int userId, int id) throws NotFoundException {
        return MealsUtil.createWithExcess(checkNotFoundWithId(repository.get(userId, id), id), false);
    }

    @Override
    public MealTo create(int userId, Meal meal) {
        checkNew(meal);
        return MealsUtil.createWithExcess(repository.save(userId, meal), false);
    }

    @Override
    public void update(int userId, Meal meal) throws NotFoundException {
        assureIdConsistent(meal, repository.save(userId, meal).getId());
    }

    @Override
    public List<MealTo> getAll(int userId, int caloriesPerDay) {
        return MealsUtil.getWithExcess(repository.getAll(userId), caloriesPerDay);
    }

    @Override
    public List<MealTo> getAll(int userId, int caloriesPerDay, LocalDate startDate, LocalTime startTime,
                               LocalDate endDate, LocalTime endTime) {
        return MealsUtil.getFilteredWithExcess(repository.getAll(userId, startDate, endDate), caloriesPerDay,
                startTime, endTime);
    }
}