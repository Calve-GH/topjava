package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
        );
        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
//        .toLocalDate();
//        .toLocalTime();
    }

    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        return mealList
                .stream()
                .collect(Collectors.groupingBy(p -> p.getDateTime().toLocalDate())).entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getValue, e -> (Integer) e.getValue().stream().mapToInt(UserMeal::getCalories).sum())).entrySet()
                .stream()
                .flatMap(e -> e.getKey().stream().filter(p -> TimeUtil.isBetween(p.getDateTime().toLocalTime(), startTime, endTime))
                        .map(p -> new UserMealWithExceed(p.getDateTime(), p.getDescription(), p.getCalories(), e.getValue() > caloriesPerDay)))
                .collect(Collectors.toList());
    }

/*    public static List<UserMealWithExceed> getFilteredWithExceeded1(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> mapByDate = new HashMap<>();
        List<UserMeal> acceptList = new ArrayList<>();

        for (UserMeal um : mealList) {
            if (TimeUtil.isBetween(um.getDateTime().toLocalTime(), startTime, endTime)) {
                acceptList.add(um);
            }
            mapByDate.merge(um.getDateTime().toLocalDate(), um.getCalories(), (value1, value2) -> value1 + value2);
        }

        return acceptList.stream().map(p -> new UserMealWithExceed(p.getDateTime(), p.getDescription(), p.getCalories(), mapByDate.get(p.getDateTime().toLocalDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }*/
}
