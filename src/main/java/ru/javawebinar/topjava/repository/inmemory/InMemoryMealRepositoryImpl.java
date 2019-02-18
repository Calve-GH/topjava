package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

//TODO REFACTORING Eliminating boilerplate code
@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        save(1, new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
        save(1, new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000));
        save(1, new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500));
        save(3, new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000));
        save(3, new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500));
        save(3, new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));
    }

    public static <T extends Comparable<T>> boolean isBetweenDate(T local, T start, T end) {
        return local.compareTo(start) >= 0 && local.compareTo(end) <= 0;
    }

    @Override
    public Meal save(int userId, Meal meal) {
        Map<Integer, Meal> currentUserMeals = repository.putIfAbsent(userId, new HashMap<>());
        if (currentUserMeals == null) currentUserMeals = repository.get(userId);
        return InMemoryUtil.saveModel(meal, counter, currentUserMeals);
    }

    @Override
    public boolean delete(int userId, int id) {
        Map<Integer, Meal> currentUserMeals = repository.get(userId);
        if (currentUserMeals == null || !currentUserMeals.containsKey(id))
            return false;
        currentUserMeals.remove(id);
        return true;
    }

    @Override
    public Meal get(int userId, int id) {
        Map<Integer, Meal> currentUserMeals = repository.get(userId);
        if (currentUserMeals == null || !currentUserMeals.containsKey(id))
            return null;
        return currentUserMeals.get(id);
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        Map<Integer, Meal> currentUserMeals = repository.get(userId);
        if (currentUserMeals == null || currentUserMeals.isEmpty())
            return Collections.emptyList(); //TODO mb Collection
        return currentUserMeals.values().stream().sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList()); //TODO mb Collection
    }

    @Override
    public Collection<Meal> getAll(int userId, LocalDate startDate, LocalDate endDate) {
        Collection<Meal> allUserMeals = getAll(userId);
        if (allUserMeals.isEmpty())
            return allUserMeals;
        return allUserMeals.stream().filter(m -> isBetweenDate(m.getDate(), startDate, endDate))
                .collect(Collectors.toList());
    }
}

