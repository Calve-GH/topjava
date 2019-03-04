package ru.javawebinar.topjava.repository.jpa;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Transactional(readOnly = true)
public class JpaMealRepositoryImpl implements MealRepository {

    private final Map<String, Object> map = new HashMap<>();

    @PersistenceContext
    private EntityManager em;

    @Override
    public Meal save(Meal meal, int userId) {
        if (meal.isNew()) {
            em.persist(meal);
            return meal;
        } else {
            return DataAccessUtils.singleResult(em.createNamedQuery(Meal.UPDATE, Meal.class)
                    .setParameter("description", meal.getDescription())
                    .setParameter("calories", meal.getCalories())
                    .setParameter("date_time", meal.getDateTime())
                    .setParameter("id", meal.getId())
                    .setParameter("user_id", userId)
                    .getResultList());
        }
    }

    @Override
    public boolean delete(int id, int userId) {
        return em.createNamedQuery(Meal.DELETE)
                .setParameter("id", id)
                .setParameter("user_id", userId)
                .executeUpdate() != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        return em.find(Meal.class, id, setUserIdToQuery(userId));
        //em.createNamedQuery(Meal.READ)

    }

    @Override
    public List<Meal> getAll(int userId) {
        return em.createNamedQuery(Meal.ALL_SORTED, Meal.class).getResultList();
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return em.createNamedQuery(Meal.ALL_BETWEEN, Meal.class).setParameter(1, userId)
                .setParameter(2, startDate).setParameter(3, endDate)
                .getResultList();
}

    private synchronized Map<String, Object> setUserIdToQuery(Integer userId) {
        map.clear();
        map.put("user_id", userId);
        return map;
    }
}