package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MealStorage {
    private static Map<Long, Meal> dataBase;

    public static Map<Long, Meal> getTableMeals() {
        if (dataBase != null)
            return dataBase;
        else {
            dataBase = new ConcurrentHashMap<>();
            for (Meal meal : getMealsList())
                dataBase.put(meal.getId(), meal);
        }
        return dataBase;
    }

    private static List<Meal> getMealsList() {
        List<Meal> list = new ArrayList<>();
        list.add(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
        list.add(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000));
        list.add(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500));
        list.add(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000));
        list.add(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500));
        list.add(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));
        list.add(new Meal(LocalDateTime.of(2015, Month.JUNE, 1, 10, 0), "Завтрак", 300));
        list.add(new Meal(LocalDateTime.of(2015, Month.JUNE, 1, 13, 0), "Обед", 500));
        list.add(new Meal(LocalDateTime.of(2015, Month.JUNE, 1, 20, 0), "Ужин", 300));
        list.add(new Meal(LocalDateTime.of(2015, Month.JUNE, 2, 10, 0), "Завтрак", 1000));
        list.add(new Meal(LocalDateTime.of(2015, Month.JUNE, 2, 13, 0), "Обед", 1500));
        list.add(new Meal(LocalDateTime.of(2015, Month.JUNE, 2, 20, 0), "Ужин", 700));
        return list;
    }
}
