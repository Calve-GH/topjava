package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;

@Transactional(readOnly = true)
public interface CrudMealRepository extends JpaRepository<Meal, Integer> {

    @Override
    @Transactional
    Meal save(Meal meal);

    @Transactional
    @Modifying
    @Query("DELETE FROM Meal m WHERE m.id=:id AND m.user.id=:user_id")
    int delete(@Param("id") int id, @Param("user_id") int userId);

    Meal findByIdAndUserId(int id, int userId);

    @Query("SELECT m FROM Meal m WHERE m.user.id=:user_id ORDER BY m.dateTime DESC")
    List<Meal> getAll(@Param("user_id") int userId);

    @Query(name = Meal.GET_BETWEEN)
    List<Meal> getBetween(@Param("startDate") LocalDateTime startDate,@Param("endDate") LocalDateTime endDate,@Param("userId") int userId);

    //List<Meal> findMealsByUserIdAndDateTimeIsBetweenStartDateAndEndDate(LocalDateTime startDate, LocalDateTime endDate, int userId);
    //findMealsByDateBetweenStartDateAndEndDateAndUserId
}
