package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface MealService {

    void delete(int userId, int id) throws NotFoundException;

    MealTo get(int userId, int id) throws NotFoundException;

    MealTo create(int userId, Meal meal);

    void update(int userId, Meal meal) throws NotFoundException;

    List<MealTo> getAll(int userId, int caloriesPerDay);

    List<MealTo> getAll(int userId, int caloriesPerDay, LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime);
}