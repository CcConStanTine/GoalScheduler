package com.pk.ms.dao.day;

import com.pk.ms.entities.day.DailyRoutinePlan;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DailyRoutinePlanRepository extends CrudRepository<DailyRoutinePlan, Long> {

    Optional<DailyRoutinePlan> findById(long id);

    @Query(nativeQuery = true, value = "SELECT * FROM daily_routine_plan WHERE user_id = ?")
    List<DailyRoutinePlan> findAllByMyScheduleUserId(long userId);

}
