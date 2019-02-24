package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int USER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ + 1;

    public static final User USER = new User(USER_ID, "User", "user@yandex.ru", "password", Role.ROLE_USER);
    public static final User ADMIN = new User(ADMIN_ID, "Admin", "admin@gmail.com", "admin", Role.ROLE_ADMIN);

    public static final Meal FIRST = new Meal(100002,
            LocalDateTime.of(2019, 2, 22, 10, 01, 00),
            "Завтрак", 500);
    public static final Meal SECOND = new Meal(100003,
            LocalDateTime.of(2019, 2, 22, 13, 02, 00),
            "Обед", 500);
    public static final Meal THIRD = new Meal(100004,
            LocalDateTime.of(2019, 2, 22, 17, 03, 00),
            "Ужин", 510);
    public static final Meal FOURTH = new Meal(100005,
            LocalDateTime.of(2019, 2, 22, 10, 04, 00),
            "Завтрак", 1500);

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).isEqualTo(expected);
    }
}
