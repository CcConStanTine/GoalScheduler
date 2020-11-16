package com.pk.ms.dao.month;

import com.pk.ms.entities.month.MonthPlan;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MonthPlanRepository extends CrudRepository<MonthPlan, Long> {

    MonthPlan findById(long id);

    @Query(nativeQuery = true, value = "SELECT * FROM month_plan WHERE schedule_id = ? AND month_id = ?")
    List<MonthPlan> findMonthPlansByScheduleIdAndMonthId(long scheduleId, long monthId);

}
