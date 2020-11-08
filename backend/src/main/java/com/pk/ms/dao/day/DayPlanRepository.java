package com.pk.ms.dao.day;

import com.pk.ms.entities.day.DayPlan;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DayPlanRepository extends CrudRepository<DayPlan, Long> {

    DayPlan findById(long id);

    @Query(nativeQuery = true, value = "SELECT * FROM day_plan WHERE schedule_id = ? AND day_id = ?")
    List<DayPlan> findDayPlansByScheduleIdAndDayId(long scheduleId, long dayId);
}
