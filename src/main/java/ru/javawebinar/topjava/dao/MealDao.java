package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.MealTo;

import java.util.List;

public interface MealDao {

    void addMeal(MealTo meal);

    void deleteMeal(long id);

    void updateMeal(MealTo meal);

    List<MealTo> getAllMeals();

    MealTo getMealById(long id);
}
