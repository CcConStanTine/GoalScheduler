package com.pk.ms.dao.week;

import com.pk.ms.entities.week.WeekPlan;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WeekPlanRepository extends CrudRepository<WeekPlan, Long> {

    Optional<WeekPlan> findById(long id);

    @Query(nativeQuery = true, value = "SELECT * FROM week_plan WHERE schedule_id = ? AND week_id = ?")
    List<WeekPlan> findWeekPlansByScheduleIdAndWeekId(long scheduleId, long weekId);

}
