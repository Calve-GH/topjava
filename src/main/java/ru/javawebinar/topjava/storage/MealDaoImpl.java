package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MealDaoImpl implements MealDao {

    private static final int CALORIES_PER_DAY = 2000;

    private Map<Long, Meal> dataBase;

    public MealDaoImpl() {
        dataBase = MealStorage.getTableMeals();
    }

    public void addMeal(MealTo user) {
        Meal tmp = new Meal(user.getDateTime(), user.getDescription(), user.getCalories());
        dataBase.put(tmp.getId(), tmp);
    }

    public void deleteMeal(long userId) {
        dataBase.remove(userId);
    }

    public void updateMeal(MealTo user) {
        Meal tmp = new Meal(user.getId(), user.getDateTime(), user.getDescription(), user.getCalories());
        dataBase.replace(tmp.getId(), tmp);
    }

    public List<MealTo> getAllMeals() {
        return MealsUtil.getFilteredWithExcessInOnePass2(new ArrayList<>(dataBase.values()), LocalTime.MIN,
                LocalTime.MAX, CALORIES_PER_DAY);
    }

    @Override
    public MealTo getMealById(long id) {
        return MealsUtil.createWithExcess(dataBase.get(id), false);
    }
}
