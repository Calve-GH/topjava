package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static ru.javawebinar.topjava.MealTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@ActiveProfiles("jdbc")
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void create() {
        Meal newMeal = new Meal(LocalDateTime.of(2019, 02, 23, 10, 0, 0), "Завтрак", 600);
        Meal created = service.create(newMeal, USER_ID);
        newMeal.setId(created.getId());
        assertMatch(service.getAll(USER_ID), newMeal, THIRD, SECOND, FIRST);
    }

    @Test(expected = DuplicateKeyException.class)
    public void createNonUniqueMeal() {
        Meal nonUnique = new Meal(FIRST);
        nonUnique.setId(null);
        service.create(nonUnique, USER_ID);
    }

    @Test
    public void delete() throws Exception {
        service.delete(SECOND.getId(), USER_ID);
        assertMatch(service.getAll(USER_ID), THIRD, FIRST);
    }

    @Test(expected = NotFoundException.class)
    public void deleteNotFound() throws Exception {
        service.delete(1, USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void deleteSomeoneMeal() {
        service.delete(FOURTH.getId(), USER_ID);
    }

    @Test
    public void get() throws Exception {
        Meal meal = service.get(FIRST.getId(), USER_ID);
        assertMatch(meal, FIRST);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() throws Exception {
        service.get(1, USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void getSomeoneMeal() {
        service.get(FOURTH.getId(), USER_ID);
    }

    @Test
    public void update() throws Exception {
        Meal updated = new Meal(FIRST);
        updated.setDescription("Завтрак_Хард");
        updated.setCalories(750);
        service.update(updated, USER_ID);
        assertMatch(service.get(FIRST.getId(), USER_ID), updated);
    }

    @Test(expected = NotFoundException.class)
    public void updateSomeoneMeal() {
        Meal updated = new Meal(FOURTH);
        updated.setDescription("Завтрак_Лайт");
        updated.setCalories(750);
        service.update(updated, USER_ID);
    }

    @Test
    public void getBetweenDateTimes() {
        List<Meal> all = service.getBetweenDates(LocalDate.of(2019, 2, 22), LocalDate.of(2019, 2, 23), USER_ID);
        assertMatch(all, THIRD, SECOND, FIRST);
    }

    @Test
    public void getAll() throws Exception {
        List<Meal> all = service.getAll(USER_ID);
        assertMatch(all, THIRD, SECOND, FIRST);
    }
}